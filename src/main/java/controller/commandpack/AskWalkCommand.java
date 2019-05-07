package controller.commandpack;

import controller.CommandExecutor;

public class AskWalkCommand extends AbstractCommand {

    CommandExecutor executor;

    public AskWalkCommand(CommandExecutor exe){
        this.executor = exe;
    }

    @Override
    public void execute() {
        if (!owner.getState().canRun()){

        }
        owner.getState().nextState("Walk", owner);
    }
}
