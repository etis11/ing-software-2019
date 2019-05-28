package controller.commandpack;

import model.GameManager;
import model.Player;
import view.MessageListener;

import java.util.List;

public class AskPointsCommand extends Command {

    private Player player;

    public AskPointsCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, Player player){
        super(gameManager, originView, allViews);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player == null) throw new IllegalArgumentException("Player can't be null");
        int points = player.getPoints();
        originView.notify(gameManager.getMatch().getCurrentPlayer().getName()+ " hai: "+points+ "punti");
    }
}
