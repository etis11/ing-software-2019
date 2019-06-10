package network.Socket;

import controller.CommandLauncherInterface;
import controller.JsonReceiver;
import network.TokenRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public final static Logger serverSocketLogger = Logger.getLogger(SocketServer.class.getName());

    public SocketServer(int port, CommandLauncherInterface c) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocketLogger.log(Level.INFO,">>> Server Launched on port:" + port +".");
        threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        commandLauncher = c;
    }

    /**
     * Accepts a connection and creates a CommandParser associated to the Proxy view. The socket is bound to the proxy
     * @throws IOException throws an IOException if something is wrong with the connection.
     */
    public void run() throws IOException {
        while(!stop){
            serverSocketLogger.log(Level.INFO, ">>> Waiting for connection.");
            Socket clientSocket = serverSocket.accept();
            serverSocketLogger.log(Level.INFO,">>> New connection accepted: " + clientSocket.getRemoteSocketAddress());
            PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream());
            String clientToken = getClientToken(clientSocket);
            if (!TokenRegistry.tokenAlreadyGenerated(clientToken)){
                serverSocketLogger.log(Level.INFO,">>> Generating Token");
                clientToken = UUID.randomUUID().toString();
            }
            else{
                serverSocketLogger.log(Level.INFO, ">>> Token sent valid");
            }
            //sends the token to the client
            clientToken += "\n";
            clientOutput.write(clientToken);
            clientOutput.flush();
            //creates a json Receiver and binds it to the client socket.
            JsonReceiver receiverProxy = new JsonReceiverProxySocket(clientSocket);
            commandLauncher.addJsonReceiver(receiverProxy);
            TokenRegistry.associateTokenAndReceiver(clientToken, receiverProxy);
            threadPool.submit(new CommandReceiverSocket(clientSocket, commandLauncher));;

        }
    }

    /**
     * the server is putted in a stop state
     */
    public void stopReceiving(){
        stop = true;
    }

    private String getClientToken(Socket clientSocket) throws  IOException{
        BufferedReader b = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String returnString = b.readLine();
        return returnString;

    }
}
