package controller;

import exceptions.DuplicateException;

import java.util.*;

/**
 * The command parser is a registry in which command can be registered (this operation is usually done during initialization).
 * Then, for usage, the command can be obtained on request. A command is associated to a key (which can be considered his
 * name)
 * @param <T> the type of the key used in the registry.
 */
public class CommandParser<T> implements Registry<T> {

    /**
     * registry in which the commands are held
     */
    private final Map<T, Command> registry = new HashMap<>();

    /**
     * Register a command in the parser
     * @param commandName the name of the command
     * @param c the command that has to be registered
     * @throws DuplicateException if the command is already in the registry or there is already a command with the given key
     */
    @Override
    public void registerCommand(T commandName, Command c) throws  DuplicateException {
        if (commandName == null || c == null) throw new IllegalArgumentException("The key or the command are null");
        if(registry.containsKey(commandName)) throw new DuplicateException("There's already a command with this key");
        if(registry.containsValue(c)) throw new DuplicateException("There is already a command with a different key");
        registry.put(commandName, c);
    }

    /**
     * //TODO potrebbe aver senso returnare una copia
     * gets the command with the given command name
     * @param commandName the key
     * @return a command, is never null
     */
    @Override
    public Command getCommand(T commandName) {
        if(commandName == null) throw new  IllegalArgumentException("The key is null");
        if(!registry.containsKey(commandName)) throw new NoSuchElementException("There isnt a command associated to this key");
        return registry.get(commandName);
    }

    /**
     * Returns a copy of the registry.
     * //TODO potrebbe essere meglio copiare anche i comandi?
     * @return
     */
    @Override
    public List<Command> getCommandList() {
        return new ArrayList<>(registry.values());
    }
}
