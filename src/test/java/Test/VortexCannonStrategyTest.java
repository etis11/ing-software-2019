package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VortexCannonStrategyTest {
    private GameMap map;
    private VortexCannonStrategy vortex;
    private Match match;

    @BeforeEach
    void initGameMap() {
        map = new GameMap();
        match = new Match();
        map.createGraph();
    }

    @Test
    void VortexStrategy() {
        Tile blue1 = new Tile(1, true, true);
        Tile pink1 = new Tile(2, true, true);
        Tile white1 = new Tile(3, true, true);
        Tile blue2 = new Tile(4, true, true);
        Tile pink2 = new Tile(5, true, true);
        Tile white2 = new Tile(6, true, true);
        Tile white3 = new Tile(7, true, true);

        Player shooter = new Player("Sprog");
        Player target1 = new Player("Dozer");
        Player target2 = new Player("Distruttore");
        Player target3 = new Player("Banshee");
        Player target4 = new Player("Dozer2");
        Player target5 = new Player("happytarget");

        blue1.setSouthTile(pink1);
        blue1.setWestTile(blue2);
        blue2.setSouthTile(pink2);
        blue2.setEastTile(blue1);
        pink1.setNorthTile(blue1);
        pink1.setWestTile(pink2);
        pink2.setNorthTile(blue2);
        pink2.setSouthTile(white2);
        pink2.setEastTile(pink1);
        white1.setWestTile(white2);
        white2.setNorthTile(pink2);
        white2.setWestTile(white3);
        white2.setEastTile(white1);
        white3.setEastTile(white2);

        blue1.getPlayers().add(shooter);
        pink1.getPlayers().add(target1);
        pink2.getPlayers().add(target2);
        white1.getPlayers().add(target3);
        pink1.getPlayers().add(target4);
        blue1.getPlayers().add(target5);

        shooter.setTile(blue1);
        target1.setTile(pink1);
        target2.setTile(pink2);
        target3.setTile(white1);
        target4.setTile(pink1);
        target5.setTile(blue1);

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

        List<Player> enemies = new LinkedList<>();
        enemies.add(target1);
        enemies.add(target2);
        enemies.add(target3);
        enemies.add(target4);
        enemies.add(target5);
        List<Player> targets = new LinkedList<>();
        targets.add(target1);
        targets.add(target2);
        targets.add(target4);
        targets.add(target5);

        List<Player> toSet=new ArrayList<>();
        toSet.add(shooter);
        toSet.addAll(enemies);
        match.setPlayers(toSet);


        assertNotNull(match.getPlayers());
        vortex = new VortexCannonStrategy(map, match);

        List<Tile> tiles = map.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).collect(Collectors.toList());
        assertNotNull(map.allVisibleTiles(shooter), "The shooter has some visible tiles");














        List<Player> playersss = match.getPlayers().stream().filter(playerr -> map.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(playerr, map) <= 1)) && !playerr.equals(shooter)).collect(Collectors.toList());


        // match.getPlayers().stream().filter(player -> match.getMap().allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(shooter, map) <= 1)) && !player.equals(shooter)).collect(Collectors.toList());



        toSet.add(shooter);
        toSet.addAll(targets);
        match.setPlayers(toSet);


        vortex.areTargetValid(shooter, targets);

        vortex.canHitSomeone(shooter);
        assertTrue(vortex.areTargetValid(shooter, targets), "but they should all be targets");
        assertTrue(vortex.areTargetValid(shooter, enemies), "target3 is not a target!!!");
        assertTrue(vortex.canHitSomeone(shooter), "He can hit one or more targets");
        assertNotNull(vortex.getHittableTargets(shooter), "But he has some targets!");
    }
}