package Test;

import Model.PowerUpCard;
import Model.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class PowerUpCardTest {

    private PowerUpCard card;

    @BeforeEach
    public void init(){
        card = new PowerUpCard(Color.RED, PowerUpType.gunsight);
    }

    @Test
    public void constructorTest(){
        assertTrue(card instanceof PowerUpCard, "ERROR of Instance");
    }

    @Test
    public void redPowerUpCardTest(){
        assertTrue(card.getColor() == Color.RED, "ERROR: not red powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.gunsight, "ERROR: not red gunsight");

        card = new PowerUpCard(Color.RED, PowerUpType.kineticBeam);
        assertTrue(card.getColor() == Color.RED, "ERROR: not red powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.kineticBeam, "ERROR: not red kineticBeam");

        card = new PowerUpCard(Color.RED, PowerUpType.teleport);
        assertTrue(card.getColor() == Color.RED, "ERROR: not red powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.teleport, "ERROR: not red teleport");

        card = new PowerUpCard(Color.RED, PowerUpType.venomGranade);
        assertTrue(card.getColor() == Color.RED, "ERROR: not red powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.venomGranade, "ERROR: not red venomGranade");
    }

    @Test
    public void bluePowerUpCardTest(){
        card = new PowerUpCard(Color.BLUE, PowerUpType.gunsight);
        assertTrue(card.getColor() == Color.BLUE, "ERROR: not blue powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.gunsight, "ERROR: not blue gunsight");

        card = new PowerUpCard(Color.BLUE, PowerUpType.kineticBeam);
        assertTrue(card.getColor() == Color.BLUE, "ERROR: not blue powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.kineticBeam, "ERROR: not blue kineticBeam");

        card = new PowerUpCard(Color.BLUE, PowerUpType.teleport);
        assertTrue(card.getColor() == Color.BLUE, "ERROR: not blue powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.teleport, "ERROR: not blue teleport");

        card = new PowerUpCard(Color.BLUE, PowerUpType.venomGranade);
        assertTrue(card.getColor() == Color.BLUE, "ERROR: not red powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.venomGranade, "ERROR: not blue venomGranade");
    }

    @Test
    public void yellowPowerUpCardTest(){
        card = new PowerUpCard(Color.YELLOW, PowerUpType.gunsight);
        assertTrue(card.getColor() == Color.YELLOW, "ERROR: not yellow powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.gunsight, "ERROR: not yellow gunsight");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.kineticBeam);
        assertTrue(card.getColor() == Color.YELLOW, "ERROR: not yellow powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.kineticBeam, "ERROR: not yellow kineticBeam");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.teleport);
        assertTrue(card.getColor() == Color.YELLOW, "ERROR: not yellow powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.teleport, "ERROR: not yellow teleport");

        card = new PowerUpCard(Color.YELLOW, PowerUpType.venomGranade);
        assertTrue(card.getColor() == Color.YELLOW, "ERROR: not yellow powerUp");
        assertTrue(card.getPowerUpType()==PowerUpType.venomGranade, "ERROR: not yellow venomGranade");
    }
}
