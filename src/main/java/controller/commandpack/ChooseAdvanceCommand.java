package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class ChooseAdvanceCommand extends AbstractCommand {

    private String typeBase;

    public ChooseAdvanceCommand(String token, String typeBase){
        super(token);
        this.typeBase = typeBase;
    }

    public String getTypeBase(){
        return typeBase;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
