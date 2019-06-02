package network;

import view.MessageListener;
import view.StringView;

import java.io.*;
import java.net.Socket;

/**
 * This class implements a  view proxy. It's placed on the server side of the application and is perceived as a real view.
 * Use the socket input stream and output stream to communicate. All the notify are made through this component, and those
 * are sent via socket.
 */
public class SocketViewProxy implements MessageListener, StringView {
    /**
     * The socket of the client
     */
    private Socket clientSocket;
    /**
     * input read, receive the string written from the client.
     */
    private final ObjectInputStream input;
    /**
     * The messages are put in this output and sent to the client
     */
    private ObjectOutputStream output;

    /**
     * Creates a proxy linked to the client socket.
     * The input e output stream are implemented as a ObjectInputRead and a printWrite.
     * @param socket the client socket.
     * @throws IOException if there are some error in getting the input stream or the output stream.
     */
    public SocketViewProxy(Socket socket) throws IOException {
        clientSocket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream()); // put auto-flush?

    }

    /**
     * Close the socket and the streams.
     */
    public void close(){

        if (input!=null){
            try{
                input.close();
            }
            catch (IOException i){
                System.err.println("Errors while closing input stream - " + i.getMessage());
            }
        }

        if (output!=null){
            try{
                output.close();
            }
            catch (IOException i){
                System.err.println("Error while closing output stream - " + i.getMessage());
            }
        }

        try{
            clientSocket.close();
        }
        catch (IOException i){
            System.err.println("Error while closing socket - " + i.getMessage());
        }
    }

    /**
     * Implements the observer pattern. In this case, the notification is sent to the original view
     * @param message the notification ( a json file hopefully)
     */
    @Override
    public void notify(String message) {
        try{
            output.writeObject(message);
        }
        catch( IOException i){
            System.err.println("Error in the delivering of the message");
        }
    }

    /**
     * Gets the string from the input stream and return it
     * @return the command given by the client
     */
    @Override
    public String getInput() {
        String msg = null;
        try{
            msg = input.readLine();

        } catch (IOException i){
            System.out.println(i.getMessage());
            i.printStackTrace();
        }
        return msg;
    }
}
