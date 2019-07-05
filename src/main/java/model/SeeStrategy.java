package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SeeStrategy is used to know by a weapon if the target given can be seen by the shooter player and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class SeeStrategy extends AbstractTargetStrategy {

    /**
     * Variable that sets a match
     */
    private Match match;

    /**
     * creates a seeStrategy
     */
    public SeeStrategy(Match match) {
        this.match = match;
    }

    public SeeStrategy() {
    }

    /**
     * verify if all players in target are visible by shooter
     *
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> visiblePlayer = match.getMap().allVisiblePlayers(shooter);
        for (Player p : targets) {
            if (!visiblePlayer.contains(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone visible by shooter
     *
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> visiblePlayer = match.getMap().allVisiblePlayers(shooter);
        for (Player p : match.getPlayers()) {
            if (visiblePlayer.contains(p) && p != shooter) {
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player visible by shooter
     *
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> toReturn = new ArrayList<>();
        for (Player p : match.getMap().allVisiblePlayers(shooter)) {
            if (p != shooter) {
                toReturn.add(p);
            }
        }
        return toReturn;
    }
}
