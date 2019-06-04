package network.Socket;

import com.google.gson.JsonElement;
import controller.JsonReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class JsonRouterSocket implements Runnable{

    Socket clientSocket;
    ObjectInputStream in;
    JsonReceiver receiver;

    public JsonRouterSocket(Socket s, JsonReceiver receiver) throws IOException {
        clientSocket = s;
        in = new ObjectInputStream(clientSocket.getInputStream());
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try{
            JsonElement changes = (JsonElement) in.readObject();
            receiver.sendJson(changes);

        }
        catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
