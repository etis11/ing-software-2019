package Model;

import java.util.ArrayList;
import java.util.List;

public class Room {


    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private GameMap gameMap;
    private List<Tile> tiles;

    public List<Tile> getTiles() {

        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Player> getPlayersInRoom() {

        List<Player> players = new ArrayList<>();

        for(Tile tile : this.getTiles()){
            players.addAll(tile.getPlayers());
        }

        return players;
    }
}