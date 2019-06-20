package controller;
import model.Player;
import model.Tile;

import java.util.LinkedList;
import java.util.List;

public class CommandResponse  extends AbstractCommandResponse{
    private  List<Player> allPlayers;
    private  List<Player> changedPlayers;
    private  List<Tile> allTiles;

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

    public void setAllPlayers(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void setAllTiles(List<Tile> allTiles) {
        this.allTiles = allTiles;
    }

    public List<Tile> getAllTiles() {
        return allTiles;
    }
}
