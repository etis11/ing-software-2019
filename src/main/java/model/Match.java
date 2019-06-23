package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * This class handles nearly everything in the game. Starting from the GameMap,Decks,Players and so on.
 * THe Match contains every Player and handles all moves done by players in the gamemap each turn.
 */
public class Match implements ChangesMatchObservable{

    /**
     * the number of player playing the game
     */
    private int playerNumber;
    /**
     * Integer used to count number of lifes till GameOver.An integer that is between 5(included) and 8(included)
     */
    private int skulls;

    /**
     * This is the map of the game made as a graph where Tiles are connected between them.Moreover there are 4 ways
     * of combining the Map based on a selection made in the beginning of the game
     */
    private GameMap map;

    /**
     * List of players playing when game starts. Such list is longer no lesser then three and no bigger then five.
     * Each player has  his own playerboard and cards
     */
    private List<Player> players = new LinkedList<>();
    /**
     * the index of the player that is currently playing
     */
    private int currentPlayer = 0;

    /**
     * One of the three Decks used during gameplay.It contains all PowerUp cards (playable or not during game)
     */
    private Deck<PowerUpCard> powerUpDeck;

    /**
     * THe second deck used for WeaponCards. It contains 21 Weapon Cards,each with different effect from the others.
     */
    private Deck<WeaponCard> weaponDeck;

    /**
     * THe third and last deck of Cards. It contains all Ammocards (36). Moreover, in case these cards end up
     * being used all, the deck gets shuffled and ready to be used again.
     */
    private Deck<AmmoCard> ammoDeck;

    /**
     * ammoSlushPile contains all AmmoCards used
     */
    private Deck<AmmoCard> ammoSlushPile;

    /**
     * powerUpSlushPile contains all AmmoCards used
     */
    private Deck<AmmoCard> powerUpSlushPile;
    /**
     * skulls provided by owners
     */
    private List<BloodToken> skullsList;
    /**
     * a list of observer interested in the change of the map
     */
    private List<ChangesMatchObserver> matchObservers;


    public Match() {
        this.playerNumber = 5;
    }

    public Match(int skulls, String mapPath, List<Player> players){
        playerNumber = players.size();
        this.skulls = skulls;
        this.players = players;
        this.map = GameMap.loadMap(mapPath);
        currentPlayer = -1;
        matchObservers = new LinkedList<>();
    }

    public Match(int playerNumber, int skulls, GameMap map) {
        this.playerNumber = playerNumber;
        this.skulls = skulls;
        this.map = map;
        this.players = new ArrayList<>(playerNumber);
        currentPlayer = -1;
        matchObservers = new LinkedList<>();
    }

    public Match(List<Player> players, int numOfSkulls, GameMap map){
        this.playerNumber = players.size();
        this.skulls = numOfSkulls;
        this.map = map;
        this.players = players;
        currentPlayer = -1;
        matchObservers = new LinkedList<>();
    }

    /**
     * creates the weaponDeck providing the cards
     * @param cards list of weapon cards
     */
    public synchronized void createWeaponDeck(List<WeaponCard> cards){
        this.weaponDeck = new Deck<>(cards);
    }

    /**
     * creates the ammoDeck providing the cards
     * @param cards list of ammo cards
     */
    public synchronized void createAmmoDeck(List<AmmoCard> cards){
        this.ammoDeck = new Deck<>(cards);
    }

    /**
     * creates the ammoSlushDeck
     */
    public synchronized void createAmmoSlushDeck(){
        this.ammoSlushPile = new Deck<>();
    }

    /**
     * creates the powerUpDeck providing the cards
     * @param cards list of powerUp cards
     */
    public synchronized void createPowerUpDeck(List<PowerUpCard> cards){
        this.powerUpDeck = new Deck<>(cards);
    }

    /**
     * creates the powerUpSlushDeck
     */
    public synchronized void createPowerUpSlushDeck(){
        this.powerUpSlushPile = new Deck<>();
    }

    /**
     * Method that returns number of skulls left during gameplay.
     */
    public synchronized int getSkulls() {
        return skulls;
    }

    /**
     * Method called before gameplay. Used to store the number of skulls before gameplay so that each player
     * before game starting, gets to know how long the game might last and so on.
     */
    public synchronized void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    /**
     * Method that returns number of player chosen for the match
     */
    public synchronized int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Method called before gameplay. Used to store the number of players before gameplay so that each player
     * before game starting, gets to know how many player plays.
     */
    public synchronized void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * This method return the player that is currently actively playing
     *
     * @return the current player
     */
    public synchronized Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * As stated above, this method returns the type of map that is going to be used during gameplay. The choice
     * has been made before with its setter
     */
    public  synchronized GameMap getMap() {
        return map;
    }

