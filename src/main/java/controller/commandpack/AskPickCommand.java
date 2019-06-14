package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

/**
 * AskPickCommand is an instance of command pattern
 * to ask if the player are allowed to pick up something in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskPickCommand extends AbstractCommand {

    public AskPickCommand(String token) {
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
