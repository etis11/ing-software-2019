package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonNotifier;
import controller.JsonReceiver;
import controller.Notifier;
import javafx.print.PageLayout;
import model.GameManager;
import model.Player;
import model.User;
import network.TokenRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewTurnTimerTask extends TimerTask {

    private final Logger timerLogger = Logger.getLogger(NewTurnTimerTask.class.getName());

    private final List<JsonReceiver> allJsonReceivers;
    private final JsonReceiver currentTurnJsonReceiver;
    private final CommandExecutor executor;
    private final Notifier notifier;
    private final GameManager gameManager;

    public NewTurnTimerTask(CommandExecutor executor, List<JsonReceiver> allJsonReceivers,
                            JsonReceiver currentTurnJsonReceiver, Notifier notifier, GameManager gameManager) {
        super();
        this.executor = executor;
        this.allJsonReceivers = new ArrayList<>(allJsonReceivers);
        this.currentTurnJsonReceiver = currentTurnJsonReceiver;
        this.notifier = notifier;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        TokenRegistry registry = TokenRegistry.getInstance();
        //saves the old player
        Player oldPlayer = gameManager.getMatch().getCurrentPlayer();
        //gets te current user
        User user = registry.getJsonUserOwner(currentTurnJsonReceiver);
        //remove the association between receiver and user, and disconnects the user
        notifier.disconnectReceiver(currentTurnJsonReceiver);
        //notify to all the remaining receivers that the user has disconnected
        for(JsonReceiver js: allJsonReceivers){
            if(js != currentTurnJsonReceiver){
                Player playerToNotify= registry.getJsonUserOwner(js).getPlayer();
                notifier.notifyDisconnection(user, playerToNotify, js);
            }
        }
        //ends the round
        gameManager.getMatch().endRound();
        //creates a new turn timer
        Timer turnTimer = new Timer();
        executor.setTurnTimer(turnTimer);
        //new round, new player
        gameManager.getMatch().newRound();
        turnTimer.schedule(new NewTurnTimerTask(executor, allJsonReceivers, currentTurnJsonReceiver, notifier, gameManager),
                executor.getTurnLength() * 1000);
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        timerLogger.log(Level.INFO, oldPlayer.getName() + " disconnected. " + currentPlayer.getName() + "'s turn started");

        //notifies the start of the new turn
        JsonReceiver userToBeNotifiedThrow = null;
        for (JsonReceiver jr : allJsonReceivers) {
            User userToBeNotified = TokenRegistry.getInstance().getJsonUserOwner(jr);
            if (userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())) {
                userToBeNotifiedThrow = jr;
            }
        }

        //notifies the current player to throw the powerup in case is the furst turn
        notifier.notifyMessageTargetPlayer("", userToBeNotifiedThrow, currentPlayer);
        if ((currentPlayer.getState().getName().equals("EndTurn") && currentPlayer.getTile() == null) || currentPlayer.getState().getName().equals("Dead") || currentPlayer.getState().getName().equals("Overkilled")) {
            notifier.notifyMessageTargetPlayer("scegli quale powerup scartare per spawnare", userToBeNotifiedThrow, currentPlayer);
            timerLogger.log(Level.INFO, "Asking to throw a power up to" + currentPlayer.getName());
        }
    }
}
