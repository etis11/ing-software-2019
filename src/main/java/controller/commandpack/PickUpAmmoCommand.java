package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;
import model.AmmoCard;

import java.util.List;

public class PickUpAmmoCommand extends AbstractCommand {

    public PickUpAmmoCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        super(gameManager, originView, allViews);
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute(CommandExecutor exe) {
        if (gameManager.getMatch().getCurrentPlayer().getState().getName().equals("PickUp")){
            //TODO verifica tile giusto
            //set remaining steps to zero
            gameManager.getMatch().getCurrentPlayer().getState().remainingStepsToZero();

            AmmoCard ammoCard = gameManager.getMatch().getCurrentPlayer().getTile().pickUpAmmoCard();
            //draw
            gameManager.getMatch().getCurrentPlayer().useAmmoCard(ammoCard, gameManager.getMatch().getPowerUpDeck());
            //put the card in the slush pile
            gameManager.getMatch().getAmmoSlushPile().addCard(ammoCard);

            //notify
            String message = "Il giocatore attuale ha raccolto una carta munizioni";
            for (MessageListener view : allViews){
                view.notify(message);
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
