package model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The following class is one of the strategies used to determine the way targets are chosen by the shooter.
 * */
public class VortexCannonStrategy extends AbstractTargetStrategy {

    private int distance;
    private Match match;
    /**
     * Private attribute of type GameMap needed to check other players position in the map
     * */
    private GameMap gameMap;

    /**
     * List of players that have already been attacked once. The following attribute is used when the optional effect
     * has been applied also
     * */
    private List<Player> attackedPlayers;

    /**
     * This is the constructor of our class
     * */
     public VortexCannonStrategy (GameMap gameMap, Match match){
         this.gameMap = gameMap ;
         this.match = match;
     }
    public  VortexCannonStrategy(int distance){
        this.distance = distance;
    }
     /**
      * Method used to check whether or not the targets chosen by our shooter are all valid or not
      * @param shooter is the player whose turn is and who got to chose the target/targets
      * @param targets list of players chosen BY the player to check whether or not they are valid targets or not
      * @return a boolean that it depends from the list of players selected by the shooter. If all valid then return true
      * */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return getHittableTargets(shooter).containsAll(targets);
    }

    /**
     * Method needed to determine whether or not there's at least one target available for the shooter.This way
     * we can avoid unneeded further methods to be used at
     * @param shooter is the player whose turn is
     * @return a boolean that checks whether or not the getHittableTargets method is empty or not
     * */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return !getHittableTargets(shooter).isEmpty();
    }

    /**
     * Method used to return list of all hittable targets from our shooter's map position.
     * Two conditions are needed to be met so that it gets validated:
     * 1. Filter all the players that do have a distance minor or equal to 1
     * 2. Remove yourself from the list that was generated in the first step
     * @param shooter is needed to check what players can he attack and obviously to remove himself from the list
     *                of possible targets
     * @return all the players that can be targeted by the shooter (the shooter isn't included)
     * */
//tra tutti i player,filtro quelli che hanno distanza di <=1 da visibletiles e l'altra condizione e' che non sia mestesso
    @Override
    public List<Player> getHittableTargets(Player shooter) {
         return match.getPlayers().stream().filter( player -> gameMap.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(player, true, true, true, true)<=1)) && !player.equals(shooter) ).collect(Collectors.toList());
    }
                        //THE FOLLOWING METHODS ARE NOT NEEDED AS OF RIGHT NOW
    /**
     * Method used to move the picked target (after verifying that the player is a valid target and destination
     * is a valid action also.
     * @param shooter needless to say, is the one who picks the target and target's new destination
     * @param target is the player who is being targeted by the shooter and whose destination will change
     * @param destination is the new Tile where the target will get moved into
     * */
    public void moveTarget(Player shooter,  Player target, Tile destination) {
        Tile startPoint = target.getTile();
        startPoint.getPlayers().remove(target);
        target.setTile(destination);
        destination.getPlayers().add(target);
        this.attackedPlayers.add(target);
    }

/**
 * This method is used in case the player wants to apply the optional effect also, Black Hole. Once the basic effect
 * has been applied, it checks if the new list of targets is available or not.
 * @param shooter is the player whose turn is and who gets to choose the target/targets
 * @param targets is the list of targets picked by the player. This doesn't mean all the targets should be valid
 * @return true or false based on the list of targets chosen by the player. If all the targets are valid targets,
 * then return true, else false
 * */
    public boolean areTargetValidBlackHole(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return hittableTargetsBlackHole(shooter).containsAll(targets);
    }

    /**
     * Method used for the optional effect, Black Hole. It checks whether or not the player, after performing
     * the basic effect, can perform or not the optional one also based on whether or not there is at list one
     * possible target
     * @param shooter is the player whose turn is
     * @return true or false by checking whether or not the hittableTargetsBlackHole is empty or not
     * */
    public boolean canHitSomeoneBlackHole(Player shooter) {
        return !hittableTargetsBlackHole(shooter).isEmpty();
    }

    /**
     * Method needed to return the list of players that are valid targets for our player (shooter)
     * @param shooter is the player whose turn is. It's needed to perform the action later on
     * @return list of all valid targets seen by our shooter's perspective
     * */
    public List<Player> hittableTargetsBlackHole(Player shooter) {
        return match.getPlayers().stream().filter( player -> gameMap.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(player, true, true, true, true)<=1)) && !player.equals(shooter) && !this.attackedPlayers.contains(player) ).collect(Collectors.toList());
    }

    /**
     * This method is used to move the hittable targets to their new destination which would be the Tile where the
     * Black Hole is placed at, chosen by the player
     * @param shooter  is the player whose turn is.He gets to chose the targets and also where to place the black hole
     * @param targets is the list of all the VALID players to whom the effect can be applied and chosen by the player
     * @param destination is the new destination where this/these target/targets will get placed at
     * */
    public void blackHole (Player shooter,  List<Player> targets, Tile destination){
        hittableTargetsBlackHole(shooter).forEach(player -> moveTarget(shooter,player,destination));
    }

}
