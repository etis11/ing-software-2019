package Test;

import model.FlameThrowerStrategy;
import model.GameMap;
import model.Player;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FlameThrowerStrategyTest {

    private FlameThrowerStrategy flameStrategy;

    @BeforeEach
    void initSTrat(){
        flameStrategy = new FlameThrowerStrategy();
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "src/test/testingMaps/testMap1.json",
            "src/test/testingMaps/twoAllignedPlayers.json",
            "src/test/testingMaps/onePlayerFar.json",
            "src/test/testingMaps/onePlayerNear.json",
            "src/test/testingMaps/TwoPlayerAdjacents.json"
                            })
    void canHitTests(String path){
        GameMap map = GameMap.loadMap(path);
        Tile redRegenPoint = map.getRedRegenPoint();
        Player shooter = new Player("Giuseppe");
        redRegenPoint.addPlayer(shooter);
        assertTrue(flameStrategy.canHitSomeone(shooter), ()->"ERROR: all these maps should have at least one available target");
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "src/test/testingMaps/WrongTargets.json"
    })
    void cantHitTests(String path){
        GameMap map = GameMap.loadMap(path);
        Tile redRegenPoint = map.getRedRegenPoint();
        Player shooter = new Player("Giuseppe");
        redRegenPoint.addPlayer(shooter);
        assertFalse(flameStrategy.canHitSomeone(shooter), ()->"ERROR: all these maps should have at least one available target");
    }

    @Test
    void correctTargetPlayers(){
        String[] mapsPath = {
                "src/test/testingMaps/testMap1.json",
                "src/test/testingMaps/twoAllignedPlayers.json",
                "src/test/testingMaps/onePlayerFar.json",
                "src/test/testingMaps/onePlayerNear.json",
                "src/test/testingMaps/TwoPlayerAdjacents.json"
        };
        GameMap map = GameMap.loadMap(mapsPath[0]);
        Tile redRegenPoint = map.getRedRegenPoint();
        Player shooter = new Player("Giuseppe");
        redRegenPoint.addPlayer(shooter);
        List<Player> players = map.allVisiblePlayers(shooter);
        System.out.println(players);
        players.addAll(map.allNotVisiblePlayers(shooter));
        System.out.println(players);


    }


}
