package jsonparser;


import model.Match;
import model.WeaponCard;

import java.util.List;

public class mainArmi {
    public static void main(String[] args) {
        Match match = new Match();
        List<WeaponCard> weaponCards = WeaponCard.getWeaponsFromJson(mainArmi.class.getResourceAsStream("/cards/weaponCards.json") , match);

        for(WeaponCard weaponCard: weaponCards)
            System.out.println(weaponCard);
    }
}
