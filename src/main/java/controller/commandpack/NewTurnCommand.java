package controller.commandpack;

import View.AbstractView;
import model.Match;

import java.util.List;

public class NewTurnCommand extends AbstractCommand {

    public NewTurnCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     *
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
        //TODO ELIMINARE TUTTO
    }

}
