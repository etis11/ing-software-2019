package controller.commandpack;

import model.Match;
import model.Player;
import view.MessageListener;

import java.util.List;

public class AskPointsCommand extends AbstractCommand {

    private Player player;

    public AskPointsCommand(Match match, MessageListener originView, List<MessageListener> allViews, Player player){
        super(match, originView, allViews);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player == null) throw new IllegalArgumentException("Player can't be null");
        int points = player.getPoints();
        originView.notify(match.getCurrentPlayer().getName()+ " hai: "+points+ "punti");
    }
}
