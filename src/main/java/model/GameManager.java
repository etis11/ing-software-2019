package model;

import java.util.LinkedList;
import java.util.List;

public class GameManager implements StartGameEventObservable {

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

    private List<StartGameEventObserver> startGameObservers;

    /**
     * creates a new lobby. The Match is null, since will be created though a function
     */
    public GameManager() {
        this.lobby = new Lobby();
        started = false;
        startGameObservers = new LinkedList<>();
    }

    public Match getMatch() {
        return match;
    }

    public Lobby getLobby() {
        return lobby;
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

    /**
     * given a map name, returns the relative json path
     * @return the path of the json
     */
    private String mapToPath(){
        return null;
    }


    private void notifyAllObservers(){
        for(StartGameEventObserver s: startGameObservers)
            s.notifyStartedGame(getMatch());
    }
    /****************************** StartGameEventObservable Implementation *****************************************/
    @Override
    public void attach(StartGameEventObserver ob) {
        startGameObservers.add(ob);
    }
}
