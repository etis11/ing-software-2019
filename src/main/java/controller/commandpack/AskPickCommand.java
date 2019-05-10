package controller.commandpack;

import View.AbstractView;
import model.Match;

import java.util.List;

/**
 * AskPickCommand is an instance of command pattern
 * to ask if the player are allowed to pick up something in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskPickCommand extends AbstractCommand {

    public AskPickCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canPickUp() && match.getCurrentPlayer().getRemainingMoves()>0){
            //TODO originView.onfailure();
        }
        else {
            match.getCurrentPlayer().getState().nextState("PickUp", match.getCurrentPlayer());
            //TODO allViews
        }
    }
}