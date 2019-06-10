package controller.commandpack;

import controller.CommandExecutor;

public class SetTokenCommand extends AbstractCommand {

    String playerToken;

    public SetTokenCommand(String token, String playerToken) {
        super(token);
        this.playerToken = playerToken;
    }

    public String getPlayerToken() {
        return playerToken;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
