package Test;

import jsonparser.JsonFileReader;
import jsonparser.WeaponCardDeserializer;
import model.AmmoCard;
import model.Match;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponCardTest {

    private WeaponCard card;

    @BeforeEach
    public void init() {
        card = new WeaponCard();
    }

    @Test
    public void constructorTest() {
        assertTrue(card instanceof WeaponCard, "ERROR of instance");

        WeaponCard card2 = new WeaponCard(card);
        assertTrue(card2 instanceof WeaponCard, "ERROR of instance");

    }

    @Test
    public void equalsTest() {
        assertFalse(card.equals(null), "ERROR: must be false card not equals with null");

        WeaponCard card2 = card;
        assertTrue(card.equals(card2), "ERROR: must be true cards are the same object");

        AmmoCard card3 = new AmmoCard();
        assertFalse(card.equals(card3), "ERROR: must be false, they are different classes");

        //TODO
        //oggetti distinti ma uguali->serve costruttore
    }

    @Test
    void jsonLoadTest() {
        JsonFileReader jsonFileReader = new JsonFileReader();
        String cards = jsonFileReader.loadWeaponCards("cards/cards.json");
        Match match = new Match();
        //List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        WeaponCardDeserializer weaponCardDeserializer = new WeaponCardDeserializer(match);
        List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        System.out.println(weaponCards.get(0).getBaseEffect().get(0).getMarks());
    }


}
