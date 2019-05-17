package controller.commandpack;

import view.MessageListener;
import model.AmmoCard;
import model.Match;

import java.util.List;

public class PickUpAmmoCommand extends AbstractCommand{

    public PickUpAmmoCommand(Match match, MessageListener originView, List<MessageListener> allViews){
        super(match, originView, allViews);
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute() {
        if (match.getCurrentPlayer().getState().getName().equals("PickUp")){
            //TODO verifica tile giusto
            //set remaining steps to zero
            match.getCurrentPlayer().getState().remainingStepsToZero();

            AmmoCard ammoCard = match.getCurrentPlayer().getTile().pickUpAmmoCard();
            //draw
            match.getCurrentPlayer().useAmmoCard(ammoCard, match.getPowerUpDeck());
            //put the card in the slush pile
            match.getAmmoSlushPile().addCard(ammoCard);

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
