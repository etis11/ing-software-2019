package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;
import java.util.List;

public class ChooseTargetCommand extends AbstractCommand {

    private List<String> target;
    private String color;
    private String targetName;
    private String cost;

    public ChooseTargetCommand(String token, List<String> target, String color, String targetName, String cost) {
        super(token);
        this.target = target;
        this.color = color;
        this.targetName = targetName;
        this.cost = cost;
    }

    public List<String> getTarget(){
        return target;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
