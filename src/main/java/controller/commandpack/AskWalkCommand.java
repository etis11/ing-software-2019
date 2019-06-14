package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

/**
 * AskWalkCommand is an instance of command pattern
 * to ask if the player are allowed to walk somewhere in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskWalkCommand extends AbstractCommand {

    public AskWalkCommand(String token) {
        super(token);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
