package model.clientModel;

import model.AmmoCard;
import model.Player;
import model.WeaponCard;

import java.util.List;

public class SemplifiedTile {
    private final int id;
    private final boolean ammoTile;
    private final boolean weaponTile;
    private List<SemplifiedPlayer> players;
    private AmmoCard ammoCard;
    private List<SemplifiedWeaponCard> weaponCards;

    public SemplifiedTile(int id, boolean ammoTile, boolean weaponTile) {
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
    }


}
