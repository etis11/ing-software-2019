package Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonParser.JsonFileReader;
import model.AmmoCard;
import model.Effect;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponCardTest {

    private WeaponCard card;

    @BeforeEach
    public void init(){
        card = new WeaponCard();
    }

    @Test
    public void constructorTest(){
        assertTrue(card instanceof WeaponCard, "ERROR of instance");

        WeaponCard card2 = new WeaponCard(card);
        assertTrue(card2 instanceof WeaponCard, "ERROR of instance");

    }

    @Test
    public void equalsTest(){
        assertFalse(card.equals(null), "ERROR: must be false card not equals with null");

        WeaponCard card2 = card;
        assertTrue(card.equals(card2), "ERROR: must be true cards are the same object");

        AmmoCard card3= new AmmoCard();
        assertFalse(card.equals(card3), "ERROR: must be false, they are different classes");

        //TODO
        //oggetti distinti ma uguali->serve costruttore
    }

    @Test
    void jsonLoadTest(){
        JsonFileReader jsonFileReader = new JsonFileReader();
        List<WeaponCard> cards = jsonFileReader.loadWeaponCards("cards/cards.json");


    }


}
