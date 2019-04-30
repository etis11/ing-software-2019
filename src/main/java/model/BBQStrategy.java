package model;

import java.util.List;

/**
 *this strategy extends the flameThrowerStrategy, but  just the areTargetsValid method, since the others work fine
 */
public class BBQStrategy extends FlameThrowerStrategy {

    /**
     *Checks if all the targets are in the same direction. The check is made on a cross centered in the shooter at a max distance
     * of two. More players can be in the same tile
     * @param shooter the player that is shooting
     * @param targets a list of players that has to be checked
     * @return true if the targets are aligned within 2 movements from the shooter.
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks if the parameters are correct
        super.areTargetValid(shooter, targets);

        String[] directions = { "north", "east", "south", "west"};

        //checks if the list is not empty
        if(targets.isEmpty()) throw  new IllegalArgumentException("This list should not be empty");

        //else checks that all the players are placed in the same direction
        List<Tile> tiles;
        for(String dir : directions){
            tiles = shooter.getTile().getTilesInDirection(dir);
            // shooterTile -> tile1 -> tile2, the other are irrelevant
            Tile tile1;
            Tile tile2;
            boolean allPlayerRight = true;
            //se If there's only 1 tile, this mean that the direction is not right for sure for the check

            //if there are 2 tiles, this mean that all the player should be in the same tile
            if (tiles.size() == 2){
                allPlayerRight = true;
                tile1 = tiles.get(1);
                for(Player p: targets){
                    if (!tile1.isPlayerIn(p)) allPlayerRight = false;
                }

                if (allPlayerRight) return true;

            }

            //if there are 3 tiles, this mean that the player should be in the last 2
            if (tiles.size()>=3){
                tile1 = tiles.get(1);
                tile2 = tiles.get(2);
                allPlayerRight = true;
                for(Player p : targets){
                    //if the player is not in the tile1 and in the tile2, then this direction is not correct
                    if(!tile1.isPlayerIn(p)&&!tile2.isPlayerIn(p)) allPlayerRight = false;
                }
                if (allPlayerRight) return true;
            }

        }
        return false;
    }

}
