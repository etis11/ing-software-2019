package controller.commandpack;

import controller.CommandExecutor;
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

    public MoveCommand(MessageListener originView, List<MessageListener> allViews, List<String> moves){
        super(originView, allViews);
        this.moves = moves;
    }

    public List<String> getMoves() {
        return moves;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
