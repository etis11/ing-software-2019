package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;

import java.io.Serializable;
import java.util.List;

public interface Command extends Serializable {
    void execute(CommandExecutor exe);

    void setJsonReceiver();

    void setAllJsonReceivers(List<JsonReceiver> receivers);
}