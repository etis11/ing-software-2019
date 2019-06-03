package controller;

import controller.commandpack.Command;

import java.io.Serializable;

public interface CommandContainer extends Serializable{
    /**
     * adds a command to a queue
     */
    void addCommand(Command c);
}
