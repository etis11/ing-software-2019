package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the flame and the bbq strategy
 */
public class FlameThrowerStrategyTest {

    private FlameThrowerStrategy flameStrategy;
    private BBQStrategy bbqStrategy;

    @BeforeEach
    void initStrategies(){
        flameStrategy = new FlameThrowerStrategy();
        bbqStrategy = new BBQStrategy();
    }

    /**
     * checks in different maps if the can hit method works fine
     * @param path path of the json map
     */
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
    void canNotHitTests(String path){
        GameMap map = GameMap.loadMap(path);
        Tile redRegenPoint = map.getRedRegenPoint();
        Player shooter = new Player("Giuseppe");
        redRegenPoint.addPlayer(shooter);
        assertFalse(flameStrategy.canHitSomeone(shooter), ()->"ERROR: all these maps should have at least one available target");
    }

    /**
     * in this test 4 targets are created. 1 is placed north to the shooter, 2 east and the last at the east of the last twos.
     * 
     */
    @Test
    void flameAndBbqCorrectTargetPlayers(){
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

    /**
     * stress the areTargetsValid
     */
    @Test
    void flameCorrectShootBehaviour(){
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


        //it's important that the first is the closest, otherwise return false
        List<Player> possibleTargets = adjacentsPlayer(targetArray);
        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets), ()->"ERROR: the player are all alligned, they should be valid" );

        //in this case, the first in the list is the further target, so it gotta be false
        possibleTargets = adjacentsPlayerNotCorrectForFlame(targetArray);
        assertFalse(flameStrategy.areTargetValid(shooter, possibleTargets), ()-> "ERROR:should be false, because the first" +
                                                                            " target should be the nearest to the shooter");

        //just one, the further
        possibleTargets = furtherPlayer(targetArray);
        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets));

        //just one, the nearer
        possibleTargets = nearer(targetArray);
        assertTrue(flameStrategy.areTargetValid(shooter, possibleTargets), ()->"ERROR: only a target is ok for the flamethrower" );

        //one is up, another is on the left. should be false
        possibleTargets = notAlligned(targetArray);
        assertFalse(flameStrategy.areTargetValid(shooter, possibleTargets), () ->"ERROR: the target are not allined!");

        final List<Player> finalPossibleTargets = new LinkedList<>();
        assertThrows(IllegalArgumentException.class, ()->flameStrategy.areTargetValid(shooter, finalPossibleTargets));
    }

    /**
     * strees the are target valid method for bbq
     */
    @Test
    void bbqCorrectShotBehaviuor(){
        GameMap map = GameMap.loadMap("src/test/testingMaps/empty.json");
        Tile redRegenPoint = map.getRedRegenPoint();
        List<Player> possibleTargets = new LinkedList<>();

         Player[] targetArray = {
                 new Player("target1"),
                 new Player("target2"),
                 new Player("target3") ,
                 new Player("target4")
         };

        Player shooter = new Player("Giuseppe");
        redRegenPoint.getTile("north").addPlayer(targetArray[0]);
        redRegenPoint.getTile("east").getTile("east").addPlayer(targetArray[1]);
        redRegenPoint.getTile("east").addPlayer(targetArray[2]);
        redRegenPoint.getTile("east").addPlayer(targetArray[3]);

        redRegenPoint.addPlayer(shooter);

        assertTrue(bbqStrategy.areTargetValid(shooter, adjacentsPlayer(targetArray)), () -> "ERROR: the targets are alligned");
        assertTrue(bbqStrategy.areTargetValid(shooter, adjacentsPlayerNotCorrectForFlame(targetArray)),
                                                () -> "ERROR: with the bbq the player can be hit");

        assertTrue(bbqStrategy.areTargetValid(shooter, furtherPlayer(targetArray)), ()-> "ERROR: The target should be valid");
        assertTrue(bbqStrategy.areTargetValid(shooter, nearer(targetArray)), ()-> "ERROR: The target should be valid");
        assertFalse(bbqStrategy.areTargetValid(shooter, notAlligned(targetArray)), ()-> "ERROR: The target should be valid");




//        assertTrue(bbqStrategy.areTargetValid(shooter));

     }


    /**
     * Create a list of 2 adjacent player from player array, Player array should be defined as in the test
     * @param playerArray
     * @return
     */
    private List<Player> adjacentsPlayer(Player[] playerArray){
        List<Player> list = new LinkedList<>();
        list.add(playerArray[2]);
        list.add(playerArray[1]);
        return list;

    }

    /**
     * Create a player array that is not correct for the flame strategy. The first targer is further then the second
     * @param playerArray
     * @return
     */
    private List<Player> adjacentsPlayerNotCorrectForFlame(Player[] playerArray){
        List<Player> list = new LinkedList<>();
        list.add(playerArray[1]);
        list.add(playerArray[2]);
        return list;
    }

    private List<Player> furtherPlayer(Player[] playerArray){
        List<Player> list = new LinkedList<>();
        list.add(playerArray[1]);
        return list;
    }

    private List<Player> nearer(Player[] playerArray){
        List<Player> list = new LinkedList<>();
        list.add(playerArray[2]);
        return list;
    }

    private List<Player> notAlligned(Player[] playerArray){
        List<Player> list = new LinkedList<>();
        list.add(playerArray[2]);
        list.add(playerArray[0]);
        return list;
    }



}
