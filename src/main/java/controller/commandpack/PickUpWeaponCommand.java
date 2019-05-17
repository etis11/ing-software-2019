package controller.commandpack;

import model.GameManager;
import view.MessageListener;
import exceptions.PickableNotPresentException;
import model.WeaponCard;

import java.util.List;

public class PickUpWeaponCommand extends AbstractCommand {

    private String weaponName;

    public PickUpWeaponCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, String weaponName){
        super(gameManager, originView, allViews);
        this.weaponName = weaponName;
    }

    @Override
    public void execute() {
        if (gameManager.getMatch().getCurrentPlayer().getState().getName().equals("PickUp")) {
            //set player remaining steps to zero
            gameManager.getMatch().getCurrentPlayer().getState().remainingStepsToZero();

            WeaponCard weaponCard = null;
            int count = 0;
            if (weaponName == null) throw new IllegalArgumentException("can't insert null weapon");
            try {
                for (WeaponCard wpc : gameManager.getMatch().getCurrentPlayer().getTile().getWeapons()) {
                    if (wpc.getName().equals(weaponName)) {
                        weaponCard = gameManager.getMatch().getCurrentPlayer().getTile().getWeapons().remove(count);
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
                    gameManager.getMatch().getCurrentPlayer().pickUpWeapon(weaponCard);
                } catch (Exception e) {
                    originView.notify("hai più armi di quante consentite, scegline una da scartare tra: "+gameManager.getMatch().getCurrentPlayer().weaponsToString());
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
