package controller;

import model.*;
import network.TokenRegistry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the notification to the clients using a json protocol communication
 */
public class JsonNotifier implements Notifier {

    /**
     * used to notify some anomalies during the notification
     */
    private static final Logger jsonNotifierLogger = Logger.getLogger(JsonNotifier.class.getName());
    /**
     * registry used to retrieve useful information and used for disconnection
     */
    private final TokenRegistry registry;
    /**
     * command launcher. It' s here only for the disconnection
     */
    private final CommandLauncherInterface launcher;
    /**
     * game manager, used for the disconnection
     */
    private final GameManager gameManager;

    /**
     * creates the json
     */
    private final JsonCreator jsonCreator;

    public JsonNotifier(JsonCreator jsonCreator, CommandLauncherInterface launcher, GameManager gameManager){
        registry = TokenRegistry.getInstance();
        this.launcher = launcher;
        this.gameManager = gameManager;
        this.jsonCreator = jsonCreator;
    }

    @Override
    public void notifyError(final String message, JsonReceiver jsonReceiver) {
            if(jsonReceiver != null){
                String json = jsonCreator.createJsonWithError(message);
                sendJsonAndHandleException(json, jsonReceiver);
        }
    }

    @Override
    public void notifyMessage(final String message, JsonReceiver jsonReceiver) {
        if(jsonReceiver != null){
            String json = jsonCreator.createJsonWithMessage(message);
            sendJsonAndHandleException(json, jsonReceiver);
        }
    }

    @Override
    public void notifyMessageTargetPlayer(final String message, JsonReceiver jsonReceiver, final Player player) {
        if (jsonReceiver!= null){
            String json = jsonCreator.createTargetPlayerJson(message, player);
            sendJsonAndHandleException(json,jsonReceiver);
        }
    }

    private void sendJsonAndHandleException(final String json, JsonReceiver jsonReceiver){
        try{
                jsonReceiver.sendJson(json);
        }
        catch(RemoteException re){
            disconnectJsonReceiver(jsonReceiver);
        }
        catch (IOException ioe){
            disconnectJsonReceiver(jsonReceiver);
        }
    }

    /**
     * disconnect the given jsonReceiver
     * @param jsonReceiver
     */
    @Override
    public void disconnectReceiver(JsonReceiver jsonReceiver) {
        try{
            jsonReceiver.disconnect();
        }
        catch (IOException ioe){
            jsonNotifierLogger.log(Level.WARNING, "Maybe this receiver was already disconnected");
        }
        disconnectJsonReceiver(jsonReceiver);
    }

    @Override
    public void notifyDisconnection(User user, Player player, JsonReceiver jsonReceiver) {
        TokenRegistry registry = TokenRegistry.getInstance();
        String json = jsonCreator.createTargetPlayerJson(user.getUsername() + " has disconnected.", player);
        sendJsonAndHandleException(json, jsonReceiver);
    }

    /**
     * disconnects a json receiver
     * @param jsonReceiver
     */
    private void disconnectJsonReceiver(JsonReceiver jsonReceiver) {
        //if the user is null, this means that the json receiver is not registered anymore, so another routine has removed him
        if(registry.getJsonUserOwner(jsonReceiver) == null)
        {
            jsonNotifierLogger.log(Level.WARNING, "json Receiver già disconnesso");
            return;
        }
        //same, another routine is disconnecting him
        if(registry.getJsonUserOwner(jsonReceiver).isDisconnected()){
            jsonNotifierLogger.log(Level.WARNING, "json Receiver già disconnesso");
            return;
        }

        User user = registry.getJsonUserOwner(jsonReceiver);
        try{
            launcher.removeJsonReceiver(jsonReceiver);
        }
        catch (RemoteException re){
            jsonNotifierLogger.log(Level.WARNING,
                    "Removing json receiver. This exception should never occour. " +re.getMessage());
        }
        user.setDisconnected(true);
        jsonNotifierLogger.log(Level.INFO, "Disconnecting the receiver");
        registry.removeTokenReceiverAssociation(jsonReceiver);
        if( !gameManager.isMatchStarted()){
            gameManager.getLobby().removeUser(user);
            registry.removeTokenToUserAssociationAndToken(user.getUsername());
        }
        registry.removeReceiverUserAssociation(jsonReceiver);
    }
}
