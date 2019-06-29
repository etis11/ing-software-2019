package Test;

import model.Player;
import model.Tile;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player p;

    @BeforeEach
    void initPlayer() {
        p = new Player();
    }

    @Test
    void weaponOneInsertionTest() {
        WeaponCard w = new WeaponCard();
        System.out.println(" p.getWeapons().size :"+ p.getWeapons().size());
        Tile tile = new Tile(1,false,true);
        tile.putWeaponCard(w);
        p.setTile(tile);
        System.out.println(" p.getTile :"+ p.getTile().getID());
        try {
            p.pickUpWeapon(w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("after p.getWeapons().size :"+ p.getWeapons().size());




        assertEquals(w, p.getWeapons().get(0), () -> "ERROR: the weapon returned is not the same that has been put in");

    }


}
