package Test;

import model.BloodToken;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BloodTokenTest {
    BloodToken dt;
    Player owner;

    @BeforeEach
    public void init(){
        owner = new Player();
        dt = new BloodToken(owner);
    }

    @Test
    public void instanceTest(){
        assertNotNull(dt);
        assertTrue(dt instanceof BloodToken, "ERROR of Instance");
    }

    @Test
    public void paramTest(){
        assertSame(owner, dt.getOwner(), "ERROR wrong owner");
    }
}
