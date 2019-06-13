package network.RMI;

import controller.CommandLauncherInterface;
import controller.JsonReceiver;
import exceptions.DuplicateException;
import network.TokenRegistry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An exportable object that provides some functionality to a RMI client
 */
public class ServerRMI extends UnicastRemoteObject implements ServerRMIInterface {
    private static final Logger rmiServerLogger = Logger.getLogger(ServerRMI.class.getName());
    /**
     * Lists of all the command launchers. Each Command Launcher is associated to a single match
     */
    private List<CommandLauncherInterface> launchers = new ArrayList<>();
    /**
     * index of the current available launcher
     */
    private int currentLauncer = 0;

    /**
     * Creates a server with a launcher and the RMI registry.
     *
     * @param firstLauncher the first launcher ever created in this run.
     * @throws RemoteException If there are problem initializing the rmi registry.
     */
    public ServerRMI(CommandLauncherInterface firstLauncher) throws RemoteException {
        launchers.add(firstLauncher);
        LocateRegistry.createRegistry(1099);
        rmiServerLogger.log(Level.INFO, ">>> Created the registry");
    }

    /*----------------- ServerRMIInterface implementation -------------------------*/

    /**
     * returns a command launcher
     *
     * @return the  stub of the command launcher
     */
    @Override
    public CommandLauncherInterface getCurrentCommandLauncher() {
        return launchers.get(currentLauncer);
    }

    /**
     * creates a token and adds it to the list of tokens
     *
     * @return the token
     */
    @Override
    public String getPersonalToken(JsonReceiver jsonReceiver, String token) throws RemoteException {
        String newToken = token;
        TokenRegistry registry = TokenRegistry.getInstance();
        if (registry.tokenAlreadyGenerated(token)) {
            newToken = UUID.randomUUID().toString();
        }
        try {
            registry.associateTokenAndReceiver(newToken, jsonReceiver);
            getCurrentCommandLauncher().addJsonReceiver(jsonReceiver);
        } catch (DuplicateException d) {
            rmiServerLogger.log(Level.WARNING, ">>> A client already associated is trying to get another token");
            throw new DuplicateException(d);
        }
        return token;
    }
}
