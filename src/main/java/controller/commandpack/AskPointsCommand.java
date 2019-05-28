package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.Player;
import view.MessageListener;

import java.util.List;

public class AskPointsCommand extends AbstractCommand {

    private Player player;

    public AskPointsCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, Player player){
        super(gameManager, originView, allViews);
        this.player = player;
    }

    @Override
    public void execute(CommandExecutor exe) {
        if (player == null) throw new IllegalArgumentException("Player can't be null");
        int points = player.getPoints();
        originView.notify(gameManager.getMatch().getCurrentPlayer().getName()+ " hai: "+points+ "punti");
    }
}
