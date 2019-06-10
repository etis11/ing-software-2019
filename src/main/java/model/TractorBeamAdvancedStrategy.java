package model;

import java.util.List;
import java.util.stream.Collectors;

public class TractorBeamAdvancedStrategy extends AbstractTargetStrategy {

    private Match match;


    public TractorBeamAdvancedStrategy(Match match) {
        this.match = match;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return shooter.getTile().distance(targets.get(0), match.getMap()) < 3;
    }

    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return match.getPlayers().stream().filter(player -> shooter.getTile().distance(player, match.getMap()) <= 2 && !player.equals(shooter)).collect(Collectors.toList());
    }
}
