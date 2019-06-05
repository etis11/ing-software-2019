package network.Socket;

import com.google.gson.JsonElement;
import controller.JsonReceiver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Placed on the server side, pretends to be a jsonReceiver, but sends the json to the corresponding client
 */
public class JsonReceiverProxySocket implements JsonReceiver {

    /**
     * the client's socket
     */
    Socket clientSocket;
    /**
     * output stream of the socket
     */
    ObjectOutputStream out;

    /**
     *
     * @param s the socket of the client
     * @throws IOException if there are problems opening the output stream of the socket
     */
    public JsonReceiverProxySocket(Socket s) throws IOException {
        clientSocket = s;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    /**
     * writes on the output stream the json element
     * @param changes the json element
     * @throws IOException if there are problems while writing the object
     */
    @Override
    public void sendJson(JsonElement changes) throws IOException{
        System.out.println(">>> Sending the json changes");
        out.writeObject(changes);
        out.flush();
    }
}
