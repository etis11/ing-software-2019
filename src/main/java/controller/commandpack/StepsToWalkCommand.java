package controller.commandpack;

import controller.AbstractCommand;
import controller.MatchCommandExecutor;

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
