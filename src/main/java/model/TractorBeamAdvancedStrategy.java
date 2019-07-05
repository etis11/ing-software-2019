package model;

import java.util.List;
import java.util.stream.Collectors;

public class TractorBeamAdvancedStrategy extends AbstractTargetStrategy {

    /**
     * variable that sets a Match
     */
    private Match match;

    /**
     * Creates a TractorBeamAdvancedStrategy
     * @param match
     */
    public TractorBeamAdvancedStrategy(Match match) {
        this.match = match;
    }

    /**
     * Method that checks whether or not the targets are all valid or not
     * @param shooter is the player whose turn is
     * @param targets is the list of all possible targets
     * @return a boolean
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return shooter.getTile().distance(targets.get(0), match.getMap()) < 3;
    }

    /**
     * Method needed to check whether or not the player which is also the shooter, can hit someone or not
     * @param shooter is the player whose turn is
     * @return true or false
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    /**
     * Method that returns list of all players that are targets for the shooter
     * @param shooter is the player whose turn is
     * @return list of all targets for the player whose turn is
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return match.getPlayers().stream().filter(player -> shooter.getTile().distance(player, match.getMap()) <= 2 && !player.equals(shooter)).collect(Collectors.toList());
    }
}
