package controller.commandpack;

import View.AbstractView;
import exceptions.InsufficientAmmoException;
import model.Match;
import model.WeaponCard;

import java.util.List;

public class ReloadCommand extends AbstractCommand {

    private String weaponName;

    public ReloadCommand(Match match, AbstractView originView, List<AbstractView> allViews, String weaponName){
        super(match, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute() {
        if (weaponName == null || weaponName.isEmpty()) throw new IllegalArgumentException("no weapon selected");
        for (WeaponCard wpc : match.getCurrentPlayer().getWeapons()){
            if (wpc.getName().equals(weaponName)){
                try {
                    wpc.reload(match.getCurrentPlayer().getPlayerBoard().getLoader());
                    //TODO notify a tutti
                } catch (InsufficientAmmoException e) {
                    e.printStackTrace();
                    //TODO notify errore
                }
            }
        }
    }
}
