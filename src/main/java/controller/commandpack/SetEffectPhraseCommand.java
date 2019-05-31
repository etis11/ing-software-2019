package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetEffectPhraseCommand extends AbstractCommand {

    private String phrase;
    private User user;

    public SetEffectPhraseCommand(MessageListener originView, List<MessageListener> allViews, User user, String phrase){
        super(originView, allViews);
        this.phrase = phrase;
        this.user = user;
    }

    public String getPhrase() {
        return phrase;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
