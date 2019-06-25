package controller;


import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedTile;

import java.util.LinkedList;
import java.util.List;

public class CommandResponseClient extends AbstractCommandResponse {
    private final List<SemplifiedPlayer> allPlayers;
    private final List<SemplifiedPlayer> changedPlayers;
    private final List<SemplifiedTile> allTiles;
    private SemplifiedPlayer currentPlayer;
    private List<List<SemplifiedBloodToken>> deathTrack;

    public CommandResponseClient(List<SemplifiedPlayer> allPlayers, List<SemplifiedTile> allTiles){
        super();
        this.allPlayers = allPlayers;
        this.allTiles = allTiles;
        changedPlayers = new LinkedList<>();
    }

    public void resetChangedPlayers(){
        changedPlayers.clear();
    }

    public void addChangedPlayer(SemplifiedPlayer p){
        changedPlayers.remove(p);
        changedPlayers.add(p);
    }

    public List<SemplifiedPlayer> getAllPlayers() {
        return allPlayers;
    }

    public List<SemplifiedPlayer> getChangedPlayers() {
        return changedPlayers;
    }

    public List<SemplifiedTile> getAllTiles() {
        return allTiles;
    }

    public SemplifiedPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public List<List<SemplifiedBloodToken>> getDeathTrack() {
        return deathTrack;
    }
}
