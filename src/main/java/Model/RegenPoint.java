package Model;

import java.util.List;

public class RegenPoint extends Tile {


    private List<WeaponCard> weaponCards;

    public List<WeaponCard> getWeapons() {
      return weaponCards;
    }

    public void setWeaponCards(List<WeaponCard> weaponCards) {
        this.weaponCards = weaponCards;
    }

    public void putWeaponCard(WeaponCard weaponCard){
        this.weaponCards.add(weaponCard);
    }



}
