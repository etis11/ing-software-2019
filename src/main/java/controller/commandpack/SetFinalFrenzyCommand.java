package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetFinalFrenzyCommand extends AbstractCommand {

    private boolean frenzy;

    public SetFinalFrenzyCommand(String token, boolean frenzy) {
        super(token);
        this.frenzy = frenzy;
    }

    public boolean getFrenzy(){
        return frenzy;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
