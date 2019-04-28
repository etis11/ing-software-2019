package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LaserRifleStrategy extends AbstractTargetStrategy {

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {

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

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
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
