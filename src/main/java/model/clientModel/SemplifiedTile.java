package model.clientModel;

import model.AmmoCard;
import model.Player;
import model.Room;
import model.WeaponCard;

import java.util.List;

public class SemplifiedTile {
    private final int id;

    private List<Player> players;

    private final boolean ammoTile;

    private AmmoCard ammoCard;

    private final boolean weaponTile;

    private List<WeaponCard> weaponCards;

    public SemplifiedTile(int id, boolean ammoTile, boolean weaponTile){
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
    }


}
