package Test;

import javafx.scene.paint.Color;
import jsonparser.JsonFileReader;
import jsonparser.WeaponCardDeserializer;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonCreatorTest {

    private JsonCreator jsonCreator;
    private Player gigino;
    private Player pinotto;
    private GameMap map;
    private Match m;

    @BeforeEach
    void initJsonCreator() {
        gigino = new Player("gigino");
        pinotto = new Player("pinotto");
        List<Player> players = new LinkedList<>();
        players.add(gigino);
        players.add(pinotto);
        m = new Match();
        m.setPlayers(players);
        map = new GameMap();
        map.addRegenPoint("red", new Tile(true, false));
        m.setMap(map);
        GameManager gm = new GameManager();
        jsonCreator = new JsonCreator();
    }

    @Test
    void correctPlayerFormat() {
        jsonCreator.notifyPlayerChange(gigino);
        jsonCreator.notifyPlayerChange(pinotto);
        try {
            gigino.pickUpWeapon(new WeaponCard());
        } catch (Exception e) {
            fail("Lancio di eccezione inaspettato");
        }

        String expectedOutput = "{\"message\":\"gigino e pinotto\",\"errorOccurred\":false,\"allPlayers\":[{\"name\":\"gigino\"," +
                "\"remainingMoves\":0,\"numWeaponCard\":1,\"weaponCards\":[{\"name\":\"default\",\"cost\":[],\"loaded\":true}]," +
                "\"numPowerUps\":0,\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1]," +
                "\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}},{\"name\":\"pinotto\",\"remainingMoves\":0,\"numWeaponCard\":0," +
                "\"weaponCards\":[],\"numPowerUps\":0,\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[]," +
                "\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"playerChanged\":true," +
                "\"changedPlayers\":[{\"name\":\"gigino\"},{\"name\":\"pinotto\"}],\"mapChanged\":false,\"allTiles\":[{\"id\":0,\"players\":[]," +
                "\"ammoTile\":true,\"weaponTile\":false}]}";

        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("gigino e pinotto"), () -> "The two string are different");

        jsonCreator.reset();
        expectedOutput = "{\"message\":\"gigino e pinotto\",\"errorOccurred\":false,\"allPlayers\":[{\"name\":\"gigino\"}," +
                "{\"name\":\"pinotto\"}],\"playerChanged\":true,\"changedPlayers\":[],\"mapChanged\":false," +
                "\"allTiles\":[{\"id\":0,\"players\":[],\"ammoTile\":true,\"weaponTile\":false}]}";
        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("vuoto"), () -> "The two string are different");
    }

    void correctTileFormat() {
        Tile t = new Tile(null, null, null, null, true, false);
        Tile t2 = new Tile(null, null, null, null, false, true);
        t2.setID(1);

        try {
            WeaponCard w = new WeaponCard();
            w.setName("broccolator");
            t2.putWeaponCard(w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Error: problems while putting the weapon");
        }
        t.addPlayer(gigino);
        jsonCreator.notifyTileChange(t);
        jsonCreator.notifyTileChange(t2);
        String expectedJson = "{\"changedPlayers\":[],\"changedTiles\":[{\"id\":0,\"players\":[\"Gigino\"]," +
                "\"ammoTile\":true,\"weaponTile\":false},{\"id\":1,\"players\":[],\"ammoTile\":false,\"weaponTile\":true," +
                "\"weapons\":[{\"name\":\"broccolator\",\"cost\":[],\"loaded\":true}]}]}";
        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), () -> "Error: the two json file should be the same");
    }


    void correctTileAndPlayer() {
        Tile t = new Tile(null, null, null, null, true, false);
        Tile t2 = new Tile(null, null, null, null, false, true);
        t2.setID(1);
        try {
            WeaponCard w = new WeaponCard();
            w.setName("broccolator");
            t2.putWeaponCard(w);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Error: problems while putting the weapon");
        }
        t.addPlayer(gigino);
        jsonCreator.notifyPlayerChange(gigino);
        jsonCreator.notifyPlayerChange(pinotto);
        jsonCreator.notifyTileChange(t);
        jsonCreator.notifyTileChange(t2);
        String expectedJson = "{\"changedPlayers\":[{\"name\":\"Gigino\",\"remainingMoves\":0,\"numWeaponCard\":0," +
                "\"weaponCards\":[],\"numPowerUps\":0,\"powerUps\":[],\"tile\":0,\"playerBoard\":{\"damageTokens\":[]," +
                "\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}," +
                "{\"name\":\"Pinotto\",\"remainingMoves\":0,\"numWeaponCard\":0,\"weaponCards\":[],\"numPowerUps\":0" +
                ",\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1]," +
                "\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"changedTiles\":[{\"id\":0,\"players\":[\"Gigino\"]," +
                "\"ammoTile\":true,\"weaponTile\":false},{\"id\":1,\"players\":[],\"ammoTile\":false,\"weaponTile\":true," +
                "\"weapons\":[{\"name\":\"broccolator\",\"cost\":[],\"loaded\":true}]}]}";
        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), () -> "ERROR:the two json should be the same");
    }


    /**
     * This test checks if the player and weapons are shown correctly. Pipino should not be able to see the gigino's weaposn
     */
    @Test
    void withRealWeapons() {
        Tile t = new Tile(null, null, null, null, true, false);
        Tile t2 = new Tile(null, null, null, null, false, true);
        t2.setID(1);
        PowerUpCard pc = new PowerUpCard(Color.RED, PowerUpType.NEWTON);
        JsonFileReader jsonFileReader = new JsonFileReader();//ok
        String cards = jsonFileReader.loadWeaponCards("cards/cards.json");//ok
        Match match = new Match();
        //List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        WeaponCardDeserializer weaponCardDeserializer = new WeaponCardDeserializer(match);//ok
        List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);//ok
        WeaponCard w = weaponCards.get(0);
        try {
            WeaponCard broccolator = new WeaponCard();
            broccolator.setName("broccolator");
            List<String> cost = new LinkedList<>();
            cost.add("yellow");
            broccolator.setReloadCost(cost);
            pinotto.pickUpWeapon(broccolator);

            //pinotto.pickUpPowerUp(pc);
            gigino.pickUpWeapon(w);
            w.setLoaded(true);
            t2.putWeaponCard(weaponCards.get(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Error: problems while putting the weapon");
        }
        t.addPlayer(gigino);
        jsonCreator.notifyPlayerChange(gigino);
        jsonCreator.notifyPlayerChange(pinotto);
        jsonCreator.notifyTileChange(t);
        jsonCreator.notifyTileChange(t2);


        String expectedString = "{\"message\":\"Apelle figlio di apollo\",\"errorOccurred\":false," +
                "\"allPlayers\":[{\"name\":\"gigino\",\"remainingMoves\":0,\"numWeaponCard\":1,\"weaponCards\":[{}]," +
                "\"numPowerUps\":0,\"powerUps\":[],\"tile\":0,\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[]," +
                "\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}},{\"name\":\"pinotto\"," +
                "\"remainingMoves\":0,\"numWeaponCard\":1,\"weaponCards\":[{\"name\":\"broccolator\",\"cost\":[\"yellow\"]," +
                "\"loaded\":true}],\"numPowerUps\":0,\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[]," +
                "\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"playerChanged\":true," +
                "\"changedPlayers\":[{\"name\":\"gigino\"},{\"name\":\"pinotto\"}],\"mapChanged\":true,\"allTiles\":[{\"id\":0,\"players\":[]," +
                "\"ammoTile\":true,\"weaponTile\":false}]}";
        assertEquals(expectedString, jsonCreator.createTargetPlayerJson("Apelle figlio di apollo", pinotto), () -> "ERROR: the json should be the same");

    }
}
