package controller.commandpack;

import view.AbstractView;
import model.Match;

import java.util.List;

/**
 * AskEndTurnCommand is an instance of command pattern
 * to ask if the player are allowed to go to the end turn state
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskEndTurnCommand extends AbstractCommand {

    public AskEndTurnCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        //TODO rivedere la condizione
        if (!match.getCurrentPlayer().getState().isNormalAction() || !match.getCurrentPlayer().getState().isMoreAction() ||!match.getCurrentPlayer().getState().isMostAction() ){
            //TODO originView.onfailure();
        }
        else {
            match.getCurrentPlayer().getState().nextState("EndTurn", match.getCurrentPlayer());
            //TODO allViews
        }
    }
}
