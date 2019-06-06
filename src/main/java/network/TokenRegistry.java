package network;

import controller.JsonReceiver;
import exceptions.DuplicateException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class TokenRegistry {

    private static final ConcurrentMap<String, JsonReceiver> tokenAssociated = new ConcurrentHashMap<>();
    private static final List<String> registeredTokens = new ArrayList<>(10);
    //private static final Object registeredTokensLock = new Object();

    /**
     * Adds a token to a token registry. If the token has already sent, is not added, because it's already there. This happens
     * when a client disconnects and reconnects.
     * Then, creates an association between the token and the jsonReceiver of the client.
     * If a token is already associated with a json receiver, this method fails.
     * @param token token of the client
     * @param jsonReceiver jsonReceiver of the client
     */
    public static void associateTokenAndReceiver(String token, JsonReceiver jsonReceiver){
        synchronized (registeredTokens){
            if (tokenAssociated.containsKey(token)) throw new DuplicateException(">>> There is already a jsonReceiver associated to this token");
            if (!registeredTokens.contains(token)) registeredTokens.add(token);
            tokenAssociated.putIfAbsent(token, jsonReceiver);
        }
    }

    /**
     * removes the association between the token and the json receiver. The token is not removed
     * @param token
     */
    public void removeAssociation(String token){
        tokenAssociated.remove(token);
    }

    /**
     * Saves all the tokens in the server. Still not implemented
     */
    public  static void saveTokens(){
        throw new UnsupportedOperationException();
    }
}
