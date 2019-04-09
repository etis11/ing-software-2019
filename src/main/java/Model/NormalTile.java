package Model;

import java.util.List;

public class NormalTile extends Tile {


    private boolean isPresentAmmo;
    //appounto di oscar. Un tile può contenere solo un ammocard, non ha senso che sia una list
    private AmmoCard ammoCard;

    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    public void putAmmoCard(AmmoCard ammoCard) {
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
