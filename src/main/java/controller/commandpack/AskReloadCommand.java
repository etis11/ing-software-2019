package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskReloadCommand is an instance of command pattern
 * to ask if the player are allowed to reload in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskReloadCommand extends AbstractCommand{

    public AskReloadCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!gameManager.getMatch().getCurrentPlayer().getState().canReload()){
            originView.notify("non puoi ricaricare");
        }
        else {
            gameManager.getMatch().getCurrentPlayer().getState().nextState("Reload", gameManager.getMatch().getCurrentPlayer());
            String message = "Il giocatore attuale sta ricaricando";
            for (MessageListener view : allViews){
                if(view!=originView) {
                    view.notify(message);
                }
            }
            originView.notify("Scegli quale arma ricaricare tra: "+gameManager.getMatch().getCurrentPlayer().weaponsToString());
        }
    }
}
