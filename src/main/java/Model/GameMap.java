package Model;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private List<Room> rooms;
    private List<Tile> referenceTile;

    public List<Room> getRooms() {

        return rooms;
    }

    public void setRooms(List<Room> rooms) {

        this.rooms = rooms;
    }

    public List<Tile> getReferenceTile() {

        return referenceTile;
    }

    public void setReferenceTile(List<Tile> referenceTile) {

        this.referenceTile = referenceTile;
    }

    public Tile getTile(Player p) {
        return p.getTile();
    }

    public List<Player> allVisiblePlayers(Player p)  {

        List<Player> visiblePlayers = new ArrayList<>();
        for (Tile tile: this.allVisibleTiles(p) ){
            visiblePlayers.addAll(tile.getPlayersInTile()) ;
        }
        return visiblePlayers;
    }

    public List<Tile> allVisibleTiles(Player p)  {
        List<Tile> visibleTiles = new ArrayList<>();
        Tile eastTile = p.getTile().getEastTile();
        Tile northTile = p.getTile().getNorthTile();
        Tile southTile = p.getTile().getSouthTile();
        Tile westTile = p.getTile().getWestTile();


        visibleTiles.add(eastTile);
        visibleTiles.add(northTile);
        visibleTiles.add(southTile);
        visibleTiles.add(westTile);


       return  referenceTile;
    }

    public List<Room> visibleRooms(Player p) {
        //TODO must return something
        return null;
    }
    //room or rooms?! or both?
}

