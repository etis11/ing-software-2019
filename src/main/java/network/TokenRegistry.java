package network;

import controller.JsonReceiver;
import exceptions.DuplicateException;
import model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TokenRegistry {

    private static TokenRegistry registry;

    private  final ConcurrentMap<String, JsonReceiver> tokenAssociated;
    private  final List<String> registeredTokens;
    private  final ConcurrentMap<JsonReceiver, User> userReceiver;
    //private static final Object registeredTokensLock = new Object();

    private TokenRegistry(){
        tokenAssociated  = new ConcurrentHashMap<>();
        registeredTokens = new ArrayList<>(10);
        userReceiver = new ConcurrentHashMap<>();
    }

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
        }
    }

    /**
     * Saves all the tokens in the server. Still not implemented
     */
    public void saveTokens() {
        throw new UnsupportedOperationException();
    }

    public boolean tokenAlreadyGenerated(String token) {
        return registeredTokens.contains(token);
    }

    public JsonReceiver getJsonReceiver(String token) {
        JsonReceiver receiver = tokenAssociated.get(token);
        if (receiver == null) throw new NullPointerException("This token is not registered");
        return receiver;
    }

    public User getJsonUserOwner(JsonReceiver receiver) {
        User owner = userReceiver.get(receiver);
        return owner;
    }

    /**
     * removes the association between the token and the json receiver. The token is not removed
     *
     * @param token
     */
    public void removeAssociation(String token) {
        tokenAssociated.remove(token);
    }

    public boolean usernameAlreadyPresent(String name){
        List<User> users = new ArrayList<>(userReceiver.values());
        for(User u: users){
            if (u.getUsername().equals(name)) return true;
        }
        return false;
    }

    public void associateReceiverAndUser(JsonReceiver jr, User user){
        synchronized (userReceiver){
            userReceiver.putIfAbsent(jr, user);
        }
    }
}
