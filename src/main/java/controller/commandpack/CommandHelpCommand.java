package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

public class CommandHelpCommand extends Command {

    private List<String> command;

    public CommandHelpCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, List<String> command){
        super(gameManager, originView, allViews);
        this.command = command;
    }

    @Override
    public void execute() {
        originView.notify(command.toString());
    }
}
