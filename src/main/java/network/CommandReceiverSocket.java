package network;

import controller.CommandContainer;
import controller.commandpack.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * A CommandReceiverSocket is an object placed in the server side of the application that receives a command through the net
 * and add it to a command Launcher. This object should be launched within a thread.
 */
public class CommandReceiverSocket implements Runnable{
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
    public void run(){
        while(!stop){
            Command c = readCommand();
            if (c != null){
                launcher.addCommand(c);
            }
        }
        System.out.println(">>>Stopping receiving");
        close();
    }

    /**
     * read  a command from the input stream
     * @return
     */
    private Command readCommand(){
        Command c = null;
        try{
            c = (Command) in.readObject();

        }
        catch (IOException  | ClassNotFoundException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
            stopReceiving();
        }
        return c;
    }

    /**
     * sets stop to true, so that the run method stops running
     */
    private void stopReceiving(){
        stop = true;
    }

    /**
     * close all the streams and the socket
     */
    private void close(){
        try{
            in.close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
            System.out.println("maybe something is already closed in another part of the application");
        }
    }
}
