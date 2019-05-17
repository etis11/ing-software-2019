package Test;

import model.FlameThrowerStrategy;
import model.GameMap;
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

    /**
     * in this tesr 4 targets are created. 1 is placed north to the shotter, 2 east and the last at the east of the last twos.
     * 
     */
    @Test
    void correctTargetPlayers(){
        GameMap map = GameMap.loadMap("src/test/testingMaps/empty.json");
        Tile redRegenPoint = map.getRedRegenPoint();
        Player[] targetArray = {
                new Player("target1"),
                new Player("target2"),
                new Player("target3") ,
                new Player("target4")
                };
        List<Player> originalTargets = Arrays.asList(targetArray);

        Player shooter = new Player("shooter");
        redRegenPoint.addPlayer(shooter);
        redRegenPoint.getTile("north").addPlayer(targetArray[0]);
        redRegenPoint.getTile("east").getTile("east").addPlayer(targetArray[1]);
        redRegenPoint.getTile("east").addPlayer(targetArray[2]);
        redRegenPoint.getTile("east").addPlayer(targetArray[3]);
        List<Player> hittable = flameStrategy.getHittableTargets(shooter);

        assertTrue(hittable.size() == originalTargets.size(), ()->"ERROR:in this case all the targets are hittable. " +
                                                                            "Some targets are missing");
        for(Player p: originalTargets){
            if(!hittable.contains(p)) fail(p + " should be in the hittable targets");
        }


    }

    @Test
    void correctShootBehaviour(){
        GameMap map = GameMap.loadMap("src/test/testingMaps/empty.json");
        Tile redRegenPoint = map.getRedRegenPoint();
        Player[] targetArray = {
                new Player("target1"),
                new Player("target2"),
                new Player("target3") ,
                new Player("target4")
        };

        Player shooter = new Player("shooter");
        redRegenPoint.addPlayer(shooter);
        redRegenPoint.getTile("north").addPlayer(targetArray[0]);
        redRegenPoint.getTile("east").getTile("east").addPlayer(targetArray[1]);
        redRegenPoint.getTile("east").addPlayer(targetArray[2]);
        redRegenPoint.getTile("east").addPlayer(targetArray[3]);

        List<Player> possibleTargets =new LinkedList<>();
        //it's important that the first is the closest, otherwise return false
        possibleTargets.add(targetArray[2]);
        possibleTargets.add(targetArray[1]);

        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets), ()->"ERROR: the player are all alligned, they should be valid" );


        possibleTargets =new LinkedList<>();
        //in this case, the first in the list is the further target, so it gotta be false
        possibleTargets.add(targetArray[1]);
        possibleTargets.add(targetArray[2]);
        assertFalse(flameStrategy.areTargetValid(shooter, possibleTargets), ()-> "ERROR:should be false, because the first" +
                                                                            " target should be the nearest to the shooter");

        //just one, the further
        possibleTargets = new LinkedList<>();
        possibleTargets.add(targetArray[1]);
        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets));
        //just one, the nearer
        possibleTargets = new LinkedList<>();
        possibleTargets.add(targetArray[2]);
        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets), ()->"ERROR: only a target is ok for the flamethrower" );

        //one is up, another is on the left. should be false
        possibleTargets = new LinkedList<>();
        possibleTargets.add(targetArray[2]);
        possibleTargets.add(targetArray[0]);
        assertFalse(flameStrategy.areTargetValid(shooter, possibleTargets), () ->"ERROR: the target are not allined!");

        final List<Player> finalPossibleTargets = new LinkedList<>();
        assertThrows(IllegalArgumentException.class, ()->flameStrategy.areTargetValid(shooter, finalPossibleTargets));
    }


}
