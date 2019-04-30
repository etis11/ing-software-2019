package controller;

import exceptions.NotValidActionException;

public class RunCommand extends AbstractCommand {

    MatchCommandExecutor executor;

    public RunCommand(MatchCommandExecutor exe){
        this.executor = exe;
    }

    @Override
    public void execute() {
        try {
            executor.walkingRoutine(owner);
        } catch (NotValidActionException e) {
            e.printStackTrace();
        }
    }
}
