package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

public class SetNumberOfDeathCommand extends AbstractCommand {

    private int death;

    public SetNumberOfDeathCommand(MessageListener originView, List<MessageListener> allViews, int num){
        super(originView, allViews);
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
