package controller;


import controller.commandpack.Command;
import model.GameManager;
import model.Match;
import network.TokenRegistry;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that implements a generic behavior for a command executor. Creates thread that executes the commands. If no commands
 * are put in the queue, the command executor is in a wait state
 */
public class CommandLauncher implements CommandLauncherInterface {

    private static final Logger LOGGER = Logger.getLogger(CommandLauncher.class.getName());
    /**
     * A queue where the commands are placed. The queue is concurrent and the commands of the current player are
     */
    private final BlockingQueue<Command> commandQueue;

    /**
     * The pool that gives the thread for the execution of the commands
     */
    private final ExecutorService pool;

    private final CommandExecutor commandExecutor;

    private boolean stop = false;

    /**
     * creates a command executor with the given match. Also the commandQueue is set empty and the pool is a cached pool
     *
     * @param gameManager the game in which the players are playing
     */
    public CommandLauncher(GameManager gameManager) {
        commandQueue = new LinkedBlockingDeque<>();
        this.pool = Executors.newCachedThreadPool();
        commandExecutor = new CommandExecutor(gameManager);
    }

    /**
     * Waits for the command queue to fill. Takes the command and launch a new thread the executes it's method.
     * If the thread is interrupted, the executor shuts down
     */
    @Override
    public void executeCommand() {
        Command takenCommand = null;
        while (!stop) {
            try {
                takenCommand = commandQueue.take();
                String token = takenCommand.getToken();
                takenCommand.setJsonReceiver(TokenRegistry.getJsonReceiver(token));
            } catch (InterruptedException i) {
                System.out.println(i.getMessage());
                LOGGER.log(Level.WARNING, i.getMessage(), i);
                System.out.println("Unable to extract a command because the reading threas has been interrupted. " +
                        "Stopping the command executor");
                pool.shutdown();
                stop = true;
                Thread.currentThread().interrupt();
            }

            if (takenCommand != null) {
//                System.out.println("provo a runnare");
                //multithread command management
                //pool.submit(new RunnableCommand(takenCommand));
                takenCommand.execute(commandExecutor);
            }
        }

    }

    /**
     * add the specified command to the commandList.
     *
     * @param c command to be added
     */
    @Override
    public void addCommand(Command c) {
        if (c == null) {
            throw new IllegalArgumentException();
        }
        commandQueue.offer(c);
    }
}
