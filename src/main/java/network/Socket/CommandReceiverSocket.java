package network.Socket;

import controller.CommandContainer;
import controller.commandpack.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;
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
    private final ObjectInputStream in;
    /**
     * If true, the command receiver stops receiving
     */
    private boolean stop;
    /**
     * the object where the commands are added
     */
    private CommandContainer launcher;

    public CommandReceiverSocket(Socket clientSocket, CommandContainer commandLauncher) throws IOException {
        this.clientSocket = clientSocket;
        in = new ObjectInputStream(this.clientSocket.getInputStream());
        stop = false;
        launcher = commandLauncher;
    }

    /**
     * Reads a command from the input stream. If is read a null value, the client stops receiving
     */
    @Override
    public void run() {
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
            //System.out.println(ioe.getMessage()); printa null
            commandReceiverSocketLogger.log(Level.WARNING, ">>> The input stream of " + clientSocket +
                    " is not working anymore. The client could have disconnected");
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
}
