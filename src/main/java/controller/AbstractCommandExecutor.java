package controller;

import controller.commandpack.Command;
import model.Match;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract  class AbstractCommandExecutor implements CommandExecutor{
    //TODO lista di client

    protected Match match;

    ConcurrentLinkedQueue<Command> commandList;

    /**
     * execute the first command in the CommandList
     */
    @Override
    public void executeCommand() {
        Command c = commandList.remove();
        c.execute();
    }

    /**
     * add the specified command to the commandList
     * @param c command to be added
     */
    @Override
    public void addCommand(Command c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        commandList.add(c);
    }

    @Override
    public void executeSecondaryCommand() {
        //TODO
    }

}
