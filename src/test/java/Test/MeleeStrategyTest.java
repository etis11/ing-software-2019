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
    public void init(){
        match = new Match();
        strategy = new MeleeStrategy(match);
        otherTile = new Tile();
        tile = new Tile(otherTile, null, null, null, false, false);
        otherTile.setSouthTile(tile);
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
    public void onlyShooterInTile(){
        List<Player> target = new ArrayList<>();
        target.add(target1);
        assertFalse(strategy.areTargetValid(shooter, target), "ERROR: there is only the shooter");
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(null, target));
        assertThrows(IllegalArgumentException.class, ()->strategy.areTargetValid(shooter, null));
    }

}
