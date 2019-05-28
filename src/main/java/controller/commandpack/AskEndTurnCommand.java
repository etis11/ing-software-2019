package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskEndTurnCommand is an instance of command pattern
 * to ask if the player are allowed to go to the end turn state
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskEndTurnCommand extends AbstractCommand {

    public AskEndTurnCommand(MessageListener originView, List<MessageListener> allViews){
        super(originView, allViews);
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
