package Test;

import model.AmmoCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    private AmmoCard ammoCard;

    @BeforeEach
    void initInstance() {
        ammoCard = new AmmoCard();
    }

    @Test
    public void testSetter() {

        int numBlue = 2;
//        ammoCard.setNumBlue(numBlue);
//        assertNotNull(ammoCard.getNumBlue());
//        assertTrue(ammoCard.getNumBlue() == 2);

    }

    @Test
    public void testConstructor() {
        ammoCard = new AmmoCard();

        assertTrue(ammoCard instanceof AmmoCard, "ERRORE Istanza");
    }


}
