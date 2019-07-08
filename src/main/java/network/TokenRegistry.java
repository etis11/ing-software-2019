package network;

import controller.JsonReceiver;
import controller.LOGGER;
import exceptions.DuplicateException;
import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenRegistry {

    /**
     * singleton instance
     */
    private static TokenRegistry registry;


    private final Logger tokenRegistryLogger = Logger.getLogger(TokenRegistry.class.getName());
    /**
     * map where token and json receivers are linked
     */
    private  final ConcurrentMap<String, JsonReceiver> associatonTokenReceiver;
    /**
     * list of all the registered tokens
     */
    private  final List<String> registeredTokens;
    /**
     * map where the json receiver and the user are associated
     */
    private  final ConcurrentMap<JsonReceiver, User> receiverUser;

    private final Map<String, String> tokenToUsername;



    private TokenRegistry(){
        associatonTokenReceiver  = new ConcurrentHashMap<>();
        registeredTokens = new ArrayList<>(10);
        receiverUser = new ConcurrentHashMap<>();
        tokenToUsername = new HashMap<>();
    }

    /**
     * returns the singleton
     * @return
     */
    public static  TokenRegistry getInstance(){
        if (registry== null) {
            registry= new TokenRegistry();
        }
        return  registry;
    }

    /**
     * True if this token has been already generated by this server
     * @param token token passed by the client
     * @return
     */
    public boolean tokenAlreadyGenerated(String token) {
        return registeredTokens.contains(token);
    }

    /**
     * Tells if the username has been already registered.
     * @param name
     * @return true if some other user has already chose this username
     */
    public boolean usernameAlreadyPresent(final String name){
        return tokenToUsername.values().stream().anyMatch(username ->username.equals(name));
    }

    /**
     * returns the json receiver associated to this token
     * @param token
     * @return
     */
    public JsonReceiver getJsonReceiver(String token) {
        JsonReceiver receiver = associatonTokenReceiver.get(token);
        return receiver;
    }

    /**
     * Gets the user given it's json receiver
     * @param receiver source receiver
     * @return correspongind user
     */
    public User getJsonUserOwner(JsonReceiver receiver) {
        User owner = receiverUser.get(receiver);
        return owner;
    }

    /**
     * returns the corresponding user of this token
     * @param token key
     * @return user, can be null
     */
    public String getUsernameFromToken(String token){
        String username = tokenToUsername.get(token);
        return username;
    }



    /**
     * Adds a token to a token registry. If the token has already sent, is not added, because it's already there. This happens
     * when a client disconnects and reconnects.
     * Then, creates an association between the token and the jsonReceiver of the client.
     * If a token is already associated with a json receiver, this method fails.
     *
     * @param token        token of the client
     * @param jsonReceiver jsonReceiver of the client
     */
    public void associateTokenAndReceiver(String token, JsonReceiver jsonReceiver) {
        synchronized (registeredTokens) {
            if (associatonTokenReceiver.get(token) != null)
                throw new DuplicateException(">>> There is already a jsonReceiver associated to this token");
            if (!registeredTokens.contains(token)) registeredTokens.add(token);
            JsonReceiver test= associatonTokenReceiver.putIfAbsent(token, jsonReceiver);
            if(test!=null)
            LOGGER.LOGGER.log(Level.INFO,test.toString());
            tokenRegistryLogger.log(Level.INFO, "Associated token and json receiver");
        }
    }

    /**
     * associates the given json receiver and user. A json receiver can exist while the user is still null
     * @param jr client json receiver
     * @param user user that is associated to this
     */
    public void associateReceiverAndUser(JsonReceiver jr, User user){
        User test2 = receiverUser.putIfAbsent(jr, user);
        if(test2!=null)
            LOGGER.LOGGER.log(Level.INFO,test2.toString());
        tokenRegistryLogger.log(Level.INFO, "Associated json receiver and user " + user.getUsername());

    }

    /**
     * associated token and user
     * @param token key
     * @param username value
     */
    public void associateTokenAndUser(String token, String username){
        tokenToUsername.put(token, username);
        tokenRegistryLogger.log(Level.INFO, "Associated token and user " + username);

    }


    /**
     * removes the association between the token and the json receiver. The token is not removed.
     * @param token
     */
    public void removeTokenReceiverAssociation(String token) {
        associatonTokenReceiver.remove(token);
    }


    /**
     * given the json receiver, remove the associaton between the token and the json receiver
     * @param jsonReceiver the json receiver that has to be removed
     */
    public void removeTokenReceiverAssociation(JsonReceiver jsonReceiver){
        synchronized (registeredTokens){
            String toRemoveToken = null;
            for(String token: registeredTokens){
                if( token!= null && jsonReceiver!= null && associatonTokenReceiver.get(token) != null){
                    if (associatonTokenReceiver.get(token).equals(jsonReceiver)){
                        toRemoveToken = token;
                    }
                }
            }
            if (toRemoveToken != null){
                removeTokenReceiverAssociation(toRemoveToken);
            }
        }
    }

    /**
     * remove both the token and user
     * @param token token of the user that has to be removed
     */
    public void removeTokenToUsernameAssociation(String token){
        tokenToUsername.remove(token);
        tokenRegistryLogger.log(Level.INFO, "Removed token " + token);

    }

    /**
     * removes the association between token and user. The token is also removed
     * @param username user to be removed
     */
    public void removeTokenToUserAssociationAndToken(String username){
        String toDeleteToken = null;
        synchronized (registeredTokens){
            for(String token: registeredTokens){
                if (tokenToUsername.get(token).equals(username)){
                    tokenToUsername.remove(token, username);
                    toDeleteToken = token;
                }
            }
            if (toDeleteToken != null)
                registeredTokens.remove(toDeleteToken);
        }
    }

    public void removeReceiverUserAssociation(JsonReceiver jsonReceiver){
        receiverUser.remove(jsonReceiver);
    }

    public void removeToken(String token){
        synchronized (registeredTokens){
            registeredTokens.remove(token);
        }
    }


    /**
     * Used in the login phase, creates a momentary association between a token and a fake user
     * @param token
     * @param name
     * @return
     */
    public boolean checkAndInsertUsername(String token, final String name) {
        if (usernameAlreadyPresent(name))
            return true;
        else {
            tokenToUsername.put(token, name);
            return false;
        }
    }

}
