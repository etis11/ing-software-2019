package network.Socket;

import controller.CommandContainer;
import controller.commandpack.Command;
import view.AnsiColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class behave like a command Container, but instead of adding a command in a pool, sends it to the executor on the server
 */
public class CommandLauncherProxySocket implements CommandContainer {

    private final Socket socket;
    //private ObjectInputStream in;
    private final ObjectOutputStream out;

    private final String token;

    private final int maxTries = 3;
    /**
     * creates a socker
     *
     * @param host IP of the host
     * @param port port on which is listening
     * @throws IOException problems while getting the input and output stream
     */
    public CommandLauncherProxySocket(String host, int port, String token) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        this.token = token;
    }

    public CommandLauncherProxySocket(Socket s, String token) throws IOException {
        socket = s;
        out = new ObjectOutputStream(s.getOutputStream());
        this.token = token;
    }

    /**
     * sends the command on the net
     *
     * @param c the command that has to be sent
     */
    @Override
    public void addCommand(Command c) {
            try {
                out.writeObject(c);
                out.flush();
            } catch (IOException i) {
                close();
                System.out.println(AnsiColor.RED + "Socket Server irraggiungibile (sto provando a inviare un comando)" + AnsiColor.RESET);
                System.exit(1);
            }
    }

    /**
     * close the socket. After this method, the copmmand executor is not able to send commands on the net anymore
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException i) {
            System.err.println(i.getMessage());
            i.printStackTrace();
        }
    }
}
