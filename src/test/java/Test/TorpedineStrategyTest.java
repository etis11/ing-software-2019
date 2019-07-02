package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TorpedineStrategyTest {
    private Tile tile;
    private Tile adjacent;
    private Tile otherTile;
    private Tile oppositeTile;
    private Tile notVisibleTile;
    private Tile oppositeNotVisibleTile;
    private TorpedineStrategy strategy;
    private GameMap gameMap;
    private Match match;
    private Player shooter;
    private Player target1;
    private Player target2;
    private Player target3;
    private Room room;
    private Room room2;

    @BeforeEach
    public void init() {
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
        room.addTile(adjacent);
        gameMap.addRoom(room);
        gameMap.addRoom(room2);
        match.setMap(gameMap);

        strategy = new TorpedineStrategy(match);
        shooter = new Player("Shooter");
        tile.addPlayer(shooter);
        shooter.setTile(tile);
        target1 = new Player("Target1");
        target2 = new Player("Target2");
        target3 = new Player("Target3");
        target1.setTile(adjacent);
        target2.setTile(adjacent);
        target3.setTile(adjacent);
        adjacent.getPlayers().add(target1);
        adjacent.getPlayers().add(target2);
        adjacent.getPlayers().add(target3);

        LinkedList<Player> players = new LinkedList<>();
        players.add(shooter);
        players.add(target1);
        players.add(target2);
        players.add(target3);
        match.setPlayers(players);
    }

    @Test
    public void instanceTest() {
        assertTrue(strategy instanceof TorpedineStrategy, "ERROR: wrong instance");
    }

    @Test
    public void onlyShooterVisible() {
        List<Player> target = new ArrayList<>();

        target.add(target1);


        assertThrows(IllegalArgumentException.class, () -> strategy.areTargetValid(null, target));
        assertThrows(IllegalArgumentException.class, () -> strategy.areTargetValid(shooter, null));


        target.add(target2);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are no players");



        assertFalse(strategy.getHittableTargets(shooter).isEmpty(), "ERROR: there are no players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");

        target.add(target3);
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");

    }
}
