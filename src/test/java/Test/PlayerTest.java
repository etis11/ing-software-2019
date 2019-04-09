package Test;

import Model.Player;
import Model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.WeakHashMap;

public class PlayerTest {

    private Player p;

    @BeforeEach
    void initPlayer(){
        p = new Player();
    }

    @Test
    void weaponOneInsertionTest(){
        WeaponCard w = new WeaponCard();

    }

}
