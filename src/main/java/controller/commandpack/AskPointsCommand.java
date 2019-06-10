package controller.commandpack;

import controller.CommandExecutor;

public class AskPointsCommand extends AbstractCommand {


    public AskPointsCommand(String token) {
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
