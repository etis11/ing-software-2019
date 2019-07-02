package controller;
import model.BloodToken;
import model.Player;
import model.Tile;

import java.util.LinkedList;
import java.util.List;

public class CommandResponse  extends AbstractCommandResponse{
    private  List<Player> allPlayers;
    private  List<Player> changedPlayers;
    private  List<Tile> allTiles;
    private Player currentPlayer;
    private List<List<BloodToken>> deathTrack;

    public CommandResponse(){
        super();
        changedPlayers = new LinkedList<>();
    }

    public void resetChangedPlayers(){
        changedPlayers.clear();
    }

    public void addChangedPlayer(Player p){
        changedPlayers.remove(p);
        changedPlayers.add(p);
        System.out.println("in commandResponse : " + changedPlayers);
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

    public void setCurrentPlayer(Player p){
        currentPlayer = p;
    }

    public void setDeathTrack(List<List<BloodToken>> deathTrack) {
        this.deathTrack = deathTrack;
    }
}
