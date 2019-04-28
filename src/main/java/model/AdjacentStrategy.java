package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjacentStrategy extends AbstractTargetStrategy {

    private GameMap gameMap ;

    public AdjacentStrategy (GameMap gameMap){
        this.gameMap = gameMap;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> adjacentPlayers = gameMap.allAdjacentPlayers(shooter);
        return adjacentPlayers.containsAll(targets);
    }
    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        List<Player> adjacentPlayers = gameMap.allAdjacentPlayers(shooter);
        return players.stream().anyMatch(p ->  adjacentPlayers.contains(p) ) ;
    }



    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
       return gameMap.allAdjacentPlayers(shooter);
    }




 }
