package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdjacentStrategyTest {
    private GameMap map;
    private AdjacentStrategy adjacentStrategy;

    @BeforeEach
    void initGameMap(){
        map = new GameMap();
    }
    @Test
    void adjacentPlayersTesting() {
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();
        Tile green = new Tile();

        Player shooter = new Player("Etis");
        Player target = new Player("Arlind");
        Player target2 = new Player("Hamid");

        red.setEastTile(blue);
        blue.setWestTile(red);
        red.setNorthTile(yellow);
        yellow.setSouthTile(red);
        red.getPlayers().add(shooter);
        shooter.setTile(red);
        yellow.getPlayers().add(target);
        target.setTile(yellow);
        green.setWestTile(yellow);
        yellow.setEastTile(green);
        green.getPlayers().add(target2);
        target2.setTile(green);

        Room room1 = new Room();
        Room room2 = new Room();
        room1.addTile(red);
        room1.addTile(blue);
        room2.addTile(yellow);
        room2.addTile(green);

        map.addRoom(room1);
        map.addRoom(room2);

        List<Player> adjacentPlayers = map.allAdjacentPlayers(shooter);

        assertTrue(adjacentPlayers.contains(target),"Target should be in Adjacent");
        assertFalse(adjacentPlayers.contains(shooter),"shooter should not be in Adjacent");
        assertFalse(adjacentPlayers.contains(target2),"target2 should not be in Adjacent");

        List<Player> targets = new LinkedList<>();
        targets.add(target);
        targets.add(target2) ;
        adjacentStrategy = new AdjacentStrategy(map, null);

        adjacentStrategy.areTargetValid(shooter,targets);
        assertFalse(adjacentStrategy.areTargetValid(shooter,targets),"Targets should not be valid");
        assertFalse(adjacentStrategy.canHitSomeone(shooter),"Can hit someone");
        assertFalse(adjacentStrategy.getHittableTargets(shooter).isEmpty(),"Can hit someone");
        assertTrue(adjacentStrategy.getHittableTargets(shooter).contains(target),"Can hit Arlind");
        assertFalse(adjacentStrategy.getHittableTargets(shooter).contains(target2),"Cannot hit Hamid");

    }
}
