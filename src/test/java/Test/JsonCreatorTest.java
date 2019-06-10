package Test;

import model.JsonCreator;
import model.Player;
import model.Tile;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonCreatorTest {

    private JsonCreator jsonCreator;

    @BeforeEach
    void initJsonCreator(){
        jsonCreator = new JsonCreator();
    }

    @Test
    void correctPlayerFormat(){
        Player gigino = new Player("Gigino");
        Player pinotto = new Player("Pinotto");
        jsonCreator.notifyPlayerChange(gigino);
        jsonCreator.notifyPlayerChange(pinotto);
        try {
            gigino.pickUpWeapon(new WeaponCard());
        } catch (Exception e) {
            fail("Lancio di eccezione inaspettato");
        }

        String expectedOutput = "{\"changedPlayers\":[{\"name\":\"Gigino\",\"remainingMoves\":0," +
                "\"numWeaponCard\":1,\"weaponCards\":[{\"name\":\"default\",\"cost\":[],\"loaded\":true}],\"numPowerUps\":0," +
                "\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[],\"killValue\":[8,6,4,2,1,1,1,1,1]," +
                "\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}},{\"name\":\"Pinotto\",\"remainingMoves\":0,\"numWeaponCard\":0," +
                "\"weaponCards\":[],\"numPowerUps\":0,\"powerUps\":[],\"playerBoard\":{\"damageTokens\":[],\"marksTokens\":[]," +
                "\"killValue\":[8,6,4,2,1,1,1,1,1],\"numBlueAmmo\":1,\"numRedAmmo\":1,\"numYellowAmmo\":1}}],\"changedTiles\":[]," +
                "\"message\":\"gigino e pinotto\"}";
        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("gigino e pinotto"), ()-> "The two string are different");

        jsonCreator.reset();
        expectedOutput = "{\"changedPlayers\":[],\"changedTiles\":[],\"message\":\"vuoto\"}";
        assertEquals(expectedOutput, jsonCreator.createJsonWithMessage("vuoto"), ()->"The two string are different");
    }

    @Test
    void correctTileFormat(){
        Player gigino = new Player("Gigino");
        Player pinotto = new Player("Pinotto");
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
        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), ()->"Error: the two json file should be the same");
    }

    @Test
    void correctTileAndPlayer(){
        Player gigino = new Player("Gigino");
        Player pinotto = new Player("Pinotto");
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
        assertEquals(expectedJson, jsonCreator.createJsonWithMessage(null), () ->"ERROR:the two json should be the same");
    }
}
