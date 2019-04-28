package controller;

public interface CommandExecutor {

    /**
     * executes a command and removes from a queue
     */
    void executeCommand();

    /**
     * adds a command to a queue
     */
    void addCommand();

    /**
     * executes a secondary command
     */
    void executeSecondaryCommand();
}