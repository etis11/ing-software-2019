package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.Player;
import view.MessageListener;

import java.util.List;

public class SetNumberOfDeathCommand extends AbstractCommand {

    private int death;
    private Player owner;

    public SetNumberOfDeathCommand(MessageListener originView, List<MessageListener> allViews, int num, Player owner){
        super(originView, allViews);
        this.death = num;
        this.owner = owner;
    }

    public Player getOwner(){
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
