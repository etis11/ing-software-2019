package controller;

import exceptions.DuplicateException;

import java.util.List;

/**
 * This interface describes a parser, that has to be able to register and get a command (given a key) and should be able
 * to give a list of all the registered command.
 * @param <T> The type of the key used to get the commands registered. The T type has to override its equal method.
 */
public interface Registry<T> {

    /**
     * Add to a register a command associated to its key. It's used to initialize the parser
     * @param commandName the name of the command
     * @param c the command that has to be registered
     */
    public void registerCommand( T commandName, Command c) throws DuplicateException;

    /**
     * Returns a command associated to the key
     * @param commandName the key
     * @return a command. Can't be null
     */
    public Command getCommand(T commandName);

    public List<Command> getCommandList();
}

