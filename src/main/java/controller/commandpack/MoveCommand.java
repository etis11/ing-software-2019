package controller.commandpack;

import view.AbstractView;
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

    public MoveCommand(Match match, AbstractView originView, List<AbstractView> allViews, List<String> moves){
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
        //TODO modificare condizione se ho superato i controlli dei sottostati precedenti
        if (match.getCurrentPlayer().getState().getRemainingSteps()<moves.size()){
            //TODO originView.onfailure();
        }
        else {
            match.getCurrentPlayer().getState().decrementReaminingSteps(moves.size());
            match.getCurrentPlayer().move(new Movement(new ArrayList<String>(moves)));
            //TODO allViews
        }
    }
}
