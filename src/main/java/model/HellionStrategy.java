package model;

import java.util.List;

public class HellionStrategy extends AbstractTargetStrategy {

    private Match match;
    private int distance;

    public HellionStrategy(int distance, Match match) {
        this.distance = distance;
        this.match = match;
    }

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

    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

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