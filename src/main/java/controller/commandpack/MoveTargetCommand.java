package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class MoveTargetCommand extends AbstractCommand {

    public MoveTargetCommand(String token){
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
