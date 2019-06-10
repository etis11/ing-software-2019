package controller;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this interface represent an object that wants a json element in order to do something
 */
public interface JsonReceiver  extends Remote {

    /**
     * sends a json element object
     * @param changes the json element
     * @throws RemoteException Using RMI, problem with binding
     * @throws IOException Using socket, if there are some problems writing an object in a stream
     */
    void sendJson(String changes) throws RemoteException, IOException;
}
