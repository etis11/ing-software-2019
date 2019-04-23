package Test;


import model.AmmoCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AmmoCardTest {

    private AmmoCard card;

    @BeforeEach
    public void init(){
        card = new AmmoCard(1,1,1,false);
    }

    /**
     * Test if the constructor instantiate the correct object.
     */
    @Test
    public void constructorTest(){
        assertTrue(card instanceof AmmoCard, "ERROR of Instance");
    }

    /**
     * Test if the constructor with the parameters works. All the parameters should be in correctly attributes.
     */
    @Test
    public void valuesTest(){
        assertFalse(card.isDrawPowerUp(), "ERROR power up has not to be drawn");

        card = new AmmoCard(1,2,3,true);

        assertEquals(1, card.getNumBlue(), "ERROR wrong number of blue ammo");
        assertEquals(2, card.getNumYellow(), "ERROR wrong number of yellow ammo");
        assertEquals(3, card.getNumRed(), "ERROR wrong number of red ammo");

        assertTrue(card.isDrawPowerUp(), "ERROR power up has to be drawn");
    }


}
