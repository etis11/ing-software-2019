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

        CommandLauncherInterface launcher = new CommandLauncherInterface() {
            @Override
            public void executeCommand() {
                System.out.println(">>> Eseguo");
            }

            @Override
            public void addCommand(Command c) {
                System.out.println(">>> Arrivato il comando: " + c.getClass());
            }

            @Override
            public void addJsonReceiver(JsonReceiver j) throws RemoteException {
                System.out.println("cose");
            }
        };

        ServerRMI rmiServer = new ServerRMI(provider);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("serverRMI", rmiServer);

        SocketServer ss;
        try {

            ss = new SocketServer(8000, provider);
            ss.run();
        } catch (IOException i) {
            System.out.println("che merda");
            System.out.println(i.getMessage());
            i.printStackTrace();
        }


    }
}
