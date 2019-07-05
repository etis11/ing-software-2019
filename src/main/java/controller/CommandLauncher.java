package controller;


import controller.commandpack.Command;
import controller.commandpack.StopTimerLobby;
import model.GameManager;
import model.JsonCreator;
import network.TokenRegistry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that implements a generic behavior for a command executor. Creates thread that executes the commands. If no commands
 * are put in the queue, the command executor is in a wait state
 */
public class CommandLauncher implements CommandLauncherInterface {

    private static final Logger commandLauncherLogger = Logger.getLogger(CommandLauncher.class.getName());
    private final List<JsonReceiver> allReceivers = new ArrayList<>(5);
    /**
     * A queue where the commands are placed. The queue is concurrent and the commands of the current player are
     */
    private final BlockingQueue<Command> commandQueue;

    /**
     * The pool that gives the thread for the execution of the commands
     */
    private final ExecutorService pool;

    private final CommandExecutor commandExecutor;

    private AtomicBoolean stop = new AtomicBoolean(false);

    /**
     * creates a command executor with the given match. Also the commandQueue is set empty and the pool is a cached pool
     *
     * @param gameManager the game in which the players are playing
     */
    public CommandLauncher(GameManager gameManager, JsonCreator jsonCreator) {
        commandQueue = new LinkedBlockingDeque<>();
        this.pool = Executors.newCachedThreadPool();
        commandExecutor = new CommandExecutor(gameManager, jsonCreator, this);
    }

    /**
     * Waits for the command queue to fill. Takes the command and launch a new thread the executes it's method.
     * If the thread is interrupted, the executor shuts down
     */
    @Override
    public void executeCommand() {
        Command takenCommand = null;
        while (!stop.get()) {
            try {
                takenCommand = commandQueue.take();
                commandLauncherLogger.log(Level.INFO, ">>> " + takenCommand.getClass() + " extracted");
                String token = takenCommand.getToken();
                JsonReceiver clientReceiver = TokenRegistry.getInstance().getJsonReceiver(token);
                takenCommand.setJsonReceiver(clientReceiver);
                takenCommand.setAllJsonReceivers(getAllReceivers());
            } catch (InterruptedException i) {
                commandLauncherLogger.log(Level.WARNING, i.getMessage(), i);
                pool.shutdown();
                stopExecuting();
                Thread.currentThread().interrupt();
            }
            if (takenCommand != null) {
                try {
                    takenCommand.execute(commandExecutor);
                } catch (IOException e) {
                    commandLauncherLogger.log(Level.INFO,"un client si è disconnesso e non sono riuscito a gestire bene la disconnessione");
                   // e.printStackTrace();
                }
            }
        }

    }

    private List<JsonReceiver> getAllReceivers(){
        synchronized (allReceivers){
            return allReceivers;
        }
    }

    @Override
    public void removeJsonReceiver(JsonReceiver js){
        synchronized (allReceivers){
            allReceivers.remove(js);
            commandLauncherLogger.log(Level.INFO, "Removed a json receiver. A disconnection may have occurred");
        }
        addCommand(new StopTimerLobby(""));
    }

    @Override
    public void addJsonReceiver(JsonReceiver j){
        synchronized (allReceivers){
            allReceivers.add(j);
        }
    }

    /* *********************** Command Launcher interface implementation *******************************/

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
        if(!commandQueue.offer(c))
            LOGGER.LOGGER.log(Level.WARNING,"There was a problem with Queue");

    }

    @Override
    public void stopExecuting() {
        stop.set(true);
    }
}
