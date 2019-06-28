package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class ChooseOptEffectCommand extends AbstractCommand{

    private String opt;

    public ChooseOptEffectCommand(String token, String opt){
        super(token);
        this.opt = opt;
    }

    public String getOpt(){
        return opt;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
