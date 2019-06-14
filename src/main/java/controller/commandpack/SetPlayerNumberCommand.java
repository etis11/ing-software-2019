package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetPlayerNumberCommand extends AbstractCommand {

    private int players;

    public SetPlayerNumberCommand(String token, int players) {
        super(token);
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
