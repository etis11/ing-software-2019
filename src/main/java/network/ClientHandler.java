package network;

import view.MessageListener;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, MessageListener {

    Socket clientSocket;
    private final BufferedReader is;
    private final PrintWriter os;
    private boolean stop = false;

    public ClientHandler(Socket socket) throws IOException {
        clientSocket = socket;
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = new PrintWriter(socket.getOutputStream()); // put auto-flush?

    }

    @Override
    public void run() {

    }

    @Override
    public String notify(String message) {
        return "to implement";
    }
}
