package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeeStrategyTest {
    private Tile tile;
    private Tile adjacent;
    private Tile otherTile;
    private Tile oppositeTile;
    private Tile notVisibleTile;
    private Tile oppositeNotVisibleTile;
    private SeeStrategy strategy;
    private GameMap gameMap;
    private Match match;
    private Player shooter;
    private Player target1;
    private Player target2;
    private Room room;
    private Room room2;

    @BeforeEach
    public void init(){
        match = new Match();
        gameMap = new GameMap();
        room = new Room();
        room2 = new Room();
        adjacent = new Tile();
        notVisibleTile = new Tile(null, null, adjacent, null, false, false);
        oppositeTile = new Tile();
        oppositeNotVisibleTile = new Tile();
        otherTile = new Tile(null, notVisibleTile, null, oppositeNotVisibleTile, false, false);
        tile = new Tile(otherTile, null, oppositeTile, null, false, false);
        otherTile.setSouthTile(tile);
        oppositeTile.setNorthTile(tile);
        notVisibleTile.setWestTile(otherTile);
        oppositeNotVisibleTile.setEastTile(otherTile);
        adjacent.setNorthTile(notVisibleTile);
        adjacent.setWestTileBehindWall(tile);
        tile.setEastTileBehindWall(adjacent);
        room.addTile(oppositeTile);
        room.addTile(otherTile);
        room.addTile(tile);
        room.addTile(oppositeNotVisibleTile);
        room2.addTile(notVisibleTile);
        room2.addTile(adjacent);
        gameMap.addRoom(room);
        gameMap.addRoom(room2);
        match.setMap(gameMap);
        strategy = new SeeStrategy(match);
        shooter = new Player("Shooter");
        tile.addPlayer(shooter);
        shooter.setTile(tile);
        target1 = new Player("Target1") ;
        target2 = new Player("Target2") ;
        LinkedList<Player> players = new LinkedList<>();
        players.add(shooter);
        players.add(target1);
        players.add(target2);
        match.setPlayers(players);
    }

    @Test
    public void instanceTest(){
        assertTrue(strategy instanceof SeeStrategy, "ERROR: wrong instance");
    }

    @Test
    public void onlyShooterVisible(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(null, target));
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(shooter, null));

        target.add(target2);
        assertFalse(strategy.canHitSomeone(shooter), "ERROR: there are no players");
        assertSame(strategy.getHittableTargets(shooter).size(),0, "ERROR: there are no players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");

    }

    @Test
    public void onePlayerVisibleMelee(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        target1.setTile(tile);
        tile.addPlayer(target1);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is the target");

        target.add(target2);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there are players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is wrong target");

        target2.setTile(notVisibleTile);
        notVisibleTile.addPlayer(target2);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there are players");
       assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is wrong target");
    }

    @Test
    public void onePlayerVisible(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        target1.setTile(oppositeTile);
        oppositeTile.addPlayer(target1);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is the target");

        target.add(target2);
        target2.setTile(adjacent);
        adjacent.addPlayer(target2);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there are players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is wrong target");

        otherTile.addPlayer(shooter);
        shooter.setTile(otherTile);
        try {
            tile.removePlayer(shooter);
        } catch (Exception e) { e.printStackTrace();}
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),2, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is wrong target");
    }

    public void twoPlayerVisible(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        target1.setTile(oppositeTile);
        oppositeTile.addPlayer(target1);
        target.add(target2);
        adjacent.addPlayer(target2);
        target2.setTile(adjacent);
        otherTile.addPlayer(shooter);
        shooter.setTile(otherTile);
        try {
            tile.removePlayer(shooter);
        } catch (Exception e) { e.printStackTrace();}
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),2, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is wrong target");
    }

}
