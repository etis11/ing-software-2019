package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

public class SetPlayerNumberCommand extends AbstractCommand {

    private int players;

    public SetPlayerNumberCommand(MessageListener originView, List<MessageListener> allViews, int players){
        super(originView, allViews);
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
