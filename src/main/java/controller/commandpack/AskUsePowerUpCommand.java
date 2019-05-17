package controller.commandpack;

import view.MessageListener;
import model.Match;

import java.util.List;

/**
 * AskUsePowerUpCommand is an instance of command pattern
 * to ask if the player are allowed to power up in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskUsePowerUpCommand extends AbstractCommand {

    public AskUsePowerUpCommand(Match match, MessageListener originView, List<MessageListener> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canUsePowerUp() || match.getCurrentPlayer().getPowerUps().isEmpty()){
            originView.notify("Non puoi usare powerup");
        }
        else {
            //match.getCurrentPlayer().getState().nextState("PowerUp", match.getCurrentPlayer());
            String message = "Il giocatore attuale sta usando un power up";
            for (MessageListener view : allViews){
                view.notify(message);
            }
            originView.notify("Scegli quale power up usare tra: "+match.getCurrentPlayer().powerUpToString());
        }
    }
}
