package network.Socket;

import controller.JsonReceiver;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The socket class that receives from the network the response of the commands
 */
public class JsonRouterSocket implements Runnable {

    /**
     * the receiver that has to receive the json object
     */
    JsonReceiver receiver;
    boolean stop;
    /**
     * the socket of the client
     */
    private Socket clientSocket;
    /**
     * input stream of the socket
     */
    private Scanner in;

    /**
     * creates a json router socket that receives the json elements. It's associated to the client socket
     * and a json receiver
     *
     * @param s        socket
     * @param receiver json receiver
     * @throws IOException If there are problems getting the socket input stream
     */
    public JsonRouterSocket(Socket s, JsonReceiver receiver) throws IOException {
        clientSocket = s;
        in = new Scanner(clientSocket.getInputStream());
        this.receiver = receiver;
        stop = false;
    }

    /**
     * Waiting for a json from the net
     */
    @Override
    public void run() {
        while (!stop)
            try {
                String changes = in.nextLine();
                receiver.sendJson(changes);
            } catch (IOException e) {
                stop();
                throw new RuntimeException(e.getMessage());
            }
    }

    public void stop() {
        this.stop = true;
    }

    public void close() {
        //TODO
    }
}
