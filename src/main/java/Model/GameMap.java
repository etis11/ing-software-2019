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

    public List<Player> allVisiblePlayers(Player p) {

    }

    public Tile getTile(Player p) {
     //TODO must return something
    }

    public List<Player> allVisiblePlayers(Player p)  {
        //TODO must return something
    }

    public List<Tile> allVisibleTiles(Player p)  {
        //TODO must return something
    }

    public List<Room> visibleRooms(Player p) {
        //TODO must return something
    }
    //room or rooms?! or both?
}

