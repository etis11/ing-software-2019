package model.clientModel;

import model.Player;

import java.util.List;

/**
 * Semplified version of the game for the client. Contains only all the players, the current player,  and   a reference to the map
 */
public class SemplifiedGame {

    private List<SemplifiedPlayer> players;
    private SemplifiedPlayer currentPlayer;
    private SemplifiedMap map;
    private String message;

    public SemplifiedGame(){
        map = new SemplifiedMap();
    }

    public List<SemplifiedPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SemplifiedPlayer> players) {
        this.players = players;
    }

    public SemplifiedPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public SemplifiedPlayer getPlayerByName(String name) {
        for (SemplifiedPlayer p : players) {
            if (p.getName().equals(name))
                return p;
        }
        throw new IllegalArgumentException("No player found with this name");
    }

    public SemplifiedMap getMap() {
        return map;
    }

    public void setMap(SemplifiedMap map) {
        this.map = map;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
