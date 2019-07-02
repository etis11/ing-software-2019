package network;

import controller.*;
import controller.commandpack.Command;
import network.RMI.ServerRMI;
import network.Socket.SocketServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Level;

public class ServerLauncher {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, FileNotFoundException {
        if (args.length == 0){
            System.out.println("Porta mancante");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);
        int turnTimer = -1;
        int startTimer = -1;

        if (args.length >= 2){
            turnTimer = Integer.parseInt(args[1]);
            CommandExecutor.turnLength = turnTimer;
        }

        if (args.length >= 3){
            startTimer = Integer.parseInt(args[2]);
            CommandExecutor.startMatchTimerDelay = startTimer;
        }

        CommandLauncherProvider provider = new CommandLauncherProvider();

        ServerRMI rmiServer = new ServerRMI(provider);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("serverRMI", rmiServer);

        SocketServer ss;
        try {

            ss = new SocketServer(port, provider);
            ss.run();
        } catch (IOException i) {
            LOGGER.LOGGER.log(Level.WARNING,"Problemi con il server socket");
            LOGGER.LOGGER.log(Level.WARNING,i.getMessage());
            LOGGER.LOGGER.log(Level.WARNING, Arrays.toString(i.getStackTrace()));
        }


    }
}
