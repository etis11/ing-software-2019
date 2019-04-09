package Test;

import Model.AmmoCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private AmmoCard ammoCard;


    @Test
    public void testConstructor() {
        ammoCard = new AmmoCard();

        assertTrue(ammoCard instanceof AmmoCard, "ERRORE Istanza");
    }

    @Test
    public void testSetter() {

        int numBlue =2 ;
        ammoCard.setNumBlue(numBlue);
        assertNotNull(ammoCard.getNumBlue());
        assertTrue(ammoCard.getNumBlue() == 2);

    }

}
