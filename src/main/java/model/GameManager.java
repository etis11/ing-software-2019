package model;

public class GameManager {

    private final Match match;

    private final Lobby lobby;

    public GameManager() {
        this.match = new Match();
        this.lobby = new Lobby();
    }
    public GameManager(Match m){
        this.match = m;
        this.lobby = new Lobby();
    }

    public Match getMatch() {
        return match;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
