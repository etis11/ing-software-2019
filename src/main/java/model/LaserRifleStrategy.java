package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A strategy used fror the rifle lasers and for all the weapons that can shoot through walls in one direction
 */
public class LaserRifleStrategy extends AbstractTargetStrategy {

    /**
     * Return true if all the players are in the same direction.
     * @param shooter the player shooting
     * @param targets the list of possible targets
     * @return true if the list is correct
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks for exceptions;
        super.areTargetValid();
        String[] directions = {"north", "east", "south", "west"};
        List<Tile> tiles;
        List<Player> playerInDirection;
        boolean allPlayerCorrect = true;

        for(String d: directions){
            playerInDirection = new LinkedList<>();
            tiles = GameMap.getTilesDirectionBehindWall(d, shooter);
            for(Tile t: tiles){
                playerInDirection.addAll(t.getPlayers());
            }

            for(Player p: targets){
                if(!playerInDirection.contains(p)) allPlayerCorrect = false;
            }
            if (allPlayerCorrect) return true;

        }
        return false;
    }

    /**
     * This target returns true if there is a target in one of the cardinal direction, on a cross centered on the shooter
     * @param shooter the player that is shooting
     * @return true if there is an available target
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        String[] directions = {"north", "east", "south", "west"};
        List<Tile> tiles;
        //if there is a target in the same tile of the shooter, then he can be hit
        if(shooter.getTile().getPlayers().size() >1 ) return true;

        //else i have to check every tile in a given direction, ignoring the shooter's tile
        for(String dir: directions){
            tiles = GameMap.getTilesDirectionBehindWall(dir, shooter);
            for(int i = 1; i < tiles.size(); i++)
                if (!tiles.get(i).getPlayers().isEmpty()) return true;
        }

        return false;
    }

    /**
     * Returns a list of all the players (except for the shooter) in the four cardinal directions
     * @param shooter the player that is shooting
     * @return a list of players
     */
    @Override
    public List<Player> hittableTargets(Player shooter) {
        String[] directions = {"north", "east", "south", "west"};
        List<Tile> tiles;
        List<Player> possibleTargets = new LinkedList<>();

        //this line adds all the players in the same tile of the shooter except for the shooter
        possibleTargets.addAll(shooter.getTile().getPlayers());
        possibleTargets.remove(shooter);

        for(String dir: directions){
            tiles = GameMap.getTilesDirectionBehindWall(dir, shooter);
            //removes the cell where the player is
            tiles.remove(0);
            for(Tile t: tiles){
                possibleTargets.addAll(t.getPlayers());
            }
        }

        return new ArrayList<>(possibleTargets);

    }
}
