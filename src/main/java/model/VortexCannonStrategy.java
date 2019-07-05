package model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The following class is one of the strategies used to determine the way targets are chosen by the shooter.
 */
public class VortexCannonStrategy extends AbstractTargetStrategy {

    /**
     * Variable that sets a Match
     */
    private Match match;
    /**
     * Private attribute of type GameMap needed to check other players position in the map
     */
    private GameMap gameMap;

    /**
     * This is the constructor of our class
     */
    public VortexCannonStrategy(GameMap gameMap, Match match) {
        this.gameMap = gameMap;
        this.match = match;
    }

    public VortexCannonStrategy( Match match) {
        this.match = match;
    }

    /**
     * Method used to check whether or not the targets chosen by our shooter are all valid or not
     *
     * @param shooter is the player whose turn is and who got to chose the target/targets
     * @param targets list of players chosen BY the player to check whether or not they are valid targets or not
     * @return a boolean that it depends from the list of players selected by the shooter. If all valid then return true
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return getHittableTargets(shooter).containsAll(targets);
    }

    /**
     * Method needed to determine whether or not there's at least one target available for the shooter.This way
     * we can avoid unneeded further methods to be used at
     *
     * @param shooter is the player whose turn is
     * @return a boolean that checks whether or not the getHittableTargets method is empty or not
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    /**
     * Method used to return list of all hittable targets from our shooter's map position.
     * Two conditions are needed to be met so that it gets validated:
     * 1. Filter all the players that do have a distance minor or equal to 1
     * 2. Remove yourself from the list that was generated in the first step
     *
     * @param shooter is needed to check what players can he attack and obviously to remove himself from the list
     *                of possible targets
     * @return all the players that can be targeted by the shooter (the shooter isn't included)
     */
//tra tutti i player,filtro quelli che hanno distanza di <=1 da visibletiles e l'altra condizione e' che non sia mestesso
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return match.getPlayers().stream().filter(player -> gameMap.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(player, gameMap) <= 1)) && !player.equals(shooter)).collect(Collectors.toList());
    }
}
