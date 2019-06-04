package network.RMI;

import controller.CommandLauncherInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI extends UnicastRemoteObject implements ServerRMIInterface {

    private List<CommandLauncherInterface> launchers = new ArrayList<>();
    private int currentLauncer = 0;

    public ServerRMI(CommandLauncherInterface firstLauncher) throws RemoteException, AlreadyBoundException {
        launchers.add(firstLauncher);
        //exports the current object
        //UnicastRemoteObject.exportObject(this, 0);

        //gets the registry and binds the RMIServer in the registry
//        Registry registry = LocateRegistry.getRegistry();
//        registry.bind("serverRMI", this);
    }

    /*----------------- ServerRMIInterface implementation -------------------------*/
    @Override
    public CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException {
        return launchers.get(currentLauncer);
    }
}
