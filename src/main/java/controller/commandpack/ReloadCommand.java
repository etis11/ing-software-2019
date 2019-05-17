package controller.commandpack;

import view.MessageListener;
import exceptions.InsufficientAmmoException;
import model.Match;
import model.WeaponCard;

import java.util.List;

public class ReloadCommand extends AbstractCommand {

    private String weaponName;

    public ReloadCommand(Match match, MessageListener originView, List<MessageListener> allViews, String weaponName){
        super(match, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute() {
        if (weaponName == null) throw new IllegalArgumentException("no weapon selected");
        for (WeaponCard wpc : match.getCurrentPlayer().getWeapons()){
            if (wpc.getName().equals(weaponName)){
                try {
                    wpc.reload(match.getCurrentPlayer().getPlayerBoard().getLoader());
                    String message = "Il giocatore attuale ha ricaricato: "+wpc.getName();
                    for (MessageListener view : allViews){
                        view.notify(message);
                    }
                    originView.notify("vuoi ricaricare un'altra arma oppure finire il turno?");
                } catch (InsufficientAmmoException e) {
                    originView.notify("Non hai le munizioni necessarie per ricaricare ques'arma, vuoi ricaricare un'altra arma oppure finire il turno?");
                }
            }
        }
    }
}
