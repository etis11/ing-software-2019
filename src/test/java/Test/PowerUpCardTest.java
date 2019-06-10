package Test;

import javafx.scene.paint.Color;
import model.PowerUpCard;
import model.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PowerUpCardTest {

    private PowerUpCard card;

    @BeforeEach
    public void init(){
        card = new PowerUpCard(Color.RED, PowerUpType.TARGETING_SCOPE);
    }

    @Test
    public void constructorTest(){
        assertTrue(card instanceof PowerUpCard, "ERROR of Instance");
    }

    @Test
    public void redPowerUpCardTest(){
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TARGETING_SCOPE, "ERROR: not red gunsight");

        card = new PowerUpCard(Color.RED, PowerUpType.NEWTON);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.NEWTON, "ERROR: not red kineticBeam");

        card = new PowerUpCard(Color.RED, PowerUpType.TELEPORTER);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TELEPORTER, "ERROR: not red teleport");

        card = new PowerUpCard(Color.RED, PowerUpType.TAGBACK_GRANADE);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TAGBACK_GRANADE, "ERROR: not red venomGranade");
    }

    @Test
    public void bluePowerUpCardTest(){
        card = new PowerUpCard(Color.BLUE, PowerUpType.TARGETING_SCOPE);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TARGETING_SCOPE, "ERROR: not blue gunsight");

        card = new PowerUpCard(Color.BLUE, PowerUpType.NEWTON);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.NEWTON, "ERROR: not blue kineticBeam");

        card = new PowerUpCard(Color.BLUE, PowerUpType.TELEPORTER);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TELEPORTER, "ERROR: not blue teleport");

        card = new PowerUpCard(Color.BLUE, PowerUpType.TAGBACK_GRANADE);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TAGBACK_GRANADE, "ERROR: not blue venomGranade");
    }

    @Test
    public void yellowPowerUpCardTest(){
        card = new PowerUpCard(Color.YELLOW, PowerUpType.TARGETING_SCOPE);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TARGETING_SCOPE, "ERROR: not yellow gunsight");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.NEWTON);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.NEWTON, "ERROR: not yellow kineticBeam");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.TELEPORTER);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TELEPORTER, "ERROR: not yellow teleport");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.TAGBACK_GRANADE);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.TAGBACK_GRANADE, "ERROR: not yellow venomGranade");
    }

    @Test
    public void copyConstructorTest(){
        PowerUpCard card2 = new PowerUpCard(card);
        assertTrue(card2 instanceof PowerUpCard, "ERROR of Instance");
        assertSame(card.getColor(), card2.getColor(), "ERROR: wrong color");
        assertSame(card.getPowerUpType(), card2.getPowerUpType(), "ERROR: wrong type");
    }
}
