package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {

    private GameMap map;

    private AdjacentStrategy adjacentStrategy ;

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
        adjacentStrategy = new AdjacentStrategy(map);

        adjacentStrategy.areTargetValid(shooter,targets);
        assertFalse(adjacentStrategy.areTargetValid(shooter,targets),"Targets should not be valid");
        assertTrue(adjacentStrategy.canHitSomeone(shooter,targets),"Can hit someone");
        assertFalse(adjacentStrategy.hittableTargets(shooter,targets).isEmpty(),"Can hit someone");
        assertFalse(adjacentStrategy.hittableTargets(shooter,null).isEmpty(),"Can hit someone");
        assertTrue(adjacentStrategy.hittableTargets(shooter,targets).contains(target),"Can hit Arlind");
        assertFalse(adjacentStrategy.hittableTargets(shooter,targets).contains(target2),"Cannot hit Hamid");

    }

    @Test
    void correctRoomInsertion(){
        Room r = new Room();
        try{
            map.addRoom(r);
        }
        catch (NullPointerException n) {
            System.out.println(n.getMessage());
            fail(()-> "The room shouldnt be null");
        }

        assertSame(r, map.getRooms().get(0), ()-> "ERROR: the rooms should be the same");
    }

    @Test
    void correctRegenPointInsertion(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            map.addRegenPoint("red", red);
            map.addRegenPoint("blue", blue);
            map.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, map.getRedRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(blue, map.getBlueRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, map.getYellowRegenPoint(), ()->"ERROR: The regen points should be the same");

    }

    @Test
    void correctRegenPointInsertionParametric(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            map.addRegenPoint("red", red);
            map.addRegenPoint("blue", blue);
            map.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, map.getRegenPoint("red"), ()->"ERROR: The regen points should be the same");
        assertSame(blue, map.getRegenPoint("blue"), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, map.getRegenPoint("yellow"), ()->"ERROR: The regen points should be the same");
    }

    @Test
    void insertionExceptions(){
        Tile t = new Tile();
        String s = "red";
        assertThrows(IllegalArgumentException.class, ()-> map.addRegenPoint(null, t),
                ()-> "ERROR: The method should throw an exception because the color is null");
        assertThrows(IllegalArgumentException.class, ()-> map.addRegenPoint(s, null),
                ()-> "ERROR: the method should throw an exception because the tile is null");
    }

    @Test
    void gettingRegenPointsExceptions(){
        assertThrows(NullPointerException.class, ()-> map.getRedRegenPoint());
        assertThrows(NullPointerException.class, ()-> map.getBlueRegenPoint());
        assertThrows(NullPointerException.class, ()-> map.getYellowRegenPoint());

        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("red"));
        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("blue"));
        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("yellow"));
    }
}
