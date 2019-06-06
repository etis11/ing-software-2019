package network.RMI;

import controller.CommandLauncherInterface;
import controller.JsonReceiver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote {

    CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException;

    String getPersonalToken(JsonReceiver jsonReceiver, String token) throws RemoteException;
}
