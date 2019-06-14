package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class AskPointsCommand extends AbstractCommand {


    public AskPointsCommand(String token) {
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
