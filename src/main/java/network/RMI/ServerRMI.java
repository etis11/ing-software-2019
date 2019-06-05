package network.RMI;

import controller.CommandLauncherInterface;
import network.ServerLauncher;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private List<CommandLauncherInterface> launchers = new ArrayList<>();
    private int currentLauncer = 0;
    private static final Logger rmiServerLogger = Logger.getLogger(ServerRMI.class.getName());

    public ServerRMI(CommandLauncherInterface firstLauncher) throws RemoteException, AlreadyBoundException {
        launchers.add(firstLauncher);
        LocateRegistry.createRegistry(1099);
        rmiServerLogger.log(Level.INFO, ">>> Created the registry");
    }

    /*----------------- ServerRMIInterface implementation -------------------------*/
    @Override
    public CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException {
        return launchers.get(currentLauncer);
    }

    @Override
    public String getPersonalToken() throws RemoteException {
        return UUID.randomUUID().toString();
    }
}
