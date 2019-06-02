package controller;

import controller.commandpack.Command;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommandContainer extends Serializable {
    /**
     * adds a command to a queue
     */
    void addCommand(Command c);
}
