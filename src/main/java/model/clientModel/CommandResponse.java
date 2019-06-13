package model.clientModel;

import com.google.gson.Gson;
import model.Player;
import model.Tile;

import java.util.LinkedList;
import java.util.List;

public class CommandResponse {
    private String message;
    private boolean errorOccurred;
    private String error;
    private final List<Player> allPlayers;
    private boolean playerChanged;
    private final List<Player> changedPlayers;
    private boolean mapChanged;
    private final List<Tile> allTiles;

    public CommandResponse(List<Player> allPlayers, List<Tile> allTiles){
        this.allPlayers = allPlayers;
        this.allTiles = allTiles;
        changedPlayers = new LinkedList<>();
        mapChanged = false;
    }

    public void resetMessage(){
        message = null;
    }

    public void setMessage(String s){
        message = s;
    }

    public void resetErrorMessage(){
        errorOccurred = false;
        error = null;
    }

    public void setErrorMessage(String s){
        error = s;
        errorOccurred = true;
    }

    public void resetChangedPlayers(){
        changedPlayers.clear();
    }

    public void setMapChanged(boolean value){
        mapChanged = value;
    }

    public void setPlayerChanged(boolean value){
        playerChanged = value;
    }


    public void addChangedPlayer(Player p){
        changedPlayers.remove(p);
        changedPlayers.add(p);
    }

}
