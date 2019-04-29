package controller;

import model.Match;

import java.util.List;

public abstract  class AbstractCommandExecutor implements CommandExecutor{
    //TODO lista di client

    protected Match match;

    List<Command> commandList;

    /**
     * execute the specified command
     * @param c command to be executed
     */
    @Override
    public void executeCommand(Command c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        commandList.get(commandList.indexOf(c)).execute();
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
