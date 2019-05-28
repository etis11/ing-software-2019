package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

public class CommandHelpCommand extends AbstractCommand {

    private List<String> command;

    public CommandHelpCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, List<String> command){
        super(gameManager, originView, allViews);
        this.command = command;
    }

    @Override
    public void execute(CommandExecutor exe) {
        originView.notify(command.toString());
    }
}
