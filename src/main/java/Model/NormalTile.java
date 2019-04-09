package Model;

import java.util.List;

public class NormalTile extends Tile {


    private boolean isPresentAmmo;
    //appounto di oscar. Un tile può contenere solo un ammocard, non ha senso che sia una list
    private List<AmmoCard> ammoCard;

    public List<AmmoCard> getAmmoCard() {
        return ammoCard;
    }

    public void putAmmoCard(List<AmmoCard> ammoCard) {
        this.ammoCard = ammoCard;
    }

    public boolean isPresentAmmoCard() {
        return isPresentAmmo;
    }

    @Override
    public List<WeaponCard> getWeapons() {
        //TODO
        return null;
    }

    @Override
    public boolean arePresentWeapons() {
        //TODO
        return false;
    }

    @Override
    public void putWeaponCard(WeaponCard w) {
        //TODO
    }

}
