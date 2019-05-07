package Test;

import model.Player;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user1;
    private User user2;
    private Player player;


    @BeforeEach
    public void init(){
        user1 = new User();
    }

    @Test
    public void instanceTest(){
        assertTrue(user1 instanceof User, "ERROR: wrong instance");
    }

    @Test
    public void tokenTest(){
        user2 = new User();
        assertNotSame(user1.getToken(), user2.getToken(), "ERROR: same token");

    }

    @Test
    public void settingTest(){

        assertSame(user1.getUsername(), "user", "ERROR: wrong deafult username");
        assertSame(user1.getEffectPhrase(), "I will survive", "ERROR: wrong deafult effect phrase");
        assertNull(user1.getPlayer(), "ERROR: not null value");

        user1.setUsername("Pippo");
        assertSame(user1.getUsername(), "Pippo", "ERROR: wrong username");
        assertNotSame(user1.getUsername(), "user", "ERROR: not modified");

        user1.setEffectPhrase("W adrenalina");
        assertSame(user1.getEffectPhrase(), "W adrenalina", "ERROR: wrong effect phrase");
        assertNotSame(user1.getEffectPhrase(), "I will survive", "ERROR: not modified");

        player = new Player();
        user1.setPlayer(player);
        assertSame(user1.getPlayer(), player, "ERROR: wrong player setted");
        assertNotNull(user1.getPlayer(), "ERROR: not modified");
    }
}
