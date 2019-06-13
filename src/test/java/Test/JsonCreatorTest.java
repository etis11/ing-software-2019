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

//    private JsonCreator jsonCreator;
//
//    @BeforeEach
//    void initJsonCreator() {
//        jsonCreator = new JsonCreator();
//    }
//
//    @Test
//    void correctPlayerFormat() {
//        Player gigino = new Player("Gigino");
//        Player pinotto = new Player("Pinotto");
//        jsonCreator.notifyPlayerChange(gigino);
//        jsonCreator.notifyPlayerChange(pinotto);
//        try {
//            gigino.pickUpWeapon(new WeaponCard());
//        } catch (Exception e) {
//            fail("Lancio di eccezione inaspettato");
//        }
//
//        String expectedOutput = "{\"changedPlayers\":[{\"name\":\"Gigino\",\"remainingMoves\":0," +
//                "\"numWeaponCard\":1,\"weaponCards\":[{\"name\":\"default\",\"cost\":[],\"loaded\":true}],\"numPowerUps\":0," +
//                "\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1]," +
//                "\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}},{\"name\":\"Pinotto\",\"remainingMoves\":0,\"numWeaponCard\":0," +
//                "\"weaponCards\":[],\"numPowerUps\":0,\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[]," +
//                "\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"changedTiles\":[]," +
//                "\"message\":\"gigino e pinotto\"}";
//        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("gigino e pinotto"), () -> "The two string are different");
//
//        jsonCreator.reset();
//        expectedOutput = "{\"changedPlayers\":[],\"changedTiles\":[],\"message\":\"vuoto\"}";
//        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("vuoto"), () -> "The two string are different");
//    }
//
//    @Test
//    void correctTileFormat() {
//        Player gigino = new Player("Gigino");
//        Player pinotto = new Player("Pinotto");
//        Tile t = new Tile(null, null, null, null, true, false);
//        Tile t2 = new Tile(null, null, null, null, false, true);
//        t2.setID(1);
//
//        try {
//            WeaponCard w = new WeaponCard();
//            w.setName("broccolator");
//            t2.putWeaponCard(w);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            fail("Error: problems while putting the weapon");
//        }
//        t.addPlayer(gigino);
//        jsonCreator.notifyTileChange(t);
//        jsonCreator.notifyTileChange(t2);
//        String expectedJson = "{\"changedPlayers\":[],\"changedTiles\":[{\"id\":0,\"players\":[\"Gigino\"]," +
//                "\"ammoTile\":true,\"weaponTile\":false},{\"id\":1,\"players\":[],\"ammoTile\":false,\"weaponTile\":true," +
//                "\"weapons\":[{\"name\":\"broccolator\",\"cost\":[],\"loaded\":true}]}]}";
//        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), () -> "Error: the two json file should be the same");
//    }
//
//    @Test
//    void correctTileAndPlayer() {
//        Player gigino = new Player("Gigino");
//        Player pinotto = new Player("Pinotto");
//        Tile t = new Tile(null, null, null, null, true, false);
//        Tile t2 = new Tile(null, null, null, null, false, true);
//        t2.setID(1);
//        try {
//            WeaponCard w = new WeaponCard();
//            w.setName("broccolator");
//            t2.putWeaponCard(w);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            fail("Error: problems while putting the weapon");
//        }
//        t.addPlayer(gigino);
//        jsonCreator.notifyPlayerChange(gigino);
//        jsonCreator.notifyPlayerChange(pinotto);
//        jsonCreator.notifyTileChange(t);
//        jsonCreator.notifyTileChange(t2);
//        String expectedJson = "{\"changedPlayers\":[{\"name\":\"Gigino\",\"remainingMoves\":0,\"numWeaponCard\":0," +
//                "\"weaponCards\":[],\"numPowerUps\":0,\"powerUps\":[],\"tile\":0,\"playerBoard\":{\"damageTokens\":[]," +
//                "\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}," +
//                "{\"name\":\"Pinotto\",\"remainingMoves\":0,\"numWeaponCard\":0,\"weaponCards\":[],\"numPowerUps\":0" +
//                ",\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1]," +
//                "\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"changedTiles\":[{\"id\":0,\"players\":[\"Gigino\"]," +
//                "\"ammoTile\":true,\"weaponTile\":false},{\"id\":1,\"players\":[],\"ammoTile\":false,\"weaponTile\":true," +
//                "\"weapons\":[{\"name\":\"broccolator\",\"cost\":[],\"loaded\":true}]}]}";
//        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), () -> "ERROR:the two json should be the same");
//    }
//
//    @Test
//    void withRealWeapons(){
//        Player gigino = new Player("Gigino");
//        Player pinotto = new Player("Pinotto");
//        Tile t = new Tile(null, null, null, null, true, false);
//        Tile t2 = new Tile(null, null, null, null, false, true);
//        t2.setID(1);
//        PowerUpCard pc = new PowerUpCard(Color.RED, PowerUpType.NEWTON);
//        JsonFileReader jsonFileReader = new JsonFileReader();
//        String cards = jsonFileReader.loadWeaponCards("cards/cards.json");
//        Match match = new Match();
//        //List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
//        WeaponCardDeserializer weaponCardDeserializer = new WeaponCardDeserializer(match);
//        List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
//        WeaponCard w = weaponCards.get(0);
//        try {
//            WeaponCard broccolator = new WeaponCard();
//            broccolator.setName("broccolator");
//            List<String> cost = new LinkedList<>();
//            cost.add("yellow");
//            broccolator.setReloadCost(cost);
//            pinotto.pickUpWeapon(broccolator);
//            pinotto.pickUpPowerUp(pc);
//            gigino.pickUpWeapon(w);
//            w.setLoaded(true);
//            t2.putWeaponCard(weaponCards.get(1));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            fail("Error: problems while putting the weapon");
//        }
//        t.addPlayer(gigino);
//        jsonCreator.notifyPlayerChange(gigino);
//        jsonCreator.notifyPlayerChange(pinotto);
//        jsonCreator.notifyTileChange(t);
//        jsonCreator.notifyTileChange(t2);
//
//        String expectedString = "{\"changedPlayers\":[{\"name\":\"Gigino\",\"remainingMoves\":0,\"numWeaponCard\":1," +
//                "\"weaponCards\":[{}],\"numPowerUps\":0,\"powerUps\":[],\"tile\":0,\"playerBoard\":{\"damageTokens\":[]," +
//                "\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}" +
//                ",{\"name\":\"Pinotto\",\"remainingMoves\":0,\"numWeaponCard\":1,\"weaponCards\":[{\"name\":\"broccolator\"," +
//                "\"cost\":[\"yellow\"],\"loaded\":true}],\"numPowerUps\":1,\"powerUps\":[{\"color\":{\"red\":1.0,\"green\":0.0," +
//                "\"blue\":0.0,\"opacity\":1.0},\"powerUpType\":\"NEWTON\"}],\"playerBoard\":{\"damageTokens\":[]," +
//                "\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}]," +
//                "\"changedTiles\":[{\"id\":0,\"players\":[\"Gigino\"],\"ammoTile\":true,\"weaponTile\":false},{\"id\":1," +
//                "\"players\":[],\"ammoTile\":false,\"weaponTile\":true,\"weapons\":[{\"name\":\"Mitragliatrice\"," +
//                "\"cost\":[\"blue\",\"red\"],\"loaded\":true,\"baseEffect\":[{\"isGlobal\":false,\"cost\":[]," +
//                "\"damage\":{\"red\":1.0,\"green\":0.0,\"blue\":1.0},\"marks\":{\"red\":0.0,\"green\":0.0,\"blue\":0.0}," +
//                "\"optionalEffects\":[],\"canMoveShooter\":false,\"numStepsShooter\":0,\"canMoveTarget\":false," +
//                "\"numStepsTarget\":0,\"moveTargetAndHitAll\":false,\"alreadyMovedTarget\":false,\"redDamage\":0," +
//                "\"blueDamage\":0,\"yellowDamage\":0,\"redMarks\":0,\"blueMarks\":0,\"yellowMarks\":0}]," +
//                "\"advancedEffect\":[]}]}],\"message\":\"Apelle figlio di apollo\"}";
//
//        assertEquals(expectedString, jsonCreator.createTargetPlayerJson("Apelle figlio di apollo", pinotto), ()->"ERROR: the json should be the same");
//    }
}
