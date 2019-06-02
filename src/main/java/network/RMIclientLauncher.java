package network;

import controller.CommandLauncherInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIclientLauncher {

    public static void main(String[] args){
        try{

            Registry registry = LocateRegistry.getRegistry();
            CommandLauncherInterface launcher = (CommandLauncherInterface) registry.lookup("commandLauncher");



        }
        catch (Exception r){
            System.out.println(r.getMessage());
            r.printStackTrace();
        }
    }
}
