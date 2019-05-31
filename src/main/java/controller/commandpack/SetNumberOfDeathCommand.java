package controller.commandpack;

import controller.CommandExecutor;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetNumberOfDeathCommand extends AbstractCommand {

    private int death;
    private User owner;

    public SetNumberOfDeathCommand(MessageListener originView, List<MessageListener> allViews, int num, User owner){
        super(originView, allViews);
        this.death = num;
        this.owner = owner;
    }

    public User getOwner(){
        return owner;
    }

    public int getDeath() {
        return death;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
