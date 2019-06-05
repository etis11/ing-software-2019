package network.RMI;

import controller.CommandLauncherInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote {

    CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException;

    String getPersonalToken() throws RemoteException;
}
