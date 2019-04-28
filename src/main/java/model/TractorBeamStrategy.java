package model;

import java.util.LinkedList;
import java.util.List;

public class TractorBeamStrategy extends AbstractTargetStrategy {

    private GameMap gameMap;

    public TractorBeamStrategy (GameMap gameMap){
        this.gameMap = gameMap;
    }

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

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Tile> visibleTiles = gameMap.allVisibleTiles(shooter);
        return visibleTiles.stream().anyMatch(tile ->  tile.distance(targets.get(0))<3 ) ;
    }

    public void moveTarget(Player shooter,  Player target, Tile destination){
        Tile startPoint = target.getTile();
        if(this.destinationTiles(shooter,target).contains(destination)){
            startPoint.getPlayers().remove(target);
            target.setTile(destination);
            destination.getPlayers().add(target);
        }
    }
}
