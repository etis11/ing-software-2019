package controller.commandpack;

import controller.CommandExecutor;

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

    public MoveCommand(String token, List<String> moves) {
        super(token);
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
