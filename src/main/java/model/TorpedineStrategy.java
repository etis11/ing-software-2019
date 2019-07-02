package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TorpedineStrategy extends AbstractTargetStrategy {

    private Match match;

    public TorpedineStrategy(Match match) {
        this.match = match;
    }

    public TorpedineStrategy() {
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> visiblePlayer = match.getMap().allVisiblePlayers(shooter);
        List<Player> visiblePlayer2 = match.getMap().allVisiblePlayers(targets.get(0));
        List<Player> visiblePlayer3 = match.getMap().allVisiblePlayers(targets.get(1));
        if (!visiblePlayer.contains(targets.get(0))) {
            return false;
        }
        if (!visiblePlayer2.contains(targets.get(1))) {
            return false;
        }
        if (!visiblePlayer3.contains(targets.get(2))) {
            return false;
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
