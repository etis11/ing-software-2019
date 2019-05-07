package controller.commandpack;

import View.AbstractView;
import model.Match;

import java.util.List;

public class AskWalkCommand extends AbstractCommand {

    public AskWalkCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canRun()){
            //TODO originView.onfailure();
        }
        else {
            match.getCurrentPlayer().getState().nextState("Walk", match.getCurrentPlayer());
            //TODO allViews.onfailure();
        }
    }
}
