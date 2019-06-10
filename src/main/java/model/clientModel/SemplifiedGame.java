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

    public SemplifiedMap getMap() {
        return map;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
