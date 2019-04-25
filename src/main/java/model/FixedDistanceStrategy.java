package model;

import java.util.List;

/**
 * FixedDistanceStrategy is used to know by a weapon if the target given are far from the shooter player by a fixed distance at least and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class FixedDistanceStrategy extends SeeStrategy {

    /**
     * distance represent number of moves by the shooter player
     */
    private int distance;

    /**
     * creates a FixedDistanceStrategy
     * @param gameMap of the match
     * @param distance from the shooter player at least
     */
    public  FixedDistanceStrategy(GameMap gameMap, int distance){
        super(gameMap);
        this.distance = distance;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        if (super.areTargetValid(shooter, targets)){
            for (Player p: targets){
                if (shooter.getTile().distance(p)<distance){
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        if(super.canHitSomeone(shooter, players)){
            for (Player p: players){
                if (shooter.getTile().distance(p)>=distance){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        List<Player> toReturn =  super.hittableTargets(shooter, players);
        if (toReturn.isEmpty()){
            return toReturn;
        }
        for(Player p:players){
            if (shooter.getTile().distance(p)<distance){
                toReturn.remove(p);
            }
        }
        return toReturn;
    }
}
