package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;
import java.util.List;

public class MoveTargetCommand extends AbstractCommand {

    private List<String> moves;

    public MoveTargetCommand(String token, List<String> moves){
        super(token);
        this.moves = moves;
    }

    public List<String> getMoves(){
        return moves;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
