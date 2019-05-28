package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskReloadCommand is an instance of command pattern
 * to ask if the player are allowed to reload in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskReloadCommand extends AbstractCommand {

    public AskReloadCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
