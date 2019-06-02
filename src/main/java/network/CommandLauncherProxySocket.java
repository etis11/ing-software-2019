package network;

import controller.CommandContainer;
import controller.commandpack.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class behave like a command Container, but instead of adding a command in a pool, sends it to the executor on the server
 */
public class CommandLauncherProxySocket implements CommandContainer {

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    /**
     * creates a socker
     * @param host IP of the host
     * @param port port on which is listening
     * @throws IOException problems while getting the input and output stream
     */
    public CommandLauncherProxySocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * sends the command on the net
     * @param c the command that has to be sent
     */
    @Override
    public void addCommand(Command c){
        try{
            out.writeObject(c);
        }
        catch (IOException i){
            System.err.println(i.getMessage());
            i.printStackTrace();
        }
    }

    /**
     * close the socket. After this method, the copmmand executor is not able to send commands on the net anymore
     */
    public void close(){
        try {
            socket.close();
        }
        catch (IOException i ){
            System.err.println(i.getMessage());
            i.printStackTrace();
        }
    }
}