    /**
     * Chooses what kind of map to be used during the next game. There are 4 different combinatios.Once chosen,
     * it mustn't change during gameplay
     */
    public synchronized void setMap(GameMap map) {
        this.map = map;
    }


    /**
     * This method returns a copy of the list of players that are going to/will play when game starts
     */
    public synchronized List<Player> getPlayers() {
        return new LinkedList<>(this.players);
    }

    /**
     * Method used to set list of players before game starting.
     */
    public synchronized void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Method used to return the deck of PowerUp cards
     */
    public synchronized Deck<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    /**
     * Method used to return the deck of Weapon cards
     */
    public synchronized Deck<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    /**
     * Method used to return the deck of Ammo cards
     */
    public synchronized Deck<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    /**
     * Method used to return the slush pile of Ammo cards
     */
    public synchronized Deck<AmmoCard> getAmmoSlushPile() {
        return ammoSlushPile;
    }

    /**
     * replace item consumed on the board
     */
    public synchronized void replaceCards(){
        //TODO devo notificare?
        for(Tile t: map.mapAsList()){
            if (t.canContainWeapons() && t.getWeapons().size()<3){
                while(t.getWeapons().size()<3){
                    t.putWeaponCard(weaponDeck.draw());
                }
            }
            else if (t.canContainAmmo() && !t.isPresentAmmoCard()){
                if (ammoDeck.isEmpty()){
                    ammoSlushToDeck();
                }
                t.putAmmoCard(ammoDeck.draw());
            }
        }
    }

    /**
     * reset the ammoDeck from his slush pile
     */
    private void ammoSlushToDeck(){
        while (!ammoSlushPile.isEmpty()){
            ammoDeck.addCard(ammoSlushPile.draw());
        }
        ammoDeck.shuffle();
    }

    /**
     * set the new current player
     */
    private void nextPlayer(){
        currentPlayer++;
        notifyAllObserversCurrentPlayer();
    }

    /**
     * check if the current player has to spawn
     */
    private void checkSpawn(){
        Player current = getCurrentPlayer();
        if(current.getTile() == null && current.getState().getName().equals("EndTurn")){
            current.pickUpPowerUp(powerUpDeck.draw());
            current.pickUpPowerUp(powerUpDeck.draw());
        }else if(current.getState().getName().equals("Dead") || current.getState().getName().equals("Overkilled")){
            PowerUpCard p = powerUpDeck.draw();
            current.pickUpPowerUp(p);
        }
        else{
            current.getState().nextState(actionParser(current.getPlayerBoard()),current);
            return;
        }
        //comando di spawn e scarto
    }

    private boolean checkDead(){
        for(Player p:players){
            String state =p.getState().getName();
            if(state.equals("Dead") || state.equals("Overkilled")){
                calculatePoints(p);
                return true;
            }
        }
        return false;
    }

    public void verifyDead(){
        if(checkDead()){
            skulls--;
        }
        if(skulls<1){
            //TODO end game o final frenzy
        }
    }

    private void calculatePoints(Player p){
        List<BloodToken> damage = p.getPlayerBoard().getDamageTokens();
        int[] numDamagePerPlayer = new int[playerNumber];
        for(int i = 0; i<playerNumber;i++){
            numDamagePerPlayer[i] = 0;
        }
        for (BloodToken b:damage){
            numDamagePerPlayer[players.indexOf(b.getOwner())]++;
        }
        Arrays.sort(numDamagePerPlayer);
        //TODO
        //attribuisco punti
        //attribuisco marchi
        //se overkilled marchio
        //atrtibuisco punto aggiuntivo prima uccizione
        //attribuisco skull
    }

    /**
     * starts a new round
     */
    public void newRound(){
        nextPlayer();
        checkSpawn();
    }

    /**
     * parses the action that the player can do depending on his damage
     * @param playerBoard playerBoard of current player
     * @return string of the parsed state to go
     */
    private String actionParser(PlayerBoard playerBoard){
        int damage = playerBoard.getNumDamagePoints();
        if(damage<3){
            return "NormalAction";
        }
        else if (damage<6){
            return "MoreAction";
        }
        else{
            return "MostAction";
        }
    }

    /********************** changes observable **************************/
    @Override
    public void attach(ChangesMatchObserver observer) {
        matchObservers.add(observer);
    }

    //TODO va bene per json?
    private void notifyAllObserversCurrentPlayer(){
        for(ChangesMatchObserver ob : matchObservers) ob.notifyCurrentPlayerChange(players.get(currentPlayer));
    }
    private void notifyAllObserversCurrentSkull(){
        for(ChangesMatchObserver ob : matchObservers) ob.notifySkullChange(skullsList);
    }
}
