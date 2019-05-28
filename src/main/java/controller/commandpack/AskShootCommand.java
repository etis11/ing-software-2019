package controller.commandpack;

import model.GameManager;
import model.WeaponCard;
import view.MessageListener;

import java.util.List;

/**
 * AskShootCommand is an instance of command pattern
 * to ask if the player are allowed to shoot someone in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskShootCommand extends AbstractCommand {

    public AskShootCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        boolean loaded = false;
        //verify if almost a weapon is loaded
        for (WeaponCard wpc : gameManager.getMatch().getCurrentPlayer().getWeapons()){
            if(wpc.isLoaded()){
                loaded = true;
            }
        }
        if (!gameManager.getMatch().getCurrentPlayer().getState().canShoot() || gameManager.getMatch().getCurrentPlayer().getRemainingMoves()<1 || !loaded){
            originView.notify("Non puoi sparare");
        }
        else {
            gameManager.getMatch().getCurrentPlayer().getState().nextState("Shoot", gameManager.getMatch().getCurrentPlayer());
            String message = "Il giocatore attuale sta per sparare";
            for (MessageListener view : allViews){
                view.notify(message);
            }
            //TODO notifico già le armi?
        }
    }
}
