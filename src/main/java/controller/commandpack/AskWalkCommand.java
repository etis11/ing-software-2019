package controller.commandpack;

import view.AbstractView;
import model.Match;

import java.util.List;

/**
 * AskWalkCommand is an instance of command pattern
 * to ask if the player are allowed to walk somewhere in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskWalkCommand extends AbstractCommand {

    public AskWalkCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canRun() && match.getCurrentPlayer().getRemainingMoves()>0){
            originView.notify("non puoi spostarti");
        }
        else {
            match.getCurrentPlayer().getState().nextState("Run", match.getCurrentPlayer());
            String message = "Il giocatore attuale si sta spostando";
            for (AbstractView view : allViews){
                view.notify(message);
            }
        }
    }
}
