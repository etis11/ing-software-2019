package model;

import java.util.LinkedList;
import java.util.List;

/**
 * The following class is used to determine the DontSeeStrategy. This strategy is used to target only NON visible
 * players to the shooter (the one whose turn is).So let it be an adjacent tile or not, it should work only for
 * non visible players!
 */
public class DontSeeStrategy extends AbstractTargetStrategy {

    private Match match;
    private GameMap gameMap;

    public DontSeeStrategy() {
    }

    /**
     * This is the constructor of our class
     */
    public DontSeeStrategy(Match match) {
        this.match = match;
    }

    /**
     * Method used to checker whether ort not the targets picked by the shooter (the player whose turn is)
     * are all possible targets (based by the requests of the effect) or not all of them.
     * In case at least one of them can't be targeted it should return false
     *
     * @param shooter is the player whose turn is and who decides to whom to shoot at
     * @param targets are the players being targeted by the shooter. To keep in mind that it doesn't mean that
     *                all the targets are VALID targets. That's why we're using the following method
     * @return a boolean which checks if the selected players are all targets or not
     */

    /**
     * The following method is helpful to understand if the list of the given
     * targets is valid Target List for the shooter
     * @param shooter is the player whose turn is and got the right to shoot
     * @param targets is the list of all the other players that can be shoot by the shooter
     * @return true if all the other player are valid targets for the shooter or not
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> notVisiblePlayers = match.getMap().allNotVisiblePlayers(shooter);
        for (Player p : targets) {
            if (!notVisiblePlayers.contains(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to determine if there is at least one player who can be targeted at or not.
     * In case that's not possible it wouldn't make any sense trying the above method since there wouldn't be
     * any available targets for the player to shoot at
     * @param shooter is the player who is going to perform the action. Needed to check all of his valid targets
     * @return a boolean when trying to check if there are any targets or not
     * */
    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> notVisiblePlayers = match.getMap().allNotVisiblePlayers(shooter);
        return match.getPlayers().stream().anyMatch(p -> notVisiblePlayers.contains(p));
    }


    /**
     * This method is used to return ALL the hittable players that can be considered as possible targets to the player
     * Useful to the shooter to check to whom he can shoot at.
     *
     * @param shooter needed to check who's the player who's asking to shoot so that then it can check and return
     *                all the possible players that can be his targets
     * @return list of players to whom the shooter can shoot at
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return new LinkedList<>(match.getMap().allNotVisiblePlayers(shooter));
    }
}
