package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskPickCommand is an instance of command pattern
 * to ask if the player are allowed to pick up something in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskPickCommand extends AbstractCommand {

    public AskPickCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        if (!gameManager.getMatch().getCurrentPlayer().getState().canPickUp() || gameManager.getMatch().getCurrentPlayer().getRemainingMoves()<1){
            originView.notify("Non puoi raccogliere");
        }
        else {
            gameManager.getMatch().getCurrentPlayer().setOldState(gameManager.getMatch().getCurrentPlayer().getState());
            gameManager.getMatch().getCurrentPlayer().getState().nextState("PickUp", gameManager.getMatch().getCurrentPlayer());
            String message = "Il giocatore attuale sta raccogliendo";
            for (MessageListener view : allViews){
                if (view!=originView) {
                    view.notify(message);
                }
            }
            originView.notify("Se vuoi spostarti inserisci la direzione, altrimenti inserisci cosa vuoi raccogliere? (Munizioni o armi)");

        }
    }
}
