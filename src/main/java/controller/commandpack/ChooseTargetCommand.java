package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;
import java.util.List;

public class ChooseTargetCommand extends AbstractCommand {

    List<String> target;

    public ChooseTargetCommand(String command, List<String> target){
        super(command);
        this.target = target;
    }

    public List<String> getTarget(){
        return target;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
