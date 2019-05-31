package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetUsernameCommand extends AbstractCommand {

    private String username;
    private User user;

    public SetUsernameCommand(MessageListener originView, List<MessageListener> allViews, User user, String username){
        super(originView, allViews);
        this.username = username;
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
