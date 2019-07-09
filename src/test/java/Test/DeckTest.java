package Test;

import model.Deck;
import model.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void deckTest() {
        Deck deck = new Deck<>();
        WeaponCard card1 = new WeaponCard();
        card1.setName("testing");
        deck.addCard(card1);
        assertEquals(1, deck.size(), "Wrong size of deck");
        WeaponCard cardDraw = (WeaponCard) deck.draw();
        assertEquals(0, deck.size(), "Wrong size of deck");
    }
}
