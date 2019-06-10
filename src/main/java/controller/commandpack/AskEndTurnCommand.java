package controller.commandpack;

import controller.CommandExecutor;

/**
 * AskEndTurnCommand is an instance of command pattern
 * to ask if the player are allowed to go to the end turn state
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskEndTurnCommand extends AbstractCommand {

    public AskEndTurnCommand(String token){
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
