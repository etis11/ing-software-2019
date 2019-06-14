package network.RMI;

import controller.CommandLauncherInterface;
import controller.CommandLauncherProvider;
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
    private final CommandLauncherProvider launchers;
    /**
     * index of the current available launcher
     */
    private int currentLauncer = 0;

    /**
     * Creates a server with a launcher and the RMI registry.
     *
     * @param provider the command launcher provider.
     * @throws RemoteException If there are problem initializing the rmi registry.
     */
    public ServerRMI(CommandLauncherProvider provider) throws RemoteException {
        launchers = provider;
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
    public CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException {
        return launchers.getCurrentCommandLauncher();
    }

    /**
     * creates a token and adds it to the list of tokens
     *
     * @return the token
     */
    @Override
    public String getPersonalToken(JsonReceiver jsonReceiver, String token) throws RemoteException {
        rmiServerLogger.log(Level.INFO , ">>>A client is asking a token");
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
