package network.Socket;

import controller.CommandLauncherInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Accept socket connections
 */
public class SocketServer {
    /**
     * the socket of the server
     */
    private ServerSocket serverSocket;
    /**
     * if true, stops the server
     */
    private boolean stop;
    /**
     * a thread pool for launching the command receivers
     */
    private ExecutorService threadPool;
    /**
     * num max of clients that can be served ad once
     */
    private final static int MAX_CLIENTS = 5;

    private final CommandLauncherInterface commandLauncher;

    public SocketServer(int port, CommandLauncherInterface c) throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println(">>> Server Launched on port:" + port +".");
        threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        commandLauncher = c;
    }

    /**
     * Accepts a connection and creates a CommandParser associated to the Proxy view. The socket is bound to the proxy
     * @throws IOException throws an IOException if something is wrong with the connection.
     */
    public void run() throws IOException {
        while(!stop){
            System.out.println(">>> Waiting for connection.");
            Socket clientSocket = serverSocket.accept();
            //clientSocket.getOutputStream().write(generateToken())
            System.out.println(">>> New connection accepted: " + clientSocket.getRemoteSocketAddress());
            //this part will change if we make the change
            threadPool.submit(new CommandReceiverSocket(clientSocket, commandLauncher));
//            CommandReceiverSocket r = new CommandReceiverSocket(clientSocket, commandLauncher);
//            r.run();

        }
    }

    /**
     * the server is putted in a stop state
     */
    public void stopReceiving(){
        stop = true;
    }
}
