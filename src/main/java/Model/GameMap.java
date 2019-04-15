package Model;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private List<Room> rooms;



    private Tile referenceTile;

    public Tile getReferenceTile() {
        return referenceTile;
    }

    public void setReferenceTile(Tile referenceTile) {
        this.referenceTile = referenceTile;
    }

    public List<Room> getRooms() {

        return rooms;
    }

    public void setRooms(List<Room> rooms) {

        this.rooms = rooms;
    }


    public Tile getTile(Player p) {
        return p.getTile();
    }

    public List<Player> allVisiblePlayers(Player p)  {

        List<Player> visiblePlayers = new ArrayList<>();
        for (Tile tile: this.allVisibleTiles(p) ){
            visiblePlayers.addAll(tile.getPlayers()) ;
        }
        return visiblePlayers;
    }

    public List<Tile> allVisibleTiles(Player p)  {
        List<Tile> visibleTiles = new ArrayList<>();

        for(Room visibRoom: visibleRooms(p)){
            visibleTiles.addAll(visibRoom.getTiles());
        }

       return  visibleTiles;
    }

    public List<Room> visibleRooms(Player p) {

        List<Room> visibRooms = new ArrayList<>();

        for (Tile tile : getNearTiles(p)){
            visibRooms.add(tile.getRoom());
        }

        return visibRooms;
    }

    //ritorna i tiles che sono in direzioni nord est ovest e sud
    private List<Tile> getNearTiles(Player p){
        List<Tile> tiles = new ArrayList<>();
        Tile eastTile = p.getTile().getEastTile();
        Tile northTile = p.getTile().getNorthTile();
        Tile southTile = p.getTile().getSouthTile();
        Tile westTile = p.getTile().getWestTile();

        if(null!=eastTile){
            tiles.add(eastTile);
        }
        if(null!=northTile){
            tiles.add(northTile);
        }
        if(null!=southTile){
            tiles.add(southTile);
        }
        if(null!=westTile){
            tiles.add(westTile);
        }
        return tiles;
    }
}

