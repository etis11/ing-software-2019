package network.Socket;

import com.google.gson.JsonElement;
import controller.JsonReceiver;
import model.Lobby;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Placed on the server side, pretends to be a jsonReceiver, but sends the json to the corresponding client
 */
public class JsonReceiverProxySocket implements JsonReceiver {

    public static final Logger jsonProxyLogger = Logger.getLogger(JsonReceiverProxySocket.class.getName());

    /**
     * the client's socket
     */
    Socket clientSocket;
    /**
     * output stream of the socket
     */
    PrintWriter out;

    /**
     *
     * @param s the socket of the client
     * @throws IOException if there are problems opening the output stream of the socket
     */
    public JsonReceiverProxySocket(Socket s) throws IOException {
        clientSocket = s;
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    /**
     * writes on the output stream the json element
     * @param changes the json element
     * @throws IOException if there are problems while writing the object
     */
    @Override
    public void sendJson(String changes) throws IOException{
        jsonProxyLogger.log(Level.INFO, ">>> Sending the json changes");
        out.println(changes);
        out.flush();
    }
}
