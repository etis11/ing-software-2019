package Test;

import model.GameMap;
import model.IgnoreWallStrategy;
import model.Player;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IgnoreWallTest {

    IgnoreWallStrategy wallStrategy;

    @BeforeEach
    void initStrategy() {
        wallStrategy = new IgnoreWallStrategy();
    }

    /**
     * Stress the canHitSomeone function in the case it returns true. (doors, no doors, both)
     *
     * @param path path of the map
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/testingMaps/NordSudEstOvest.json",
            "src/test/testingMaps/noDoors.json",
            "src/test/testingMaps/noDoors.json"}
    )
    void canHitSomeoneTest(String path) {
        GameMap g = GameMap.loadMap(path);
        Tile redRegenPoint = g.getRedRegenPoint();
        Player shooter = new Player("Shooter");
        redRegenPoint.addPlayer(shooter);
        assertTrue(wallStrategy.canHitSomeone(shooter));

    }

    /**
     * stresses the canHitSomeone when no target is available
     *
     * @param path
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/testingMaps/cantHitIgnoreWall.json"}
    )
    void canNotHitSomeoneTest(String path) {
        GameMap g = GameMap.loadMap(path);
        Tile redRegenPoint = g.getRedRegenPoint();
        Player shooter = new Player("Shooter");
        redRegenPoint.addPlayer(shooter);
        assertFalse(wallStrategy.canHitSomeone(shooter));
    }


    /**
     * tests that the strategy is able to see both visible targets and targets hided behind a wall
     */
    @Test
    void hittableTargets() {
        GameMap map = GameMap.loadMap("src/test/testingMaps/empty.json");
        Tile redRegenPoint = map.getRedRegenPoint();
        Tile yellowRegenPoint = map.getYellowRegenPoint();
        Tile blueRegenPoint = map.getBlueRegenPoint();
        Player[] targetArray = {
                new Player("target1"),
                new Player("target2"),
                new Player("target3"),
                new Player("target4")
        };
        List<Player> originalTargets = Arrays.asList(targetArray);

        Player shooter = new Player("Shooter");
        redRegenPoint.addPlayer(shooter);

        yellowRegenPoint.getTile("north").addPlayer(targetArray[0]);

        //on the same
        List<Player> correctTargets = new LinkedList<>();
        correctTargets.add(targetArray[0]);
        List<Player> possibleTargets = wallStrategy.getHittableTargets(shooter);
        assertSame(correctTargets.get(0), possibleTargets.get(0), () -> "ERROR: the two player are on the same direction");

        try {

            redRegenPoint.removePlayer(shooter);
        } catch (Exception e) {
            e.getMessage();
        }

        blueRegenPoint.addPlayer(shooter);
        yellowRegenPoint.getTile("west").addPlayer(targetArray[1]);
        correctTargets = new LinkedList<>();
        correctTargets.add(targetArray[1]);
        assertSame(correctTargets.get(0), wallStrategy.getHittableTargets(shooter).get(0), () -> "ERROR: even if it's behind a wall, should be hitted");
    }


    void areTargetValidTest() {
        GameMap map = GameMap.loadMap("src/test/testingMaps/empty.json");
        Tile redRegenPoint = map.getRedRegenPoint();
        Tile yellowRegenPoint = map.getYellowRegenPoint();
        Tile blueRegenPoint = map.getBlueRegenPoint();
        Player[] targetArray = {
                new Player("target1"),
                new Player("target2"),
                new Player("target3"),
                new Player("target4")
        };

        Player shooter = new Player("Shooter");

        blueRegenPoint.addPlayer(shooter);

        //add a tile behind a wall
        //yellowRegenPoint.getTile("west").addPlayer(targetArray[0]);
        //add a tile next to the shooter
        //yellowRegenPoint.getTile("north").getTile("west").addPlayer(targetArray[1]);
        //add a target in the player tile
        blueRegenPoint.addPlayer(targetArray[2]);
        List<Player> targets = new LinkedList<>();
        targets.add(targetArray[0]);
        targets.add(targetArray[1]);
        assertTrue(wallStrategy.areTargetValid(shooter, targets), () -> "ERROR: should be valide, since are all allined");
    }
}
