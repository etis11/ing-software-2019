package controller.commandpack;

import controller.CommandExecutor;
import model.Player;
import view.MessageListener;

import java.util.List;

public class AskPointsCommand extends AbstractCommand {

    private Player player;

    public AskPointsCommand(MessageListener originView, List<MessageListener> allViews, Player player){
        super(originView, allViews);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
