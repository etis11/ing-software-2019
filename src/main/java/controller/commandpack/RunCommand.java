package controller.commandpack;

import controller.AbstractCommand;
import controller.MatchCommandExecutor;
import exceptions.NotValidActionException;

public class RunCommand extends AbstractCommand {

    MatchCommandExecutor executor;

    public RunCommand(MatchCommandExecutor exe){
        this.executor = exe;
    }

    @Override
    public void execute() {
        try {
            executor.askToWalk(owner);
        } catch (NotValidActionException e) {
            e.printStackTrace();
        }
    }
}
