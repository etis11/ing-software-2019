package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomStrategyTest {
    private Tile tile;
    private Tile adjacent;
    private Tile otherTile;
    private Tile oppositeTile;
    private Tile notVisibleTile;
    private Tile oppositeNotVisibleTile;
    private RoomStrategy strategy;
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
        strategy = new RoomStrategy(match);
    }

    @Test
    public void instanceTest(){
        assertTrue(strategy instanceof RoomStrategy, "ERROR: wrong instance");
    }

    @Test
    public void noPlayerInRoom(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(null, target));
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(shooter, null));

        notVisibleTile.addPlayer(target1);
        target1.setTile(notVisibleTile);
        assertFalse(strategy.canHitSomeone(shooter), "ERROR: there are no players");
        assertSame(strategy.getHittableTargets(shooter).size(),0, "ERROR: there are no players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there are player near to the shooter");

        target.add(target2);
        adjacent.addPlayer(target2);
        target2.setTile(adjacent);
        assertFalse(strategy.canHitSomeone(shooter), "ERROR: there are no players");
        assertSame(strategy.getHittableTargets(shooter).size(),0, "ERROR: there are no players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there are player near to the shooter");
    }

    @Test
    public void onePlayerInRoom(){
        List<Player> target = new ArrayList<>();

        otherTile.addPlayer(target1);
        target1.setTile(otherTile);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there is a player");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there is a  player");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is aplayer in the room");

        target.add(target2);
        adjacent.addPlayer(target2);
        target2.setTile(adjacent);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there is a players");
        assertSame(strategy.getHittableTargets(shooter).size(),1, "ERROR: there is a player");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is a player not in the room");
    }

    @Test
    public void twoPlayerInRoom(){
        List<Player> target = new ArrayList<>();

        target.add(target1);
        otherTile.addPlayer(target1);
        target1.setTile(otherTile);
        target.add(target2);
        oppositeNotVisibleTile.addPlayer(target2);
        target2.setTile(oppositeNotVisibleTile);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(),2, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there are two players in the room");
    }
}
