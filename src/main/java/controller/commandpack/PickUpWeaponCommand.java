package controller.commandpack;

import view.MessageListener;
import exceptions.PickableNotPresentException;
import model.Match;
import model.WeaponCard;

import java.util.List;

public class PickUpWeaponCommand extends AbstractCommand {

    private String weaponName;

    public PickUpWeaponCommand(Match match, MessageListener originView, List<MessageListener> allViews, String weaponName){
        super(match, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute() {
        if (match.getCurrentPlayer().getState().getName().equals("PickUp")) {
            //set player remaining steps to zero
            match.getCurrentPlayer().getState().remainingStepsToZero();

            WeaponCard weaponCard = null;
            int count = 0;
            if (weaponName == null) throw new IllegalArgumentException("can't insert null weapon");
            try {
                for (WeaponCard wpc : match.getCurrentPlayer().getTile().getWeapons()) {
                    if (wpc.getName().equals(weaponName)) {
                        weaponCard = match.getCurrentPlayer().getTile().getWeapons().remove(count);
                    }
                    count++;
                }
            } catch (PickableNotPresentException e) {
                originView.notify("non sei in un riquadro contenente armi");
            }
            //TODO come gestisco se voglio raccogleire arma ma non sono nel tile giusto?
            if (weaponCard == null) {
                originView.notify(weaponName + " non è tra le armi presenti nel tuo riquadro");
            } else {
                try {
                    match.getCurrentPlayer().pickUpWeapon(weaponCard);
                } catch (Exception e) {
                    originView.notify("hai più armi di quante consentite, scegline una da scartare tra: "+match.getCurrentPlayer().weaponsToString());
                } finally {
                    String message = "Il giocatore attuale ha raccolto: " + weaponCard.getName();
                    for (MessageListener view : allViews) {
                        view.notify(message);
                    }
                }

            }

            //decrement moves of player and return to action selector
            endCommandToAction();
        }
        else{
            originView.notify("Comando non valido");
            //TODO o lancio exception??
        }
    }
}
