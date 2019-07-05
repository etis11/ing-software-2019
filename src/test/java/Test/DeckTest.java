package Test;

import model.Deck;
import model.WeaponCard;
import org.junit.jupiter.api.Test;

public class DeckTest {

    @Test
    void test() {
        Deck deck = new Deck<>();
        WeaponCard card1 = new WeaponCard();
        card1.setName("testing");
        deck.addCard(card1);
        WeaponCard cardDraw = (WeaponCard) deck.draw();
    }
}
