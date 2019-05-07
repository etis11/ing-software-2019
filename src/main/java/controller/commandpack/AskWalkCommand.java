package controller.commandpack;

import model.Match;

public class AskWalkCommand extends AbstractCommand {

    private Match match;

    public AskWalkCommand(Match match){
        this.match = match;
    }

    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canRun()){
            //TODO notify alla mia view che non posso
        }
        else {
            match.getCurrentPlayer().getState().nextState("Walk", match.getCurrentPlayer());
            //TODO notify a tutte le view che il current si muove
        }
    }
}
