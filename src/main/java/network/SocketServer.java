package network;

import controller.CommandLauncher;
import controller.CommandLauncherInterface;
import controller.CommandParser;

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
    private final CommandLauncherInterface commandLauncher;

    public SocketServer(int port, CommandLauncherInterface c) throws IOException{
        serverSocket = new ServerSocket(port);
        threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        commandLauncher = c;
    }

    /**
     * Accepts a connection and creates a CommandParser associated to the Proxy view. The socket is bound to the proxy
     * @throws IOException throws an IOException if something is wrong with the connection.
     */
    public void run() throws IOException {
        while(!stop){
            Socket clientSocket = serverSocket.accept();
            System.out.println(">>>New connection accepted: " + clientSocket.getRemoteSocketAddress());
            //this part will change if we make the change
            threadPool.submit(new CommandReceiverSocket(clientSocket, commandLauncher));

        }
    }
}
