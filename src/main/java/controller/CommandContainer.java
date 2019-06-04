package controller;

import controller.commandpack.Command;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommandContainer extends Remote{
    /**
     * adds a command to a queue
     */
    void addCommand(Command c) throws RemoteException;
}
