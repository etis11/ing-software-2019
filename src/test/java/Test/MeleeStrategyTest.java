package Test;

import model.Match;
import model.MeleeStrategy;
import model.Player;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeleeStrategyTest {

    private Tile tile;
    private Tile otherTile;
    private MeleeStrategy strategy;
    private Match match;
    private Player shooter;
    private Player target1;
    private Player target2;

    @BeforeEach
    public void init() {
        match = new Match();
        strategy = new MeleeStrategy(match);
        otherTile = new Tile();
        tile = new Tile(otherTile, null, null, null, false, false);
        otherTile.setSouthTile(tile);
        shooter = new Player("Shooter");
        tile.addPlayer(shooter);
        shooter.setTile(tile);
        target1 = new Player("Target1");
        target2 = new Player("Target2");
        LinkedList<Player> players = new LinkedList<>();
        players.add(shooter);
        players.add(target1);
        players.add(target2);
        match.setPlayers(players);
    }

    @Test
    public void instanceTest() {
        assertTrue(strategy instanceof MeleeStrategy, "ERROR: wrong instance");
    }

    @Test
    public void onlyShooterInTile() {
        List<Player> target = new ArrayList<>();

        target.add(target1);
        assertFalse(strategy.canHitSomeone(shooter), "ERROR: there are no players");
        assertSame(strategy.getHittableTargets(shooter).size(), 0, "ERROR: there are no players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");

        assertThrows(IllegalArgumentException.class, () -> strategy.areTargetValid(null, target));
        assertThrows(IllegalArgumentException.class, () -> strategy.areTargetValid(shooter, null));

        target1.setTile(otherTile);
        otherTile.addPlayer(target1);
        otherTile.addPlayer(target2);
        target2.setTile(otherTile);
        assertFalse(strategy.canHitSomeone(shooter), "ERROR: there are no players");
        assertSame(strategy.getHittableTargets(shooter).size(), 0, "ERROR: there are no players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");
    }

    @Test
    public void onePlayerInShooterTile() {
        List<Player> target = new ArrayList<>();

        target.add(target1);
        target1.setTile(tile);
        tile.addPlayer(target1);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(), 1, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is the target");

        target.add(target2);
        assertSame(strategy.getHittableTargets(shooter).size(), 1, "ERROR: there are players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there aren't all target");

        otherTile.addPlayer(target2);
        target2.setTile(otherTile);
        assertSame(strategy.getHittableTargets(shooter).size(), 1, "ERROR: there are players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there aren't all target");

        target1.setTile(otherTile);
        otherTile.addPlayer(target1);
        try {
            tile.removePlayer(target1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        target2.setTile(tile);
        tile.addPlayer(target2);
        try {
            otherTile.removePlayer(target2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame(strategy.getHittableTargets(shooter).size(), 1, "ERROR: there are players");
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there aren't all target");
    }

    @Test
    public void twoPlayersInTile() {
        List<Player> target = new ArrayList<>();

        target.add(target1);
        target.add(target2);
        target1.setTile(tile);
        tile.addPlayer(target1);
        target2.setTile(tile);
        tile.addPlayer(target2);
        assertTrue(strategy.canHitSomeone(shooter), "ERROR: there are players");
        assertSame(strategy.getHittableTargets(shooter).size(), 2, "ERROR: there are players");
        assertTrue(strategy.areTargetValid(shooter, target), "ERROR: there is the target");
    }

}
