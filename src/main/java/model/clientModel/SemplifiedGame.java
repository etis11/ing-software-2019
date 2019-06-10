package model.clientModel;

import model.Player;

import java.util.List;

public class SemplifiedGame {

    private List<SemplifiedPlayer> players;
    private SemplifiedPlayer currentPlayer;
    private SemplifiedMap map;
    private String message;

    public List<SemplifiedPlayer> getPlayers() {
        return players;
    }

    public SemplifiedPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public SemplifiedPlayer getPlayerByName(String name){
        for(SemplifiedPlayer p: players){
            if (p.getName().equals(name))
                return p;
        }
        throw new IllegalArgumentException("No player found with this name");
    }

    public SemplifiedMap getMap() {
        return map;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
