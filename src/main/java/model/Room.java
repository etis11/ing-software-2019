package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains reference to the tiles that are in the room.
 */
public class Room {

    /**
     * List of tiles
     */
    private List<Tile> tiles;

    /**
     * Creates a room with no tiles in it
     */
    public Room() {
        tiles = new ArrayList<>();
    }

    /**
     * returns the tiles in the room
     *
     * @return a list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * adds a tile to the current room and assigns the given room to the tile
     *
     * @param toAdd the tile that has to be added to the room
     */
    public void addTile(Tile toAdd) {
        if (toAdd == null) throw new IllegalArgumentException("The tile passed is null");
        tiles.add(toAdd);
        toAdd.setRoom(this);
    }

    /**
     * returns all the players in the room
     *
     * @return players that are in the same Room
     */
    public List<Player> getPlayersInRoom() {

        List<Player> players = new ArrayList<>();

        for (Tile tile : this.getTiles()) {
            players.addAll(tile.getPlayers());
        }

        return players;
    }
}