package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;
import exceptions.InsufficientAmmoException;
import model.WeaponCard;

import java.util.List;

public class ReloadCommand extends AbstractCommand {

    private String weaponName;

    public ReloadCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, String weaponName){
        super(gameManager, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute(CommandExecutor exe) {
        if (weaponName == null) throw new IllegalArgumentException("no weapon selected");
        for (WeaponCard wpc : gameManager.getMatch().getCurrentPlayer().getWeapons()){
            if (wpc.getName().equals(weaponName)){
                try {
                    wpc.reload(gameManager.getMatch().getCurrentPlayer().getPlayerBoard().getLoader());
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
