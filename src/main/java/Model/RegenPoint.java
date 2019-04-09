package Model;

import java.util.List;

public class RegenPoint extends Tile {


    private List<WeaponCard> weaponCards;


    @Override
    public boolean isPresentAmmoCard() {
        //TODO

        return false;
    }

    public List<WeaponCard> getWeapons() {
      return weaponCards;
    }

    @Override
    public boolean arePresentWeapons() {
        return false;
    }

    public void setWeaponCards(List<WeaponCard> weaponCards) {
        this.weaponCards = weaponCards;
    }

    public void putWeaponCard(WeaponCard weaponCard){
        this.weaponCards.add(weaponCard);
    }



}
