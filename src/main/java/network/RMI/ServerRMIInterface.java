package network.RMI;

import controller.CommandLauncher;
import controller.CommandLauncherInterface;
import controller.JsonReceiver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMIInterface extends Remote {

    CommandLauncherInterface getCurrentCommandLauncher(JsonReceiver jsonReceiver) throws RemoteException;

    String getPersonalToken(String token) throws RemoteException;

    String checkUsername(String token, String username, JsonReceiver jsonReceiver) throws RemoteException;

    CommandLauncherInterface reconnect(String token, JsonReceiver jsonReceiver) throws RemoteException;
}
