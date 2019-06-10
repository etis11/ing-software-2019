package Test;

import javafx.scene.paint.Color;
import model.Ammo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmmoTest {

    private Ammo ammo;

    @BeforeEach
    public void initInstance(){
        ammo = new Ammo(Color.BLUE);
    }

    @Test
    public void testConstructor(){
        assertTrue(ammo instanceof Ammo, "ERROR of Instance");

        assertTrue(ammo.getColor() == Color.BLUE, "ERROR not blue ammo");

        ammo = new Ammo(Color.RED);

        assertTrue(ammo.getColor() == Color.RED, "ERROR not red ammo");

        ammo = new Ammo(Color.YELLOW);

        assertTrue(ammo.getColor() == Color.YELLOW, "ERROR not yellow ammo");
    }
}
