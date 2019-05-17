package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskEndTurnCommand is an instance of command pattern
 * to ask if the player are allowed to go to the end turn state
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskEndTurnCommand extends AbstractCommand {

    public AskEndTurnCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        //TODO rivedere la condizione
        if (!gameManager.getMatch().getCurrentPlayer().getState().isNormalAction() || !gameManager.getMatch().getCurrentPlayer().getState().isMoreAction() ||!gameManager.getMatch().getCurrentPlayer().getState().isMostAction() ){
            originView.notify("Non puoi terminare il tuo turno al momento");
        }
        else {
            gameManager.getMatch().getCurrentPlayer().getState().nextState("EndTurn", gameManager.getMatch().getCurrentPlayer());
            String message = "Il giocatore attuale ha terminato il suo turno";
            for (MessageListener view : allViews){
                view.notify(message);
            }
        }
    }
}
