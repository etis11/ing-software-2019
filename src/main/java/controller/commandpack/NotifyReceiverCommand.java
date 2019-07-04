package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.IOException;

public class NotifyReceiverCommand extends AbstractCommand {

    private JsonReceiver jsonReceiver;

    public NotifyReceiverCommand(String token, JsonReceiver jsonReceiver){
        super(token);

        this.jsonReceiver = jsonReceiver;
    }

    @Override
    public JsonReceiver getJsonReceiver() {
        return jsonReceiver;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
