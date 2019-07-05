package model;

import java.util.List;

/**
 * this strategies hits all the players in a room
 */
public class HellionStrategy extends AbstractTargetStrategy {

    /**
     * private attribute Match
     */
    private Match match;

    /**
     * Constructor of the class
     * @param match needed for the class
     */
    public HellionStrategy(Match match) {
        this.match = match;
    }

    /**
     * Method that returns list of hittable targets seen by our shooter
     * @param shooter is the player whose turn is
     * @return list of target-players seen by our shooter's point of view
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> toReturn = super.getHittableTargets(shooter);
        if (toReturn.isEmpty()) {
            return toReturn;
        }
        for (Player p : match.getPlayers()) {
            if (shooter.getTile().distance(p, match.getMap()) < 2 || !this.match.getMap().allVisiblePlayers(shooter).contains(p)) {
                toReturn.remove(p);
            }
        }
        return toReturn;
    }

    /**
     * Method to determine whether or not there's a valid target to be hit by our shooter. Needed in case there's
     * no possible target so that the player can stop going forward and try to hit someone
     * @param shooter is the player whose turn is
     * @return a boolean
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    /**
     * Given a list of possible targets,it should check whether or not all of them are valid targets.
     * In case not all of them are possible targets, it returns false,else true
     * @param shooter is the player whose turn is
     * @param targets list of targets that the player thinks can shoot at
     * @return true or false
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        for (int i = 0; i < targets.size(); i++) {
            if (!super.getHittableTargets(shooter).contains(targets.get(i))) {
                return false;
            }
        }
        return true;
    }
}