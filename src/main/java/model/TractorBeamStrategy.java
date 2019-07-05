package model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is one of the strategy used to determine the action needed to be done when the effect of the card
 * is chosen.Basically,what it does is, select a player (let it be visible or not to the shooter) and move the picked
 * target up to 2 squares keeping in mind that the new position should be a visible tile to the shooter, for then
 * inflicting the damage
 */
public class TractorBeamStrategy extends AbstractTargetStrategy {

    /**
     * Variable that is needed to determine distance between two players
     */
    private int distance;

    /**
     * Private attribute of type GameMap needed to determine other players position based from the shooter's
     * point of view
     */
    private Match match;

    /**
     * Creates a TractorBeamStrategy
     * @param distance
     * @param match
     */
    public TractorBeamStrategy(int distance, Match match) {
        this.distance = distance;
        this.match = match;
    }

    /**
     * This is the constructor of the class
     */
    public TractorBeamStrategy(Match match) {
        this.match = match;
    }

    public TractorBeamStrategy() {
    }

    /**
     * Classic method used to checker whether or not the list of targets selected by the player (shooter in our case)
     * are valid or not. Even if just one of them is not possible to be considered as a target, it should return false
     *
     * @param shooter is used to identify the player whose turn is
     * @param targets is a list of plausible targets (it doesn't mean they all MUST be true) seen by shooter's perspective
     * @return a boolean that depends whether or not, the list of targets chosen by the player is totally valid or not
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Tile> visibleTiles = match.getMap().allVisibleTiles(shooter);
        return visibleTiles.stream().anyMatch(tile -> tile.distance(targets.get(0), match.getMap()) < 3);
    }

    /**
     * Checks whether or not the player which is also the shooter an shoot to someone or not
      * @param shooter is the player whose turn is
     * @return a boolean
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    /**
     *  The following method returns a list of all players that are targets to our shooter
     * @param shooter is the player whose turn is
     * @return list of all players that are targets
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return match.getPlayers().stream().filter(player -> match.getMap().allVisibleTiles(shooter).stream().anyMatch(tile -> tile.distance(player, match.getMap()) <= 2) && !player.equals(shooter)).collect(Collectors.toList());
    }

}