package controller;

import controller.commandpack.ServerEndTurnCommand;
import model.User;
import network.TokenRegistry;

import java.rmi.RemoteException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurnTimerTask extends TimerTask {

    private final Logger timerLogger = Logger.getLogger(TurnTimerTask.class.getName());

    private  final CommandLauncherInterface launcher;

    private  final JsonReceiver currentTurnReceiver;

    private final Notifier notifier;

    private final User userToDisconnect;

    public TurnTimerTask(CommandLauncherInterface launcher, JsonReceiver currentTurnReceiver, Notifier notifier, User userToDisconnect) {
        super();
        this.launcher = launcher;
        this.currentTurnReceiver = currentTurnReceiver;
        this.notifier = notifier;
        this.userToDisconnect = userToDisconnect;
    }

    @Override
    public void run() {
        TokenRegistry registry = TokenRegistry.getInstance();
        timerLogger.log(Level.INFO, "timer expired. Disconnecting user " + userToDisconnect.getUsername());


        if(currentTurnReceiver != null) {
            notifier.disconnectReceiver(currentTurnReceiver);
        }
        //ask end turn. disconnects the json receiver
        ServerEndTurnCommand endTurnCommand = new ServerEndTurnCommand("", userToDisconnect);
        try {
            launcher.addCommand(endTurnCommand);
        }
        catch (RemoteException re){
            timerLogger.log(Level.WARNING, "Should never occour");
        }
    }
}
