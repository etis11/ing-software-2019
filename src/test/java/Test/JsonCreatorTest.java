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
        Tile t = new Tile();
//        t.setTile("north", new Tile());
        t.addPlayer(gigino);
        jsonCreator.notifyTileChange(t);
        System.out.println(jsonCreator.createJsonWithMessage(null));
    }
}
