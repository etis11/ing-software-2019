package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetNumberOfDeathCommand extends AbstractCommand {

    private int death;

    public SetNumberOfDeathCommand(String token, int num) {
        super(token);
        this.death = num;
    }

    public int getDeath() {
        return death;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
