package controller.commandpack;

import controller.CommandExecutor;
import view.MessageListener;

import java.util.List;

public class SetTokenCommand extends AbstractCommand {

    String token;

    public SetTokenCommand(MessageListener originView, List<MessageListener> allViews, String token){
        super(originView, allViews);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
