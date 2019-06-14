package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.Serializable;
import java.util.List;

public interface Command extends Serializable {
    void execute(CommandExecutor exe);

    void setJsonReceiver(JsonReceiver jsonReceiver);

    void setAllJsonReceivers(List<JsonReceiver> receivers);

    String getToken();
}