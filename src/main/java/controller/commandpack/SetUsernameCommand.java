package controller.commandpack;

import model.GameManager;
import model.User;
import view.MessageListener;

import java.util.List;

public class SetUsernameCommand extends AbstractCommand {

    private String username;
    private User user;

    public SetUsernameCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, User user, String username){
        super(gameManager, originView, allViews);
        this.username = username;
        this.user = user;
    }

    @Override
    public void execute() {
        if (gameManager.getLobby().getUsers().contains(user)){
            user.setUsername(username);
            //TODO controllare non sia iniziata la partita?
            originView.notify("Il tuo username è stato modificato in: "+username);
        }
        else{
            originView.notify("Non puoi modificare il tuo username");
        }
    }
}
