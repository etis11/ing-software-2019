package Test;

import model.Deck;
import model.WeaponCard;
import org.junit.jupiter.api.Test;

public class DeckTest {

    @Test
    void testBlala() {
        Deck deck = new Deck<>();
        WeaponCard card1 = new WeaponCard();
        card1.setName("nameBlaBla");
        deck.addCard(card1);
        WeaponCard cardDraw = (WeaponCard) deck.draw();
        System.out.println("name of Draw Card is : " + cardDraw.getName());
    }
}
