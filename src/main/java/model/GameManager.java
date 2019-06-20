package model;

import view.LobbyListener;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class GameManager implements CreationGameObservable {

    /**
     * the match where the players are playing / will be playing. Can be null
     */
    private Match match;

    /**
     * Tells if the match is currently playing
     */
    private boolean started;

    /**
     * Name of the map
     */
    private String map;

    /**
     * number of kills that will be set in the math
     */
    private int numOfSkulls;

    /*
    where all the user are gathered
     */
    private final Lobby lobby;

    private List<CreationGameObserver> startGameObservers;

    /**
     * creates a new lobby. The Match is null, since will be created though a function
     */
    public GameManager() {
        this.lobby = new Lobby();
        started = false;
        startGameObservers = new LinkedList<>();
        numOfSkulls = 8;
    }

    public Match getMatch() {
        return match;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setNumOfSkulls(int numOfSkulls) {
        this.numOfSkulls = numOfSkulls;
    }

    public void setMap(String map){
        this.map = map;
    }

    public void createMatch(){
        //gets all the players from the users and puts them in a list
        //creates a map from the json path
        //creates a match with these parameters

    }

    public void startMatch(){
        started = true;
        //codice che notifica tutti gli observer che il match è stato creato
    }

    /**
     *
     * @return false if the match is not created. Otherwise, can return false if the match has been created but not
     *               started.
     */
    public boolean isMatchStarted() {
        if (match == null) return  false;
        return started;
    }

    public void attachObserverToPlayers(ChangesObserver playerObserver){
        List<User> users= lobby.getUsers();
        for(User u: users){
            u.getPlayer().attach(playerObserver);
        }
    }

    public void attachObserverToTiles(ChangesObserver tileObserver){
        if (match == null) throw  new NoSuchElementException("The match has not been created");
        GameMap map = match.getMap();
        for(Tile t: map.mapAsList()){
            t.attach(tileObserver);
        }
    }

    public void attachObserverToLobby(LobbyListener lobbyListener){
        lobby.attach(lobbyListener);
    }

    /**
     * given a map name, returns the relative json path
     * @return the path of the json
     */
    private String mapToPath(){
        return null;
    }


    private void notifyAllObservers(){
        for(CreationGameObserver s: startGameObservers)
            s.notifyStartedGame(getMatch());
    }
    /****************************** CreationGameObservable Implementation *****************************************/
    @Override
    public void attach(CreationGameObserver ob) {
        startGameObservers.add(ob);
        ob.notifyCreatedLobby(lobby);
    }
}
