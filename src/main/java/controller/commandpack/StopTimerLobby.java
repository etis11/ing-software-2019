package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class StopTimerLobby extends AbstractCommand{

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
