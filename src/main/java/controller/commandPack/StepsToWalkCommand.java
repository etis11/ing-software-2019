package controller.commandPack;

import controller.AbstractCommand;
import controller.MatchCommandExecutor;
import exceptions.NotValidActionException;

public class StepsToWalkCommand extends AbstractCommand {

    MatchCommandExecutor executor;

    int steps;

    public StepsToWalkCommand(MatchCommandExecutor executor, int steps) {
        this.executor = executor;
        this.steps = steps;
    }

    @Override
    public void execute() {
        try {
            executor.stepToWalk(owner, steps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
