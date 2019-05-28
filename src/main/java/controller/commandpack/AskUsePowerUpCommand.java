package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskUsePowerUpCommand is an instance of command pattern
 * to ask if the player are allowed to power up in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskUsePowerUpCommand extends AbstractCommand {

    public AskUsePowerUpCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute(CommandExecutor exe) {
        if (!gameManager.getMatch().getCurrentPlayer().getState().canUsePowerUp() || gameManager.getMatch().getCurrentPlayer().getPowerUps().isEmpty()){
            originView.notify("Non puoi usare powerup");
        }
        else {
            //match.getCurrentPlayer().getState().nextState("PowerUp", match.getCurrentPlayer());
            String message = "Il giocatore attuale sta usando un power up";
            for (MessageListener view : allViews){
                view.notify(message);
            }
            originView.notify("Scegli quale power up usare tra: "+gameManager.getMatch().getCurrentPlayer().powerUpToString());
        }
    }
}
