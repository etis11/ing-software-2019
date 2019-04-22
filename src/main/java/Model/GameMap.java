package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains all the reference to the rooms and spawn point of the map.
 * There should be at least one regenPoint, The rooms should at least be an empty list.
 */
public class GameMap {

    /**
     * List of the rooms in the GameMap
     */
    private List<Room> rooms;

    /**
     * A map that contains all the regent points
     */
    private Map<String, Tile> regenPoints;

    /**
     * Creates a game map with no rooms and with no regen points
     */
    public GameMap(){
        rooms = new ArrayList<>();
        regenPoints = new HashMap<>();
    }

    /**
     * Returns the rooms in the gameMap
     * @return a copy of the list. however the rooms are not copied. Only the list
     */
    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    /**
     * Add the given room to the game map
     * @param toAdd the room that has to be added
     * @throws NullPointerException the given room is null
     */
    public void addRoom(Room toAdd) throws NullPointerException{
        if (toAdd== null) throw new NullPointerException("The given room is null");
        rooms.add(toAdd);
    }

    /**
     * Gets all the players that are visible for the given player
     * @param p the player that wants to know which are the other visible players
     * @return a list of all the players seen by p, EXCEPT p
     */
    public List<Player> allVisiblePlayers(Player p)  {

        List<Player> visiblePlayers = new ArrayList<>();
        for (Tile tile: this.allVisibleTiles(p) ){
            visiblePlayers.addAll(tile.getPlayers()) ;
        }
        return visiblePlayers;
    }

    /**
     *
     * @param p
     * @return
     */
    public List<Tile> allVisibleTiles(Player p)  {
        List<Tile> visibleTiles = new ArrayList<>();

        for(Room visibRoom: visibleRooms(p)){
            visibleTiles.addAll(visibRoom.getTiles());
        }

       return  visibleTiles;
    }

    /**
     * 
     * @param p
     * @return
     */
    public List<Room> visibleRooms(Player p) {

        List<Room> visibRooms = new ArrayList<>();

        for (Tile tile : getNearTiles(p)){
            visibRooms.add(tile.getRoom());
        }

        return visibRooms;
    }

    /**
     * Returns the tiles near the given player
     * @param p the player
     * @return all not null tiles around the player
     */
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

