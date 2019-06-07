package controller;

import java.rmi.RemoteException;

public interface CommandLauncherInterface extends CommandContainer {

    /**
     * executes a command and removes from a queue
     */
    void executeCommand() throws RemoteException;

    void addJsonReceiver(JsonReceiver j) throws RemoteException;
}
