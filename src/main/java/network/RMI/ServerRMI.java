package network.RMI;

import controller.CommandLauncher;
import controller.CommandLauncherInterface;
import controller.CommandLauncherProvider;
import controller.JsonReceiver;
import controller.commandpack.NotifyReceiverCommand;
import controller.commandpack.SetUsernameCommand;
import exceptions.DuplicateException;
import model.User;
import network.TokenRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
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

    public static Registry registry;

    /**
     * Creates a server with a launcher and the RMI registry.
     *
     * @param provider the command launcher provider.
     * @throws RemoteException If there are problem initializing the rmi registry.
     */
    public ServerRMI(CommandLauncherProvider provider) throws RemoteException {
        super(1099);
        launchers = provider;

        registry = LocateRegistry.createRegistry(1099);
        rmiServerLogger.log(Level.INFO, "WCreated the registry");
    }

    public Registry getRegistry() {
        return registry;
    }

    /*----------------- ServerRMIInterface implementation -------------------------*/

    /**
     * returns a command launcher
     *
     * @return the  stub of the command launcher
     */
    @Override
    public CommandLauncherInterface getCurrentCommandLauncher(JsonReceiver jsonReceiver) throws RemoteException {
        CommandLauncherInterface launcher= launchers.getCurrentCommandLauncher();
        launcher.addJsonReceiver(jsonReceiver);
        return launcher;
    }

    /**
     * creates a token and adds it to the list of tokens
     * @return the token
     */
    @Override
    public String getPersonalToken(String token){
        rmiServerLogger.log(Level.INFO , ">>>A is trying to register");
        String newToken = token;
        TokenRegistry registry = TokenRegistry.getInstance();
        if (!registry.tokenAlreadyGenerated(token)) {
            newToken = UUID.randomUUID().toString();
        }
        return newToken;
    }

    public String checkUsername(String token, String username, JsonReceiver jsonReceiver){
        if(username.contains(" ") || username.equals(""))
            return "KO - username con formato errato";
        TokenRegistry registry = TokenRegistry.getInstance();
        if(registry.checkAndInsertUsername(token, username)){
            return "KO - username gi?? esistente";
        }
        else{
            registry.associateTokenAndReceiver(token, jsonReceiver);
            rmiServerLogger.log(Level.INFO, "token e ricevitore associati");
            return "OK";
        }
    }

    public CommandLauncherInterface reconnect(String clientToken, JsonReceiver receiver) throws RemoteException{
        TokenRegistry registry = TokenRegistry.getInstance();
        String username = registry.getUsernameFromToken(clientToken);
        CommandLauncherInterface launcher = launchers.getConnectedBeforeLauncher(username);
        User user =launchers.ReconnectUserToOldGame(username);
        if (user == null) throw new RuntimeException("User should not be null");
        launcher.addJsonReceiver(receiver);
        registry.associateTokenAndReceiver(clientToken, receiver);
        if (user!= null){
            registry.associateReceiverAndUser(receiver, user);
        }
        launcher.addCommand( new NotifyReceiverCommand("",receiver));
        return launcher;
    }

}