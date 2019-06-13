package controller;
import model.Player;
import model.Tile;

import java.util.LinkedList;
import java.util.List;

public class CommandResponse  extends AbstractCommandResponse{
    private final List<Player> allPlayers;
    private final List<Player> changedPlayers;
    private final List<Tile> allTiles;

    public CommandResponse(List<Player> allPlayers, List<Tile> allTiles){
        super();
        this.allPlayers = allPlayers;
        this.allTiles = allTiles;
        changedPlayers = new LinkedList<>();
    }

    public void resetChangedPlayers(){
        changedPlayers.clear();
    }

    public void addChangedPlayer(Player p){
        changedPlayers.remove(p);
        changedPlayers.add(p);
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public List<Player> getChangedPlayers() {
        return changedPlayers;
    }

    public List<Tile> getAllTiles() {
        return allTiles;
    }
}
