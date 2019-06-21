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
    private String mapName;

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

    public  synchronized Match getMatch() {
        return match;
    }

    public  synchronized Lobby getLobby() {
        return lobby;
    }

    public  synchronized  void setNumOfSkulls(int numOfSkulls) {
        this.numOfSkulls = numOfSkulls;
    }

    public  synchronized void setMapName(String map){
        this.mapName = map;
    }

    public synchronized String getMapName(){
        return mapName;
    }

    public synchronized void createMatch(){
        List<User> users = lobby.getUsers();
        List<Player> players = new LinkedList<>();
        for (User u: users){
            players.add(u.getPlayer());
        }
        //TODO controllo sui nomi

        String mapPath = getMapFromName(mapName);
        GameMap map = GameMap.loadMap(mapPath);
        match = new Match(players, numOfSkulls, map);
    }

    public  synchronized void startMatch(){
        started = true;
        notifyStartGameObservers();

    }

    /**
     *
     * @return false if the match is not created. Otherwise, can return false if the match has been created but not
     *               started.
     */
    public  synchronized boolean isMatchStarted() {
            if (match == null) return  false;
            return started;
    }

    public  synchronized void attachObserverToPlayers(ChangesObserver playerObserver){
        List<User> users= lobby.getUsers();
        for(User u: users){
            u.getPlayer().attach(playerObserver);
        }
    }

    public  synchronized void attachObserverToTiles(ChangesObserver tileObserver){
        if (match == null) throw  new NoSuchElementException("The match has not been created");
        GameMap map = match.getMap();
        for(Tile t: map.mapAsList()){
            t.attach(tileObserver);
        }
    }

    public  synchronized void attachObserverToLobby(LobbyListener lobbyListener){
        lobby.attach(lobbyListener);
    }

    private synchronized void notifyStartGameObservers(){
        for(CreationGameObserver s: startGameObservers)
            s.notifyStartedGame(getMatch());
    }

    private  synchronized String getMapFromName(String name){
        // piccola media grande estrema
        switch (name){
            case "piccola":
                return this.getClass().getResource("/maps/map1.json").getPath();
            case "media":
                return this.getClass().getResource("/maps/map4.json").getPath();
            case "grande":
                return this.getClass().getResource("/maps/map2.json").getPath();
            case "estrema":
                return this.getClass().getResource("/maps/map3.json").getPath();
            default: throw new RuntimeException("No map associated to " + name);
        }
    }
    /****************************** CreationGameObservable Implementation *****************************************/
    @Override
    public  synchronized void attach(CreationGameObserver ob) {
        startGameObservers.add(ob);
        ob.notifyCreatedLobby(lobby);
    }
}
