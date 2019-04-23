package Test;

import model.Player;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player p;

    @BeforeEach
    void initPlayer(){
        p = new Player();
    }
    
    void weaponOneInsertionTest(){
        WeaponCard w = new WeaponCard();
        try{
            p.pickUpWeapon(w);
        }
        catch (Exception e) {System.out.println(e.getMessage());}


       assertEquals(w, p.getWeapons().get(0), () -> "ERROR: the weapon returned is not the same that has been put in");

    }


}
