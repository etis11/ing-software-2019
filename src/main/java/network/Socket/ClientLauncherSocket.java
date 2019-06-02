package network.Socket;

import controller.commandpack.AskPickCommand;
import controller.commandpack.AskWalkCommand;
import controller.commandpack.MoveCommand;
import network.CommandLauncherProxySocket;

import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientLauncherSocket {

    public static void main(String[] args) throws InterruptedException {
        Socket mySocket = null;
        try {

            mySocket = new Socket("localhost", 8000);
        } catch (IOException i) {
            System.out.println(">>> Errore nella connessione, probabilmente il server è down");
        }

        CommandLauncherProxySocket cr = null;
        if (mySocket != null) {
            try {

                cr = new CommandLauncherProxySocket(mySocket);
            } catch (IOException i) {
                System.out.println(i.getMessage());
                i.printStackTrace();
                System.out.println(">>> Problemi con il socket");
            }

            if (cr != null) {
                AskPickCommand c = new AskPickCommand(null, null);
                cr.addCommand(c);
                MoveCommand m = new MoveCommand(null, null, null);
                cr.addCommand(m);
                sleep(10000);
                AskWalkCommand w = new AskWalkCommand(null, null);
                cr.addCommand(w);
                sleep(10000);
            }
        }

    }
}
