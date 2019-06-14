package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Command extends Serializable {
    void execute(CommandExecutor exe) throws IOException;

    void setJsonReceiver(JsonReceiver jsonReceiver);

    void setAllJsonReceivers(List<JsonReceiver> receivers);

    String getToken();
}