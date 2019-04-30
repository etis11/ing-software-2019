package model;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is one of the strategy used to determine the action needed to be done when the effect of the card
 * is chosen.Basically,what it does is, select a player (let it be visible or not to the shooter) and move the picked
 * target up to 2 squares keeping in mind that the new position should be a visible tile to the shooter, for then
 * inflicting the damage
 * */
public class TractorBeamStrategy extends AbstractTargetStrategy {

    /**
     * Private attribute of type GameMap needed to determine other players position based from the shooter's
     * point of view
     * */
    private GameMap gameMap;

    /**
     * This is the constructor of the class
     * */
    public TractorBeamStrategy (GameMap gameMap){
        this.gameMap = gameMap;
    }

    /**
     * The following method accepts as input two parameters and is needed to grab all the visible tiles of the player
     * whose turn is.The way it works is that it checks all the possible tiles of a distance minor to 3 from where the
     * target is located at and from all of them, returns ONLY the ones that are visible to our shooter
     * @param shooter needed to check his visible tiles
     * @param target is the target chosen from another method to be used and grab all the tiles of a distance<3 from
     *               the target
     * @return all the tiles are have a distance<3 from the target and at the same time are visible to the shooter
     * */
    //TODO fare test con metodo distance
    public List<Tile> destinationTiles(Player shooter, Player target){
        List<Tile> visibleTiles = gameMap.allVisibleTiles(shooter);
        List<Tile> destinations = new LinkedList<>();
        for(Tile tile : visibleTiles){
            if(tile.distance(target)<3){
                destinations.add(tile);
            }
        }
        return destinations;
    }

    /**
     * Classic method used to checker whether or not the list of targets selected by the player (shooter in our case)
     * are valid or not. Even if just one of them is not possible to be considered as a target, it should return false
     * @param shooter is used to identify the player whose turn is
     * @param targets is a list of plausible targets (it doesn't mean they all MUST be true) seen by shooter's perspective
     * @return a boolean that depends whether or not, the list of targets chosen by the player is totally valid or not
     * */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Tile> visibleTiles = gameMap.allVisibleTiles(shooter);
        return visibleTiles.stream().anyMatch(tile ->  tile.distance(targets.get(0))<3 ) ;
    }

    /**
     * THe following method is used to move the chosen target to the chosen tile by the player
     * @param shooter is needed to determine who is the shooter(whose turn is moreover)
     * @param target is the target CHOSEN by the player to be moved
     * @param destination is the new tile where the target should be moved at
     * */
    public void moveTarget(Player shooter,  Player target, Tile destination){
        Tile startPoint = target.getTile();
        if(this.destinationTiles(shooter,target).contains(destination)&&hittableTargets(shooter).contains(target)){
            startPoint.getPlayers().remove(target);
            target.setTile(destination);
            destination.getPlayers().add(target);
        }
    }

}