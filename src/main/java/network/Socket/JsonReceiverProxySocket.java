package network.Socket;

import controller.JsonReceiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Placed on the server side, pretends to be a jsonReceiver, but sends the json to the corresponding client
 */
public class JsonReceiverProxySocket implements JsonReceiver {

    public static final Logger jsonProxyLogger = Logger.getLogger(JsonReceiverProxySocket.class.getName());

    /**
     * the client's socket
     */
    Socket clientSocket;
    /**
     * output stream of the socket
     */
    PrintWriter out;

    /**
     * @param s the socket of the client
     * @throws IOException if there are problems opening the output stream of the socket
     */
    public JsonReceiverProxySocket(Socket s) throws IOException {
        clientSocket = s;
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    /**
     * writes on the output stream the json element
     *
     * @param changes the json element
     * @throws IOException if there are problems while writing the object
     */
    @Override
    public void sendJson(String changes) throws IOException{
        //The stream should be closed, the client socket too in case there is some error on this print writer
        if(out.checkError()){
            close();
            jsonProxyLogger.log(Level.INFO,"Socket " + clientSocket + "may be no more reachable. Launching a IOExcpetion");
            throw new IOException("Socket outputstream not reachable");
        }
        jsonProxyLogger.log(Level.INFO, ">>> Sending the json changes");
        out.println(changes);
        out.flush();
    }

    /**
     * close the output stream and the socket
     */
    private void close(){
        out.close();
        try{
            clientSocket.close();
        }
        catch (IOException ioe){
            jsonProxyLogger.log(Level.INFO, "The socket might have been already closed");
        }
    }
}
