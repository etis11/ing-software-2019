package controller.commandpack;

import controller.CommandExecutor;
import view.MessageListener;

import java.util.List;

public class CreateUserCommand extends AbstractCommand {

    private String username;

    public CreateUserCommand(MessageListener originView, List<MessageListener> allViews, String username){
        super(originView, allViews);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
