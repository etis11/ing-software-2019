package network;

import controller.JsonReceiver;
import exceptions.DuplicateException;
import model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    private  final ConcurrentMap<String, JsonReceiver> tokenAssociated;
    /**
     * list of all the registered tokens
     */
    private  final List<String> registeredTokens;
    /**
     * map where the json receiver and the user are associated
     */
    private  final ConcurrentMap<JsonReceiver, User> userReceiver;

    private final ConcurrentMap<String, User> tokenToUser;

    private final List<String> userNames;


    private TokenRegistry(){
        tokenAssociated  = new ConcurrentHashMap<>();
        registeredTokens = new ArrayList<>(10);
        userReceiver = new ConcurrentHashMap<>();
        tokenToUser = new ConcurrentHashMap<>();
        userNames = new LinkedList<>();
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
            if (tokenAssociated.containsKey(token))
                throw new DuplicateException(">>> There is already a jsonReceiver associated to this token");
            if (!registeredTokens.contains(token)) registeredTokens.add(token);
            tokenAssociated.putIfAbsent(token, jsonReceiver);
            tokenRegistryLogger.log(Level.INFO, "Associated token and json receiver");
        }
    }

    /**
     * Saves all the tokens in the server. Still not implemented
     */
    public void saveTokens() {
        throw new UnsupportedOperationException();
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
     * returns the json receiver associated to this token
     * @param token
     * @return
     */
    public JsonReceiver getJsonReceiver(String token) {
        JsonReceiver receiver = tokenAssociated.get(token);
        if (receiver == null) throw new NullPointerException("This token is not registered");
        return receiver;
    }

    /**
     * Gets the user given it's json receiver
     * @param receiver source receiver
     * @return correspongind user
     */
    public User getJsonUserOwner(JsonReceiver receiver) {
        User owner = userReceiver.get(receiver);
        return owner;
    }

    /**
     * removes the association between the token and the json receiver. The token is not removed.
     * @param token
     */
    public void removeAssociation(String token) {
        tokenAssociated.remove(token);
    }

    public boolean usernameAlreadyPresent(final String name){
        return userReceiver.values().stream().anyMatch(user->user.getUsername().equals(name));
    }

    /**
     * Used in the login phase, creates a momentary association between a token and a fake user
     * @param token
     * @param name
     * @return
     */
    public boolean checkAndInsertUsername(String token, final String name) {
        if (userReceiver.values().stream().anyMatch(user -> user.getUsername().equals(name)))
            return true;
        else {
            tokenToUser.put(token, new User(name));
            return false;
        }
    }


    /**
     * associates the given json receiver and user. A json receiver can exist while the user is still null
     * @param jr client json receiver
     * @param user user that is associated to this
     */
    public void associateReceiverAndUser(JsonReceiver jr, User user){
            userReceiver.putIfAbsent(jr, user);
        tokenRegistryLogger.log(Level.INFO, "Associated json receiver and user " + user.getUsername());

    }

    /**
     * associated token and user
     * @param token key
     * @param user value
     */
    public void associateTokenAndUser(String token, User user){
        tokenToUser.put(token, user);
        tokenRegistryLogger.log(Level.INFO, "Associated token and user " + user.getUsername());

    }

    /**
     * remove both the token and user
     * @param token token of the user that has to be removed
     */
    public void removeAssociaton(String token){
        tokenToUser.remove(token);
        tokenRegistryLogger.log(Level.INFO, "Removed token " + token);

    }

    /**
     * returns the corresponding user of this token
     * @param token key
     * @return user, can be null
     */
    public User getUserFromToken(String token){
        return tokenToUser.get(token);
    }
}
