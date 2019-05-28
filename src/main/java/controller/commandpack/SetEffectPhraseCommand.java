package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetEffectPhraseCommand extends AbstractCommand {

    private String phrase;
    private User user;

    public SetEffectPhraseCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, User user, String phrase){
        super(gameManager, originView, allViews);
        this.phrase = phrase;
        this.user = user;
    }

    @Override
    public void execute(CommandExecutor exe) {
        if (gameManager.getLobby().getUsers().contains(user)){
            user.setEffectPhrase(phrase);
            //TODO controllare non sia iniziata la partita?
            originView.notify("La tua frase ad effetto è stata modificata");
        }
        else{
            originView.notify("Non puoi modificare la tua frase ad effetto");
        }
    }
}
