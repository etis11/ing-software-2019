package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DontSeeStrategyTest {
    private GameMap map;
    private DontSeeStrategy dontSeeStrategy;
    private Match match;

    @BeforeEach
    void initGameMap() {map =new GameMap();
    match = new Match();
    }

    @Test
    public void notVisiblePlayers() {
        Tile blue1 = new Tile();
        Tile pink1 = new Tile();
        Tile white1 = new Tile();
        Tile blue2 = new Tile();
        Tile pink2 = new Tile();
        Tile white2 = new Tile();
        Tile white3 = new Tile();

        Player shooter = new Player("Sprog");
        Player target1 = new Player("Dozer");
        Player target2 = new Player("Distruttore");
        Player target3 = new Player("Banshee");

        blue1.setSouthTile(pink1);
        blue1.setWestTile(blue2);
        blue2.setSouthTile(pink2);
        blue2.setEastTile(blue1);
        pink1.setNorthTile(blue1);
        pink1.setEastTile(pink2);
        pink2.setNorthTile(blue2);
        pink2.setSouthTile(white2);
        pink2.setEastTile(pink1);
        white1.setWestTile(white2);
        white2.setNorthTile(pink2);
        white2.setWestTile(white3);
        white2.setEastTile(white1);

        blue1.getPlayers().add(shooter);
        pink1.getPlayers().add(target1);
        pink2.getPlayers().add(target2);
        white1.getPlayers().add(target3);

        shooter.setTile(blue1);
        target1.setTile(pink1);
        target2.setTile(pink2);
        target3.setTile(white1);

        Room blue = new Room();
        Room pink = new Room();
        Room white = new Room();

        blue.addTile(blue1);
        blue.addTile(blue2);
        pink.addTile(pink1);
        pink.addTile(pink2);
        white.addTile(white1);
        white.addTile(white2);
        white.addTile(white3);

        map.addRoom(white);
        map.addRoom(pink);
        map.addRoom(blue);

        List<Player>  invisiblePlayers =map.allNotVisiblePlayers(shooter);
        assertTrue(invisiblePlayers.contains(target3),"Target should be invisible");
        assertFalse(invisiblePlayers.contains(target2),"Target should be visible");
        assertFalse(invisiblePlayers.contains(target1),"Target should be visible");
        assertTrue(!invisiblePlayers.contains(target1)&&!invisiblePlayers.contains(target2),"Targets should be invisible");
        assertFalse(invisiblePlayers.contains(shooter),"Shooter shouldn't be a target of himself");

        List<Player> targets = new LinkedList<>();
        targets.add(target1);
        targets.add(target2);
        targets.add(target3);
        dontSeeStrategy = new DontSeeStrategy(map, match);
        dontSeeStrategy.areTargetValid(shooter, targets);
        dontSeeStrategy.canHitSomeone(shooter);
        assertFalse(dontSeeStrategy.areTargetValid(shooter,targets), "You can't hit all them");
        //TODO controllare assertTrue(dontSeeStrategy.canHitSomeone(shooter),"Can hit someone");
        assertFalse(dontSeeStrategy.getHittableTargets(shooter).isEmpty(),"Can hit someone");
        assertFalse(dontSeeStrategy.getHittableTargets(shooter).contains(target1),"You can shoot Distruttore but YOU MUSTNT");
        assertFalse(dontSeeStrategy.getHittableTargets(shooter).contains(target2),"You can shoot DOzer but YOU MUSTNT");
        assertTrue(dontSeeStrategy.getHittableTargets(shooter).contains(target3),"You cant shoot Banshee but YOU MUST");
        assertTrue(dontSeeStrategy.getHittableTargets(shooter).size()<2,"there is just one target");

    }

}
