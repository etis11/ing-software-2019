package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * the current implementation requires
 */
public class FlameThrowerStrategy extends AbstractTargetStrategy {

    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks if throws an exception
        super.areTargetValid(shooter, targets);

    }

    /**
     * Tells if the shooter has an available target with the current weapon.
     * Being a flamethrower, checks if there is an available target on a cross centered on the shooter position.
     * @param shooter the player that wants to shoot
     * @param players
     * @return true if there is at least one player in a direction.s
     */
    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        Tile shooterTile = shooter.getTile();

        Tile northTile = shooterTile.getNorthTile();
        Tile eastTile = shooterTile.getEastTile();
        Tile southTile = shooterTile.getSouthTile();
        Tile westTile = shooterTile.getWestTile();

        if (northTile != null) {
            //check if there is a player
            if (!northTile.getPlayers().isEmpty()) return true;
                //check if in the same direction there is a player
            else if (northTile.getNorthTile() != null && (!northTile.getNorthTile().getPlayers().isEmpty()))
                return true;
        }

        if (eastTile != null) {
            //check if there is a player
            if (!eastTile.getPlayers().isEmpty()) return true;
                //check if in the same direction there is a player
            else if (eastTile.getEastTile() != null && (!eastTile.getEastTile().getPlayers().isEmpty())) return true;
        }

        if (southTile != null) {
            //check if there is a player
            if (!southTile.getPlayers().isEmpty()) return true;
                //check if in the same direction there is a player
            else if (southTile.getSouthTile() != null && (!southTile.getSouthTile().getPlayers().isEmpty()))
                return true;
        }

        if (westTile != null) {
            //check if there is a player
            if (!westTile.getPlayers().isEmpty()) return true;
                //check if in the same direction there is a player
            else if (westTile.getWestTile() != null && (!westTile.getWestTile().getPlayers().isEmpty())) return true;
        }

        return false;
    }

    /**
     * Returns a list of players that could be targeted by the weapon. This list contains all the players placed on a cross
     * centered on the shooting player. A combination of elements of this list could not be legal
     * @param shooter the player that is shooting
     * @param players
     * @return list of player on a cross
     */
    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        LinkedList<Player> hittableTargets = new LinkedList<>();
        Tile shooterTile = shooter.getTile();

        //need to get all the tiles
        Tile northTile = shooterTile.getNorthTile();
        Tile eastTile = shooterTile.getEastTile();
        Tile southTile = shooterTile.getSouthTile();
        Tile westTile = shooterTile.getWestTile();

        if (northTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(northTile.getPlayers());
                //and in the same direction
            if (northTile.getNorthTile() != null){
                hittableTargets.addAll(northTile.getNorthTile().getPlayers());
            }
        }

        if (eastTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(eastTile.getPlayers());
            //and in the same direction
            if (eastTile.getEastTile() != null){
                hittableTargets.addAll(eastTile.getEastTile().getPlayers());
            }
        }

        if (southTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(southTile.getPlayers());
            //and in the same direction
            if (southTile.getSouthTile() != null){
                hittableTargets.addAll(southTile.getSouthTile().getPlayers());
            }
        }

        if (westTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(westTile.getPlayers());
            //and in the same direction
            if (westTile.getWestTile() != null){
                hittableTargets.addAll(westTile.getWestTile().getPlayers());
            }
        }

        return (hittableTargets);
    }
}
