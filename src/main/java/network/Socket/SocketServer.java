package network.Socket;

import controller.CommandLauncherInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private final static Logger serverLogger = Logger.getLogger(SocketServer.class.getName());

    public SocketServer(int port, CommandLauncherInterface c) throws IOException{
        serverSocket = new ServerSocket(port);
        serverLogger.log(Level.INFO,">>> Server Launched on port:" + port +".");
        threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        commandLauncher = c;
    }

    /**
     * Accepts a connection and creates a CommandParser associated to the Proxy view. The socket is bound to the proxy
     * @throws IOException throws an IOException if something is wrong with the connection.
     */
    public void run() throws IOException {
        while(!stop){
            serverLogger.log(Level.INFO, ">>> Waiting for connection.");
            Socket clientSocket = serverSocket.accept();
            PrintWriter p = new PrintWriter(clientSocket.getOutputStream());
            serverLogger.log(Level.INFO,">>> Generating Token");
            p.write(UUID.randomUUID().toString());
            p.flush();
            //clientSocket.getOutputStream().write(generateToken())
            serverLogger.log(Level.INFO,">>> New connection accepted: " + clientSocket.getRemoteSocketAddress());
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
