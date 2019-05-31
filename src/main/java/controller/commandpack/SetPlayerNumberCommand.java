package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetPlayerNumberCommand extends AbstractCommand {

    private int players;
    private User owner;

    public SetPlayerNumberCommand(MessageListener originView, List<MessageListener> allViews, int players, User owner){
        super(originView, allViews);
        this.players = players;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
