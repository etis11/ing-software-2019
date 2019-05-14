package controller.commandpack;

import model.WeaponCard;
import view.AbstractView;
import model.Match;

import java.util.List;

/**
 * AskShootCommand is an instance of command pattern
 * to ask if the player are allowed to shoot someone in his state of game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AskShootCommand extends AbstractCommand{

    public AskShootCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * execute the validation of command and notify to views what happen
     * (only the player who calls if is not allowed, all if he is allowed)
     */
    @Override
    public void execute() {
        boolean loaded = false;
        //verify if almost a weapon is loaded
        for (WeaponCard wpc : match.getCurrentPlayer().getWeapons()){
            if(wpc.isLoaded()){
                loaded = true;
            }
        }
        if (!match.getCurrentPlayer().getState().canShoot() && match.getCurrentPlayer().getRemainingMoves()>0 && loaded){
            originView.notify("Non puoi sparare");
        }
        else {
            match.getCurrentPlayer().getState().nextState("Shoot", match.getCurrentPlayer());
            String message = "Il giocatore attuale sta per sparare";
            for (AbstractView view : allViews){
                view.notify(message);
            }
            //TODO notifico già le armi?
        }
    }
}
