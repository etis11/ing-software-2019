package model;

import java.util.List;

/**
 * Interface that handles all strategies
 */
public interface TargetStrategy {
    /**
     * Method that checks if the list of given players is valid as targets for the shooter
     * @param shooter is the player whose turn is and got the right to shoot
     * @param targets list of possible targets for the shooter
     * @return a boolean
     */
    boolean areTargetValid(Player shooter, List<Player> targets);

    /**
     * Method that checks whether or not the shooter can hist any other player
     * @param shooter is the player whose turn is and got the right to shoot
     * @return a boolean
     */
    boolean canHitSomeone(Player shooter);

    /**
     * Gets a list of all targets seen by the shooter
     * @param shooter is the player who can shoot
     * @return list of players that are targets for the shooter
     */
    List<Player> getHittableTargets(Player shooter);

}
