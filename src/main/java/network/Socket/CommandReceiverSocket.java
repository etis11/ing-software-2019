package network.Socket;

import controller.*;
import controller.commandpack.Command;
import controller.commandpack.SetUsernameCommand;
import network.TokenRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A CommandReceiverSocket is an object placed in the server side of the application that receives a command through the net
 * and add it to a command Launcher. This object should be launched within a thread.
 */
public class CommandReceiverSocket implements Runnable {

    private static final Logger commandReceiverSocketLogger = Logger.getLogger(CommandReceiverSocket.class.getName());
    /**
     * The socket that is sending commands
     */
    private final Socket clientSocket;
    /**
     * the input stream of the socket
     */
    private ObjectInputStream in;
    /**
     * If true, the command receiver stops receiving
     */
    private boolean stop;
    /**
     * the object that provides command launcher, executors, lobby, and match
     */
    private CommandLauncherProvider provider;

    /**
     * token registry used to create the user
     */
    private final TokenRegistry registry;

    private CommandLauncherInterface launcher;

    public CommandReceiverSocket(Socket clientSocket, CommandLauncherProvider provider) throws IOException {
        this.clientSocket = clientSocket;
        stop = false;
        this.provider = provider;
        registry = TokenRegistry.getInstance();
    }

    /**
     * Reads a command from the input stream. If is read a null value, the client stops receiving
     */
    @Override
    public void run() {
        String clientToken = null;
        String username = null;
        try{
            PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream());
            Scanner clientInput = new Scanner(clientSocket.getInputStream());
            clientToken = getClientToken(clientSocket);
            //creates the right token
            if (!registry.tokenAlreadyGenerated(clientToken)) {
                commandReceiverSocketLogger.log(Level.INFO, ">>> Generating Token");
                clientToken = UUID.randomUUID().toString();
            } else {
                commandReceiverSocketLogger.log(Level.INFO, ">>> Token sent valid");
            }
            //sends the token to the client
            commandReceiverSocketLogger.log(Level.INFO, ">>> Sending token");
            clientOutput.println(clientToken);
            clientOutput.flush();
            //now asks for the username fino a quando non è corretto.
            boolean ok = false;
            while (!ok){
                clientOutput.println("Inserisci uno username");
                clientOutput.flush();
                username = clientInput.nextLine();
                if (username.contains(" ") || username.equals(""))
                    clientOutput.println("Username con formato non corretto");
                else if (registry.checkAndInsertUsername(clientToken, username)){
                    clientOutput.println("Username già presente, inserire di nuovo.");
                }
                else{
                    ok = true;
                    clientOutput.println("OK");
                }
                clientOutput.flush();
            }
            //creates a json Receiver and binds it to the client socket.
            JsonReceiver receiverProxy = new JsonReceiverProxySocket(clientSocket);
            launcher = provider.getCurrentCommandLauncher();
            launcher.addJsonReceiver(receiverProxy);
            commandReceiverSocketLogger.log(Level.INFO,">>>Token and proxy associated");
            registry.associateTokenAndReceiver(clientToken, receiverProxy);
            in = new ObjectInputStream(clientSocket.getInputStream());
            launcher.addCommand(new SetUsernameCommand(clientToken, username));
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }



        while (!stop) {
            commandReceiverSocketLogger.log(Level.FINE, ">>> Waiting for next command from " + clientSocket + ".");
            Command c = readCommand();
            if (c != null) {
                commandReceiverSocketLogger.log(Level.FINE, ">>> Received command from " + clientSocket + ". Adding it to the launcher");
                try {
                    launcher.addCommand(c);
                } catch (RemoteException i) {
                    throw new RuntimeException("This exception should never occurs. A socket doesn't generate a Remote exception ");
                }
            }
        }
        commandReceiverSocketLogger.log(Level.INFO, ">>> Stopping receiving from " + clientSocket + ".");
        close();
    }

    /**
     * read  a command from the input stream
     *
     * @return
     */
    private Command readCommand() {
        Command c = null;
        try {
            c = (Command) in.readObject();

        } catch (IOException | ClassNotFoundException ioe) {
            commandReceiverSocketLogger.log(Level.WARNING, ">>> The input stream of " + clientSocket +
                    " is not working anymore. The client may be disconnected");


            stopReceiving();
        }
        return c;
    }

    /**
     * sets stop to true, so that the run method stops running
     */
    private void stopReceiving() {
        stop = true;
    }

    /**
     * close all the streams and the socket
     */
    private void close() {
        try {
            SocketServer.serverSocketLogger.log(Level.INFO, ">>> Closing input stream");
            in.close();
        } catch (IOException ioe) {
            SocketServer.serverSocketLogger.log(Level.WARNING, ioe.getMessage() + " . Input stream may be already closed");
        }

        try {
            SocketServer.serverSocketLogger.log(Level.INFO, ">>> Closing output stream");
            clientSocket.getOutputStream().close();
        } catch (IOException ioe) {
            SocketServer.serverSocketLogger.log(Level.WARNING, ">>> Closing output stream failed. " + ioe.getMessage());
            //System.out.println(">>> Output stream maybe already closed"); redundant with the getMessage
        }

        try {
            SocketServer.serverSocketLogger.log(Level.WARNING, ">>> Closing " + clientSocket);
            clientSocket.close();
        } catch (IOException ioe) {
            SocketServer.serverSocketLogger.log(Level.WARNING, ioe.getMessage() + ". Socket maybe already closed");
        }
    }

    private String getClientToken(Socket clientSocket) throws IOException {
        Scanner b = new Scanner(clientSocket.getInputStream());
        String returnString = b.nextLine();
        return returnString;

    }
}
