package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacentDifferentTilesStrategy extends  AbstractTargetStrategy {
    private Match match;
    private GameMap map;

    public AdjacentDifferentTilesStrategy(int distance, Match match){
        this.map = map;
        this.match = match;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> adjacentPlayers = map.allAdjacentPlayers(shooter);
        List<Tile> targetsTile = new ArrayList<>();
        Set<Tile> set = new HashSet<Tile>(targetsTile);

        for (Player target : targets) {
            targetsTile.add(target.getTile());
        }
        if (adjacentPlayers.size() == 0 || adjacentPlayers.size() > 3) {
            return false;
        } else if (adjacentPlayers.size() == 1) {
            return true;
        } else if(set.size()<targetsTile.size()){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> adjacentPlayers = map.allAdjacentPlayers(shooter);
        return match.getPlayers().stream().anyMatch(p ->  adjacentPlayers.contains(p) ) ;
    }


    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return map.allAdjacentPlayers(shooter);
    }
}
