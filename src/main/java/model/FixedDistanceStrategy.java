package model;

import java.util.List;

/**
 * FixedDistanceStrategy is used to know by a weapon if the target given are far from the shooter player by a fixed distance at least and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class FixedDistanceStrategy extends SeeStrategy {

    private Match match;

    /**
     * distance represent number of moves by the shooter player
     */
    private int distance;

    /**
     * creates a FixedDistanceStrategy
     * @param gameMap of the match
     * @param distance from the shooter player at least
     */
    public  FixedDistanceStrategy(GameMap gameMap, int distance, Match match){
        super(gameMap, match);
        this.distance = distance;
    }

    /**
     * verify if all players in target are at least distance tiles far from the shooter
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
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

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone at least distance tiles far from shooter
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        if(super.canHitSomeone(shooter)){
            for (Player p: match.getPlayers()){
                if (shooter.getTile().distance(p)>=distance){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular players at least far distance tiles from the shooter
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> toReturn =  super.getHittableTargets(shooter);
        if (toReturn.isEmpty()){
            return toReturn;
        }
        for(Player p:match.getPlayers()){
            if (shooter.getTile().distance(p)<distance){
                toReturn.remove(p);
            }
        }
        return toReturn;
    }
}
