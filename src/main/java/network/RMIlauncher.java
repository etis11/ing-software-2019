package network;

import controller.CommandLauncher;
import controller.CommandLauncherInterface;
import model.GameManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIlauncher {

    public static void main(String[] args) {
        GameManager gm = new GameManager();
       try{
           CommandLauncher launcher = new CommandLauncher(gm);
           //sta roba la posso anche fare nel command launcher direttamente
           CommandLauncherInterface stub = (CommandLauncherInterface) UnicastRemoteObject.exportObject(launcher, 0);

           //need to bind the stub on the registry
           Registry registry = LocateRegistry.getRegistry();
           registry.bind("commandLauncher", stub);
       }
       catch (Exception e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
    }
}
