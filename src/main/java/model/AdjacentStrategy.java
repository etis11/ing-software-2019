package model;

import java.util.List;

/**
 * Class used to determine the Adjacent Strategy which is used in case of targets around visible tiles
 * that have a distance of 1 from the shooter.It handles requests made by the player to checker whether
 * or not that are players in East,North,West,South and also shoots them IF possible and chosen by player
 */

public class AdjacentStrategy extends AbstractTargetStrategy {

    private Match match;

    /**
     * gameMap is a private attribute of type GameMap used later on to get players on the board when needed
     */
    private GameMap gameMap;

    public AdjacentStrategy(Match match) {
        this.match = match;
    }

    /**
     * Constructor method used in this class
     */
    public AdjacentStrategy(GameMap gameMap, Match match) {
        this.gameMap = gameMap;
        this.match = match;
    }

    /**
     * areTarget valid is a method used to check whether or not the chosen targets by the shooter( the player
     * whose turn it is) or valid or not.
     *
     * @param shooter is used to identify the player who chooses the targets to shoot at
     * @param targets is the list of targets chosen by the player whose turn is
     * @return true if all the chosen targets can be shot at,else false
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> adjacentPlayers = gameMap.allAdjacentPlayers(shooter);
        return adjacentPlayers.containsAll(targets);
    }

    /**
     * Method used to check whether or not you can hit at least one player (let it be a target the player has chosen
     * or not) . In case the player can't hit any other player it returns false so the player can't make use
     * of that effect of the card.
     *
     * @param shooter is used to identify the player who chooses the targets to shoot at
     * @return true if there is at least one player the shooter can shoot at
     */

    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> adjacentPlayers = gameMap.allAdjacentPlayers(shooter);
        return match.getPlayers().stream().anyMatch(p -> adjacentPlayers.contains(p));
    }

    // public boolean canHitSomeone(Player shooter) {
    //   return !getHittableTargets(shooter).isEmpty();
    //   }

    /**
     * The following method is used to return all the players that can be targeted by the shooter bvased on the
     * way the strategy itself works.
     *
     * @param shooter is used to reference to the player whose turn it is
     * @return the list of the players that the shooter can shoot at
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return gameMap.allAdjacentPlayers(shooter);
    }
}