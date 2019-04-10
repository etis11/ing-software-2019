package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * The Tile class is the element that constitutes a GameMap. A Tile contains the reference of the adjacent tiles in the map.
 * All the adjacent tiles are different ( example: northTile and SouthTile can't both point to the same object).
 * A tile contains the players that are placed in it.
 */
public  abstract class Tile {

    /**
     * reference to the tile placed to the north. Can be null.
     */
    private Tile northTile;

    /**
     * reference to the tile placed to the east. Can be null.
     */
    private Tile eastTile;
    /**
     * reference to the tile placed to the south. Can be null.
     */
    private Tile southTile;

    /**
     * reference to the tile placed to the west. Can be null.
     */
    private Tile westTile;

    /**
     * list of all players that are in the tile. If the player is present, there is only one occurence.
     * Can't be null. Can be a empty list. All players in the list are different.
     */
    private List<Player> players;

    /**
     * creates a tile with all the values set to null
     */
    public Tile(){
        northTile = null;
        eastTile = null;
        southTile = null;
        westTile = null;
        players = new LinkedList<>();
    }

    /**
     * Creates a tile given the surrounding tiles. The parameters can be null.
     * @param north north tile
     * @param east east tile
     * @param south south tile
     * @param west west tile
     */
    public Tile(Tile north, Tile east, Tile south, Tile west){
        northTile = north;
        eastTile = east;
        southTile = south;
        westTile = west;
        players = new LinkedList<>();
    }

    /**
     * returns the tile place to the east
     * @return the east tile, can be null
     */
    public Tile getEastTile() {
        return eastTile;
    }

    /**
     * assign to the east tile a know tile.
     * @param eastTile a tile
     */
    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    /**
     * returns the tile place to the west
     * @return the west tile, can be null
     */
    public Tile getWestTile() {
        return westTile;
    }

    /**
     * assign to the west tile a know tile.
     * @param westTile a tile
     */
    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    /**
     * returns the tile place to the north
     * @return the north tile, can be null
     */
    public Tile getNorthTile() {
        return northTile;
    }

    /**
     * assign to the north tile a know tile.
     * @param northTile a tile
     */
    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    /**
     * returns the tile place to the south
     * @return the south tile, can be null
     */
    public Tile getSouthTile() {
        return southTile;
    }

    /**
     * assign to the south tile a know tile.
     * @param southTile a tile
     */
    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }


    /**
     * Gets the players that are present in this tile
     * @return list of players. can be null
     */
    public List<Player> getPlayers() {
        return players;
    }

    public boolean isPlayerIn(Player p){
        return players.contains(p);
    }

}
