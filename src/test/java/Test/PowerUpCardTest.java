package Test;

import model.PowerUpCard;
import model.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class PowerUpCardTest {

    private PowerUpCard card;

    @BeforeEach
    public void init(){
        card = new PowerUpCard(Color.RED, PowerUpType.targetingScope);
    }

    @Test
    public void constructorTest(){
        assertTrue(card instanceof PowerUpCard, "ERROR of Instance");
    }

    @Test
    public void redPowerUpCardTest(){
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.targetingScope, "ERROR: not red gunsight");

        card = new PowerUpCard(Color.RED, PowerUpType.newton);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.newton, "ERROR: not red kineticBeam");

        card = new PowerUpCard(Color.RED, PowerUpType.teleporter);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.teleporter, "ERROR: not red teleport");

        card = new PowerUpCard(Color.RED, PowerUpType.tagbackGranade);
        assertSame(card.getColor(), Color.RED, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.tagbackGranade, "ERROR: not red venomGranade");
    }

    @Test
    public void bluePowerUpCardTest(){
        card = new PowerUpCard(Color.BLUE, PowerUpType.targetingScope);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.targetingScope, "ERROR: not blue gunsight");

        card = new PowerUpCard(Color.BLUE, PowerUpType.newton);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.newton, "ERROR: not blue kineticBeam");

        card = new PowerUpCard(Color.BLUE, PowerUpType.teleporter);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not blue powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.teleporter, "ERROR: not blue teleport");

        card = new PowerUpCard(Color.BLUE, PowerUpType.tagbackGranade);
        assertSame(card.getColor(), Color.BLUE, "ERROR: not red powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.tagbackGranade, "ERROR: not blue venomGranade");
    }

    @Test
    public void yellowPowerUpCardTest(){
        card = new PowerUpCard(Color.YELLOW, PowerUpType.targetingScope);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.targetingScope, "ERROR: not yellow gunsight");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.newton);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.newton, "ERROR: not yellow kineticBeam");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.teleporter);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.teleporter, "ERROR: not yellow teleport");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.tagbackGranade);
        assertSame(card.getColor(), Color.YELLOW, "ERROR: not yellow powerUp");
        assertSame(card.getPowerUpType(), PowerUpType.tagbackGranade, "ERROR: not yellow venomGranade");
    }

    @Test
    public void copyConstructorTest(){
        PowerUpCard card2 = new PowerUpCard(card);
        assertTrue(card2 instanceof PowerUpCard, "ERROR of Instance");
        assertSame(card.getColor(), card2.getColor(), "ERROR: wrong color");
        assertSame(card.getPowerUpType(), card2.getPowerUpType(), "ERROR: wrong type");
    }
}
