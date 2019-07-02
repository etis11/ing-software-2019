package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.PowerUpDeserializer;
import jsonparser.mainArmi;
import view.LobbyListener;

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

    /**
    * identify if the game ends with the final frenzy
    */
    private boolean finalFrenzy = true;

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

    public synchronized void setFinalFrenzy(boolean frenzy){this.finalFrenzy = frenzy;}

    public synchronized boolean getFinalfrenzy(){
        return finalFrenzy;
    }

    public synchronized void createMatch(ChangesMatchObserver jsonCreator){
        List<User> users = lobby.getUsers();
        List<Player> players = new LinkedList<>();

        checkUser();

        for (User u: users){
            players.add(u.getPlayer());
        }

        String mapPath = getMapFromName(mapName);
        List<WeaponCard> weaponCards = WeaponCard.getWeaponsFromJson(GameManager.class.getResourceAsStream("/cards/weaponCards.json"), match);
        List<WeaponCard> weaponCards2 = WeaponCard.getWeaponsFromJson(GameManager.class.getResourceAsStream("/cards/weaponCards.json"), match);
        List<WeaponCard> weaponCards3 = WeaponCard.getWeaponsFromJson(GameManager.class.getResourceAsStream("/cards/weaponCards.json"), match);

        String name = "Distruttore";
        WeaponCard armascelta = null;
        for(WeaponCard weaponCard: weaponCards){
            if (weaponCard.getName().equals(name)){
                armascelta = weaponCard;
            }
        }

        WeaponCard armascelta1 = null;
        for(WeaponCard weaponCard: weaponCards){
            if (weaponCard.getName().equals(name)){
                armascelta1 = weaponCard;
            }
        }

        WeaponCard armascelta2 = null;
        for(WeaponCard weaponCard: weaponCards){
            if (weaponCard.getName().equals(name)){
                armascelta2 = weaponCard;
            }
        }

        try{
            players.get(0).pickUpWeapon(armascelta);
            players.get(1).pickUpWeapon(armascelta1);
            players.get(2).pickUpWeapon(armascelta2);

        }
        catch (Exception e){

        }

        GameMap map = GameMap.loadMap(GameManager.class.getResourceAsStream(mapPath));
        //TODO aggiungere final frenzy
        match = new Match(players, numOfSkulls, map);
        match.attach(jsonCreator);
        initWeapon(match);
        initAmmo(match);
        initPowerUp(match);
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
                return "/maps/map1.json";
            case "media":
                return "/maps/map4.json";
            case "grande":
                return "/maps/map2.json";
            case "estrema":
                return "/maps/map3.json";
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
        List<WeaponCard> weaponCards = WeaponCard.getWeaponsFromJson(mainArmi.class.getResourceAsStream("/cards/weaponCards.json") , match);
        match.createWeaponDeck(weaponCards);
        match.getWeaponDeck().shuffle();
        for (int i = 0; i<3;i++){
            match.getMap().getRegenPoint("red").putWeaponCard(match.getWeaponDeck().draw());
            match.getMap().getRegenPoint("blue").putWeaponCard(match.getWeaponDeck().draw());
            match.getMap().getRegenPoint("yellow").putWeaponCard(match.getWeaponDeck().draw());
        }
    }

    private synchronized void initAmmo(Match match){
        Gson gson = new Gson();
        AmmoCard[] newCard = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/cards/ammoCards.json")), AmmoCard[].class);
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

    private synchronized void initPowerUp(Match match) {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(PowerUpCard.class, new PowerUpDeserializer());
        Gson gson = gb.create();
        InputStream inputStream = GameManager.class.getResourceAsStream("/cards/powerUpCards.json");
        PowerUpCard[] newCard = gson.fromJson(new InputStreamReader(inputStream), PowerUpCard[].class);
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
