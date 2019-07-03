package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.IOException;
import java.util.List;

public class StopTimerLobby extends AbstractCommand{

    public StopTimerLobby(String token){
        super(token);
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
