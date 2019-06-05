package network.Socket;

import com.google.gson.JsonElement;
import controller.JsonReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * The socket class that receives from the network the response of the commands
 */
public class JsonRouterSocket implements Runnable{

    /**
     * the socket of the client
     */
    Socket clientSocket;
    /**
     * input stream of the socket
     */
    ObjectInputStream in;
    /**
     * the receiver that has to receive the json object
     */
    JsonReceiver receiver;
    boolean stop;

    /**
     * creates a json router socket that receives the json elements. It's associated to the client socket
     * and a json receiver
     * @param s socket
     * @param receiver json receiver
     * @throws IOException If there are problems getting the socket input stream
     */
    public JsonRouterSocket(Socket s, JsonReceiver receiver) throws IOException {
        clientSocket = s;
        in = new ObjectInputStream(clientSocket.getInputStream());
        this.receiver = receiver;
        stop = false;
    }

    /**
     * Waiting for a json from the net
     */
    @Override
    public void run() {
        while (!stop)
        try{
            JsonElement changes = (JsonElement) in.readObject();
            receiver.sendJson(changes);

        }
        catch (IOException | ClassNotFoundException e){
            stop();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void stop(){
        this.stop = true;
    }
}
