package controller;

import controller.commandpack.Command;
import controller.commandpack.CommandContainer;

public interface CommandExecutor extends CommandContainer {

    /**
     * executes a command and removes from a queue
     */
    void executeCommand();
}
