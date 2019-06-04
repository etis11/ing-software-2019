package network.Socket;

import com.google.gson.JsonElement;
import controller.JsonReceiver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Placed on
 */
public class JsonReceiverProxySocket implements JsonReceiver {

    Socket clientSocket;
    ObjectOutputStream out;

    public JsonReceiverProxySocket(Socket s) throws IOException {
        clientSocket = s;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void sendJson(JsonElement changes) throws IOException{
        System.out.println(">>> Sending the json changes");
        out.writeObject(changes);
    }
}
