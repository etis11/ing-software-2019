package network.RMI;

import controller.CommandLauncherInterface;
import controller.JsonReceiver;
import controller.JsonUnwrapper;
import controller.LOGGER;
import controller.commandpack.AskPickCommand;
import controller.commandpack.AskWalkCommand;
import controller.commandpack.MoveCommand;
import model.clientModel.SemplifiedGame;
import view.ClientSingleton;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

public class ClientLauncherRMI {
    public static void main(String[] args) throws Exception {
        CommandLauncherInterface launcher = null;
        String token = "";
        //todo
        JsonReceiver receiver = new JsonUnwrapper(new SemplifiedGame());
        UnicastRemoteObject.exportObject(receiver, 0);
        try {

            Registry registry = LocateRegistry.getRegistry();


            ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
            //token = serverRMI.getPersonalToken(receiver, "");
            ClientSingleton.getInstance().setToken(token);
            launcher = serverRMI.getCurrentCommandLauncher(receiver);
        } catch (Exception r) {
            LOGGER.LOGGER.log(Level.WARNING,r.getMessage());
            LOGGER.LOGGER.log(Level.WARNING, Arrays.toString(r.getStackTrace()));

        } finally {
            if (launcher != null) {
                AskPickCommand c = new AskPickCommand(token);
                launcher.addCommand(c);
                MoveCommand m = new MoveCommand(token, null);
                launcher.addCommand(m);
                sleep(2000);
                AskWalkCommand w = new AskWalkCommand(token);
                launcher.addCommand(w);
                sleep(2000);
            }
            System.out.println(">>> Chiudo RMI client");
            UnicastRemoteObject.unexportObject(receiver, true);
        }
    }
}
