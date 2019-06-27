package network.Socket;

import controller.JsonReceiver;
import view.AnsiColor;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
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

    private final int maxTentatives = 3;

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
        while (!stop){
            try {
                String changes = in.nextLine();
                receiver.sendJson(changes);
            }
            //no such element exeption thrown if something is already closed i guess
            catch (IOException | NoSuchElementException e) {
                stop();
            }
        }
        try {
            clientSocket.close();
        }
        catch (IOException i) {
            System.out.println("Chiusura socket ha lanciato un'eccezzione");
        }
        System.out.println(AnsiColor.RED + "Server non raggiungibile, in lettura dai json" + AnsiColor.RESET);
        System.exit(1);
    }

    public void stop() {
        this.stop = true;
    }

    public void close() {
        //TODO
    }
}
