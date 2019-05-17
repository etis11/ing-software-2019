package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private ServerSocket serverSocket;
    private boolean stop = false;
    private ExecutorService threadPool;
    private final static int MAX_CLIENTS = 5;

    public SocketServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
    }

    public void run() throws IOException {
        while(!stop){
            Socket clientSocket = serverSocket.accept();
            System.out.println(">>>New connection accepted: " + clientSocket.getRemoteSocketAddress());
//            threadPool.submit();

        }
    }
}
