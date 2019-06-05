package controller.commandpack;

import controller.CommandExecutor;

/**
 * AskReloadCommand is an instance of command pattern
 * to ask if the player are allowed to reload in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskReloadCommand extends AbstractCommand {

    public AskReloadCommand(String token){
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
