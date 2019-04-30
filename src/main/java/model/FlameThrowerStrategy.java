package model;
;
import java.util.LinkedList;
import java.util.List;

/**
 * A strategy that implements the behavior of the FlameThrower and the BBQ mode. Works fine if the given targets are 1 or 2.
 * Could be a problem when applying the effect, so this class has to be watched carefully.
 */
public class FlameThrowerStrategy extends AbstractTargetStrategy {

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks if throws an exception
        super.areTargetValid(shooter, targets);
        String[] directions = {"north", "east", "south", "west"};

        //first of all, check if the list has 2 player or is empty. Then the shooter and the 2 players should be aligned.
        if (targets.size() > 2 || targets.isEmpty())
            throw new IllegalArgumentException("There number of players should be at max 2 and should not be empty.");
        if (targets.size() == 2) {
            //if the 2 players are in the same tile, the targets are not valid
            if (targets.get(0).getTile() == targets.get(1).getTile()) return false;

            List<Tile> tiles;
            for (String dir : directions) {
                tiles = shooter.getTile().getTilesInDirection(dir);
                Player target1 = targets.get(0);
                Player target2 = targets.get(1);
                //if there are only 2 tiles, the 3 player cant be aligned
                if (tiles.size() >= 3) {
                    //maybe the check on the shooter is not necessary
                    if (tiles.get(0).isPlayerIn(shooter) && tiles.get(1).isPlayerIn(target1) && tiles.get(2).isPlayerIn(target2))
                        return true;
                }
            }
        }
        //the target list contains only one player
        else {
            List<Tile> tiles;
            Player target = targets.get(0);
            for (String dir : directions) {
                tiles = shooter.getTile().getTilesInDirection(dir);
                //check if is in an adjacent tile. The adjacent tile should exist (size at least of 2)
                if (tiles.size() >= 2 && tiles.get(1).isPlayerIn(target)) return true;
                //checks if it's in the tile after (size at least of 3, since there is the shooter tile, then an empty tile
                //then the tile where the player should be
                if (tiles.size() >= 3 && tiles.get(2).isPlayerIn(target)) return true;
            }
        }
        return false;
    }

    /**
     * Tells if the shooter has an available target with the current weapon.
     * Being a flamethrower, checks if there is an available target on a cross centered on the shooter position.
     * @param shooter the player that wants to shoot
     * @return true if there is at least one player in a direction.s
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
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
     *
     * @param shooter the player that is shooting
     * @return list of player on a cross
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
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
            if (northTile.getNorthTile() != null) {
                hittableTargets.addAll(northTile.getNorthTile().getPlayers());
            }
        }

        if (eastTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(eastTile.getPlayers());
            //and in the same direction
            if (eastTile.getEastTile() != null) {
                hittableTargets.addAll(eastTile.getEastTile().getPlayers());
            }
        }

        if (southTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(southTile.getPlayers());
            //and in the same direction
            if (southTile.getSouthTile() != null) {
                hittableTargets.addAll(southTile.getSouthTile().getPlayers());
            }
        }

        if (westTile != null) {
            //adds the player in the north tile
            hittableTargets.addAll(westTile.getPlayers());
            //and in the same direction
            if (westTile.getWestTile() != null) {
                hittableTargets.addAll(westTile.getWestTile().getPlayers());
            }
        }

        return (hittableTargets);
    }
}
