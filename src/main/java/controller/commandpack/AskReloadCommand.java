package controller.commandpack;

import view.MessageListener;
import model.Match;

import java.util.List;

/**
 * AskReloadCommand is an instance of command pattern
 * to ask if the player are allowed to reload in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskReloadCommand extends AbstractCommand{

    public AskReloadCommand(Match match, MessageListener originView, List<MessageListener> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!match.getCurrentPlayer().getState().canReload()){
            originView.notify("non puoi ricaricare");
        }
        else {
            match.getCurrentPlayer().getState().nextState("Reload", match.getCurrentPlayer());
            //TODO notify al player le armi che ha a disposizione, la notific a tutti va fatta anche al player che raccoglie
            String message = "Il giocatore attuale sta ricaricando";
            for (MessageListener view : allViews){
                view.notify(message);
            }
        }
    }
}
