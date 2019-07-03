package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.IOException;
import java.util.List;

public class StopTimerLobby extends AbstractCommand{

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
