package Test;

import model.DamageTransporter;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DamageTransporterTest {

    DamageTransporter dt;
    Player target;
    Player owner;

    @BeforeEach
    public void init(){
        owner = new Player();
        target = new Player();
        dt = new DamageTransporter(target, owner, 1, 2);
    }

    @Test
    public void instanceTest(){
        assertNotNull(dt);
        assertTrue(dt instanceof DamageTransporter, "ERROR of Instance");
    }
    @Test
    public void paramTest(){
        assertSame(owner, dt.getOwner(), "ERROR wrong owner");
        assertSame(target, dt.getTarget(), "ERROR wrong target");
        assertSame(1, dt.getNumDamage(), "ERROR wrong damage");
        assertSame(2, dt.getNumMark(), "ERROR wrong marks");
    }
}
