package controller.commandpack;

import controller.MatchCommandExecutor;

public class MoveCommand extends AbstractCommand {

    MatchCommandExecutor executor;

    int steps;

//    public MoveCommand(MatchCommandExecutor executor, int steps) {
//        this.executor = executor;
//        this.steps = steps;
//    }

    @Override
    public void execute() {
        try {
            //executor.stepToWalk(owner, steps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
