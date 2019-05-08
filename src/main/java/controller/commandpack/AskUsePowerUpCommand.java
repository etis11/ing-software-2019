package controller.commandpack;

import View.AbstractView;
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

    public AskUsePowerUpCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canUsePowerUp()){
            //TODO originView.onfailure();
        }
        else {
            match.getCurrentPlayer().getState().nextState("PowerUp", match.getCurrentPlayer());
            //TODO allViews
        }
    }
}
