package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.JsonFileReader;
import jsonparser.PowerUpDeserializer;
import jsonparser.WeaponCardDeserializer;
import view.LobbyListener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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
    private String mapName = "piccola";

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

        checkUser();

        for (User u: users){
            players.add(u.getPlayer());
        }

        String mapPath = getMapFromName(mapName);
        GameMap map = GameMap.loadMap(mapPath);
        match = new Match(players, numOfSkulls, map);
        initWeapon(match);
        try {
            initAmmo(match);
            initPowerUp(match);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        match.newRound();
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
        for(CreationGameObserver s: startGameObservers){
            s.notifyStartedGame(getMatch());
        }
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

    private synchronized void checkUser(){
        List<String> names = new ArrayList<>(5);
        List<String> playerNames = lobby.getNameToken();
        names.add("violetta");
        names.add("sprog");
        names.add("banshee");
        names.add("dozer");
        names.add("distruttore");
        for (String str: playerNames){
            names.remove(str);
        }
        for (User u :lobby.getUsers()){
            if (u.getPlayer().getName().equals("")){
                u.getPlayer().setName(names.remove(0));
            }
        }
    }

    private synchronized void initWeapon(Match match){
        JsonFileReader jsonFileReader = new JsonFileReader();
        String cards = jsonFileReader.loadWeaponCards("cards/weaponCards.json");
        WeaponCardDeserializer weaponCardDeserializer = new WeaponCardDeserializer(match);
        List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        match.createWeaponDeck(weaponCards);
        match.getWeaponDeck().shuffle();
        for (int i = 0; i<3;i++){
            match.getMap().getRegenPoint("red").putWeaponCard(match.getWeaponDeck().draw());
            match.getMap().getRegenPoint("blue").putWeaponCard(match.getWeaponDeck().draw());
            match.getMap().getRegenPoint("yellow").putWeaponCard(match.getWeaponDeck().draw());
        }
    }

    private synchronized void initAmmo(Match match) throws FileNotFoundException {
        Gson gson = new Gson();
        AmmoCard[] newCard = gson.fromJson(new FileReader("cards/ammoCards.json"), AmmoCard[].class);
        List<AmmoCard> ammoCards = new ArrayList<>(Arrays.asList(newCard));
        match.createAmmoDeck(ammoCards);
        match.createAmmoSlushDeck();
        match.getAmmoDeck().shuffle();
        for (Tile t : match.getMap().mapAsList()){
            if (t.canContainAmmo()) {
                t.putAmmoCard(match.getAmmoDeck().draw());
            }
        }
    }

    private synchronized void initPowerUp(Match match) throws FileNotFoundException {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(PowerUpCard.class, new PowerUpDeserializer());
        Gson gson = gb.create();
        PowerUpCard[] newCard = gson.fromJson(new FileReader("cards/powerUpCards.json"), PowerUpCard[].class);
        List<PowerUpCard> powerUpCards = new ArrayList<>(Arrays.asList(newCard));
        match.createPowerUpDeck(powerUpCards);
        match.createPowerUpSlushDeck();
        match.getPowerUpDeck().shuffle();

    }

    /****************************** CreationGameObservable Implementation *****************************************/
    @Override
    public  synchronized void attach(CreationGameObserver ob) {
        startGameObservers.add(ob);
        ob.notifyCreatedLobby(lobby);
    }
}
