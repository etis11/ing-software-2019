package network;

import controller.JsonReceiver;
import controller.JsonUnwrapper;
import exceptions.DuplicateException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class TokenRegistry {

    private static final ConcurrentMap<String, JsonReceiver> tokens = new ConcurrentHashMap<>();

    /**
     * Adds an association between a token and it's json receiver
     * @param token token of the client
     * @param jsonReceiver jsonReceiver of the client
     */
    public static void associateTokenAndReceiver(String token, JsonReceiver jsonReceiver){
        if (tokens.containsKey(token)) throw new DuplicateException(">>> There is already a jsonReceiver associated to this token");
        tokens.putIfAbsent(token, jsonReceiver);
    }

    /**
     * removes the association between the token and the json receiver
     * @param token
     */
    public void removeAssociation(String token){
        tokens.remove(token);
    }

}
