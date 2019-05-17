package controller.commandpack;

import view.MessageListener;
import model.Match;
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

    public MoveCommand(Match match, MessageListener originView, List<MessageListener> allViews, List<String> moves){
        super(match, originView, allViews);
        this.moves = moves;
        //TODO mancano controlli booleani
    }

    /**
     * execute the validation of command and notify to views what happen
     *(only the player who calls if is not allowed, all if he is allowed)
     * in case of positive validation provide to update the remaining moves and moves the player
     */
    @Override
    public void execute() {
        if (match.getCurrentPlayer().getState().getRemainingSteps()<moves.size()){
            originView.notify("Non hai abbastanze mosse rimanenti");
        }
        else {
            match.getCurrentPlayer().getState().decrementRemainingSteps(moves.size());
            match.getCurrentPlayer().move(new Movement(new ArrayList<String>(moves)));
            String message = "Il giocatore attuale si è spostato di: "+moves.size()+" mosse";
            for (MessageListener view : allViews){
                view.notify(message);
            }
        }
        //TODO impongo di dare una volta solo movimento o più volte? perche devo gestire il moviemnto nei vari casi e decidere in che stato portare il giocatore
        if(match.getCurrentPlayer().getState().getName().equals("Run")){
            endCommandToAction();//nel caso non puoi immetere più di una volta la coordinata
        }
    }
}
