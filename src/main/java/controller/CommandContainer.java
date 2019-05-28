package controller;

public interface CommandContainer {
    /**
     * adds a command to a queue
     */
    void addCommand(Command c);
}
