package controller.commandpack;

import model.GameManager;
import view.MessageListener;
import model.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 * MoveCommand is an instance of command pattern
 * it allows player to move through tiles
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MoveCommand extends AbstractCommand {

    /**
     * list containing moves to construct a movement
     */
    private List<String> moves;

    public MoveCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, List<String> moves){
        super(gameManager, originView, allViews);
        this.moves = moves;
    }

    /**
     * execute the validation of command and notify to views what happen
     *(only the player who calls if is not allowed, all if he is allowed)
     * in case of positive validation provide to update the remaining moves and moves the player
     */
    @Override
    public void execute() {
        if (gameManager.getMatch().getCurrentPlayer().getState().getRemainingSteps()<moves.size()){
            originView.notify("Non hai abbastanze mosse rimanenti");
        }
        else {
            gameManager.getMatch().getCurrentPlayer().getState().decrementRemainingSteps(moves.size());
            gameManager.getMatch().getCurrentPlayer().move(new Movement(new ArrayList<String>(moves)));
            String message = "Il giocatore attuale si è spostato di: "+moves.size()+" mosse";
            for (MessageListener view : allViews){
                view.notify(message);
            }
        }
        //TODO impongo di dare una volta solo movimento o più volte? perche devo gestire il moviemnto nei vari casi e decidere in che stato portare il giocatore
        if(gameManager.getMatch().getCurrentPlayer().getState().getName().equals("Run")){
            endCommandToAction();//nel caso non puoi immetere più di una volta la coordinata
        }
    }
}
