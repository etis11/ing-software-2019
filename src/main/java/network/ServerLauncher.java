package network;

import controller.CommandLauncherInterface;
import controller.CommandLauncherProvider;
import controller.JsonReceiver;
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

public class ServerLauncher {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, FileNotFoundException {
        CommandLauncherProvider provider = new CommandLauncherProvider();

        ServerRMI rmiServer = new ServerRMI(provider);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("serverRMI", rmiServer);

        SocketServer ss;
        try {

            ss = new SocketServer(8000, provider);
            ss.run();
        } catch (IOException i) {
            System.out.println("Problemi con il server socket");
            System.out.println(i.getMessage());
            i.printStackTrace();
        }


    }
}
