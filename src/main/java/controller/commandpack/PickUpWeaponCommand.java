package controller.commandpack;

import View.AbstractView;
import exceptions.PickableNotPresentException;
import model.Match;
import model.WeaponCard;

import java.util.List;

public class PickUpWeaponCommand extends AbstractCommand {

    private String weaponName;

    public PickUpWeaponCommand(Match match, AbstractView originView, List<AbstractView> allViews, String weaponName){
        super(match, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute() {
        //TODO verifica sottostati precedenti
        WeaponCard weaponCard = null;
        if (weaponName == null) throw new IllegalArgumentException("can't insert null weapon");
        try {
            for (WeaponCard wpc : match.getCurrentPlayer().getTile().getWeapons()) {
                if (wpc.getNAME().equals(weaponName)){
                    //TODO weaponCard = match.getCurrentPlayer().getTile().getWeapons().prindilacarta
                }
            }
        } catch (PickableNotPresentException e) {
            e.printStackTrace();
        }
        if(weaponCard == null){
            //TODO notify non è un'arma tra le presenti
        }
        else{
            try {
                match.getCurrentPlayer().pickUpWeapon(weaponCard);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO notifico la necessità di eliminare una carta ma come gestisco la cosa?????
            }
            finally {
                //TODO notify
            }
        }


        //TODO match.getCurrentPlayer().getState().nextState(match.getCurrentPlayer().getState().getPossibleNextStates()., match.getCurrentPlayer());
        match.getCurrentPlayer().setOldState(null);
    }
}
