package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

/**
 * AskWalkCommand is an instance of command pattern
 * to ask if the player are allowed to walk somewhere in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskWalkCommand extends AbstractCommand {

    public AskWalkCommand(MessageListener originView, List<MessageListener> allViews){
        super(originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
