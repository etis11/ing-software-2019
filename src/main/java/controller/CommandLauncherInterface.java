package controller;

public interface CommandLauncherInterface extends CommandContainer {

    /**
     * executes a command and removes from a queue
     */
    void executeCommand();
}
