package Model;

import java.util.List;

public class NormalTile extends Tile {


    private boolean ispresentammo;
    private List<AmmoCard> ammoCard;

    public List<AmmoCard> getAmmoCard() {
        return ammoCard;
    }

    public void putAmmoCard(List<AmmoCard> ammoCard) {
        this.ammoCard = ammoCard;
    }

    public boolean isPresentAmmoCard() {
        return ispresentammo;
    }

}
