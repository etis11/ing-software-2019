package network.RMI;

import controller.CommandLauncherInterface;
import controller.commandpack.AskPickCommand;
import controller.commandpack.AskWalkCommand;
import controller.commandpack.MoveCommand;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Thread.sleep;

public class ClientLauncherRMI {
    public static void main(String[] args) throws Exception {
        CommandLauncherInterface launcher = null;
        try{

            Registry registry = LocateRegistry.getRegistry();
            ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
            launcher = serverRMI.getCurrentCommandLauncher();
        }
        catch (Exception r){
            System.out.println(r.getMessage());
            r.printStackTrace();
        }
        if (launcher!= null){
            AskPickCommand c = new AskPickCommand(null, null);
            launcher.addCommand(c);
            MoveCommand m = new MoveCommand(null, null, null);
            launcher.addCommand(m);
            sleep(10000);
            AskWalkCommand w = new AskWalkCommand(null, null);
            launcher.addCommand(w);
            sleep(10000);
        }
        System.out.println(">>> Chiudo RMI client");


    }
}
