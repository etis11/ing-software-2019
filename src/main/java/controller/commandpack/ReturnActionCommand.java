package controller.commandpack;

import View.AbstractView;
import model.Match;

import java.util.List;

/**
 * ReturnActionCommand is an instance of command pattern
 * to put the player in his type of choosing action state
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class ReturnActionCommand extends AbstractCommand {

    public ReturnActionCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     *checks player damage to put into his type of choosing action state
     */
    @Override
    public void execute() {
        if (match.getCurrentPlayer().getPlayerBoard().getNumDamagePoints()<3){
            match.getCurrentPlayer().getState().nextState("NormalAction", match.getCurrentPlayer());
        }
        else if (match.getCurrentPlayer().getPlayerBoard().getNumDamagePoints()<6){
            match.getCurrentPlayer().getState().nextState("MoreAction", match.getCurrentPlayer());
        }
        else{
            match.getCurrentPlayer().getState().nextState("MostAction", match.getCurrentPlayer());
        }
        match.getCurrentPlayer().decrementMoves();
    }

}
