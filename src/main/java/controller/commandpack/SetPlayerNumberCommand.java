package controller.commandpack;

import controller.CommandExecutor;

public class SetPlayerNumberCommand extends AbstractCommand {

    private int players;

    public SetPlayerNumberCommand(long token, int players){
        super(token);
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
