package controller.commandpack;

import View.AbstractView;
import model.AmmoCard;
import model.Match;

import java.util.List;

public class PickUpAmmoCommand extends AbstractCommand{

    public PickUpAmmoCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute() {
        //TODO verifica sottostati precedenti
        AmmoCard ammoCard = match.getCurrentPlayer().getTile().pickUpAmmoCard();
        //draw
        match.getCurrentPlayer().useAmmoCard(ammoCard, match.getPowerUpDeck());
        //put the card in the slush pile
        match.getAmmoSlushPile().addCard(ammoCard);
        //TODO notify
        //TODO match.getCurrentPlayer().getState().nextState(match.getCurrentPlayer().getState().getPossibleNextStates()., match.getCurrentPlayer());
    }
}
