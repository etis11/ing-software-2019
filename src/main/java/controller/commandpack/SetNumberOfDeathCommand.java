package controller.commandpack;

import controller.CommandExecutor;

public class SetNumberOfDeathCommand extends AbstractCommand {

    private int death;

    public SetNumberOfDeathCommand(long token, int num){
        super(token);
        this.death = num;
    }

    public int getDeath() {
        return death;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
