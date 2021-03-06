package model;


import controller.LOGGER;
import exceptions.IllegalHavingException;
import exceptions.InsufficientAmmoException;
import exceptions.NotValidMovesException;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.stream.Collectors;


public class Player implements ChangesObservable{
    /**
     * max number of weapons that can be in the player's hand
     */
    private static final int MAX_WEAPON_CARDS = 3;
    /**
     * max number of power ups that can be in the player's hand
     */
    private static final int MAX_POWERUP_CARDS = 3;
    /**
     * name of the player
     */
    private String name;
    /**
     * current points of the player
     */
    private int points;
    /**
     * current state of the player
     */
    private State state;
    /**
     * remainingStep are moves remaining for playing turn
     */
    private int remainingMoves;
    /**
     * list of weapons owned by the player
     */
    private List<WeaponCard> weapons;
    /**
     * list of power ups in the hand of the player
     */
    private List<PowerUpCard> powerUps;
    /**
     * tile where the player token is located
     */
    private Tile tile;

    /**
     * tile where the player was before
     */
    private Tile oldTile;
    /**
     * board that contains all the "public" information of the player. this informations can be obtained by the others player too.
     */
    private PlayerBoard playerBoard;
    /**
     * oldState is the previous state of the player
     */
    private State oldState;
    /**
     * a list of observer interested in the change of the map
     */
    private List<ChangesObserver> playerObservers;

    /**
     * boolean set as true by default that tells if a weapon has alaredy been paid or not
     */
    private boolean payWewapon = true;

    /**
     * sets State as endturnState
     */
    private final State endTurnState;

    //True if there is a connected user that is playing with this player
    private AtomicBoolean active = new AtomicBoolean(true);

    /**
     * A player without name is created. His hand is empty and the state is EndTurn (he cant do anything). Since no map or tile
     * has been created, this player has not been positioned on any tile
     */
    public Player() {
        name = "";
        points = 0;
        state = null;
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
        oldState = null;
        oldTile =null;
        playerObservers = new LinkedList<>();
        endTurnState = null;
    }

    /**
     * A player with a name is created. His hand is empty and the state is EndTurn (he cant do anything). Since no map or tile
     * has been created, this player has not been positioned on any tile
     *
     * @param name name of the player
     */
    public Player(String name) {
        this.name = name;
        points = 0;
        state = null;
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
        oldState = null;
        oldTile = null;
        playerObservers = new LinkedList<>();
        endTurnState = null;
    }

    public Player(String name, State endTurnState) {
        this.name = name;
        points = 0;
        state = endTurnState;
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
        oldState = null;
        oldTile =null;
        playerObservers = new LinkedList<>();
        this.endTurnState = endTurnState;
    }

    /**
     * This method returns the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    /**
     * This method returns the current points of the player
     *
     * @return the current points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * Meyhod that returns the tile where the player was before taking action
     * @return older position of player
     */
    public Tile getOldTile() {
        return oldTile;
    }

    /**
     * Sets old tile position  of player
     * @param oldTile is the tile where there player was before
     */
    public void setOldTile(Tile oldTile) {
        this.oldTile = oldTile;
    }

    /**
     * This method returns the current state of the player
     *
     * @return the current state of the player
     */
    public State getState() {
        return state;
    }

    /**
     * This method change the state of the player
     *
     * @param s new state where the player should be
     */
    public void setState(State s) {
        state = s;
    }

    /**
     * Change state to endState
     */
    public void goToEndState(){
        state = endTurnState;
    }

    /**
     * returns the number of remaining moves for playing turn
     *
     * @return the number of remaining moves for playing turn
     */
    public int getRemainingMoves() {
        return remainingMoves;
    }

    /**
     * sets the number of remaining moves for playing turn
     *
     * @param remainingMoves are the new number of remaining moves for playing turn
     */
    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    /**
     * decrements number of moves after an action has chosen
     */
    public void decrementMoves() {
        this.remainingMoves--;
    }

    /**
     * Check if the player is active or not
     * @return it's situation
     */
    public boolean isActive() {
        return active.get();
    }

    /**
     * Sets if player is active or not
     * @param active returns a boolean
     */
    public void setActive(boolean active) {
        this.active.set(active);
    }

    /**
     * This method returns a copy of the weaponsCards in the player's hand.
     *
     * @return the list of all weapons in the hand of the player
     */
    public List<WeaponCard> getWeapons() {
        return this.weapons;
      //  return weapons.stream().map(WeaponCard::new).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * returns a copy of all the empty weapons
     * @return
     */
    public List<WeaponCard> getEmptyWeapons(){
        List<WeaponCard> emptyWeapons = new LinkedList<>();
        for(WeaponCard w: getWeapons()){
            if(!w.isLoaded())
                emptyWeapons.add(new WeaponCard(w));
        }
        return emptyWeapons;
    }

    /**
     * This method return the number of the weapons in the player's hand
     *
     * @return the number of weapons in the player's hand
     */
    public int getNumWeapons() {
        return weapons.size();
    }

    /**
     * This method return a copy of the powerUps in te player's hand.
     *
     * @return the list of all power ups in the player's hand
     */
    public List<PowerUpCard> getPowerUps() {
        return powerUps.stream().map(PowerUpCard::new).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * This method returns the number of power ups in the player's hand
     *
     * @return the number of power ups in the player's hand
     */
    public int getNumPowerUps() {
        return powerUps.size();
    }

    /**
     * Method that picks up a powerup
     * @param p is the powerup set to be picked up
     */
    public void pickUpPowerUp(PowerUpCard p) {
        powerUps.add(p);
        if (getNumPowerUps()>3){
            //TODO gestione scarto
            throw  new RuntimeException("da implementare per bene");
        }
        notifyAllObservers();
    }

    /**
     * This method return the current location of the player
     *
     * @return returns the tile where the player is located
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * THis is the constructor of tile,needed later on other classes to grab players or visible tiles.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * This method returns the playerBoard, where all his public information are stored
     *
     * @return a reference of the player's playerBoard
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * tells which was the player previous state
     *
     * @return player previous state
     */
    public State getOldState() {
        return oldState;
    }

    /**
     * allows to set the player previous state
     *
     * @param oldState state to be set
     */
    public void setOldState(State oldState) {
        this.oldState = oldState;
    }

    /**
     * This method updates the player's score
     *
     * @param points points that have to be added to the current score
     */
    public void addPoints(int points) {
        this.points += points;
        notifyAllObservers();
    }

    /**
     * Add the weapon to the player hand if he has less then 3 weapons. If the player has more than three weapons, this
     * method throws an exception
     *
     * @param w weapon that has to be picked
     * @throws IllegalHavingException if the player has already 4 weapons
     */
    public void pickUpWeapon(WeaponCard w) throws IllegalHavingException, InsufficientAmmoException {
        if (w == null) throw new IllegalArgumentException("Passato un valore nullo");
        if (weapons.size() > 2) {
            weapons.add(tile.pickUpWeaponCard(w));
            throw new IllegalHavingException("Il giocatore ha 4 armi in mano");
        } else{
            if(w.getReloadCost().size()>1 && payWewapon){
                if(canPay(w.getReloadCost().subList(1, w.getReloadCost().size()))){
                    playerBoard.getLoader().ammoToPool(w.getBluePickCost(), w.getRedPickCost(), w.getYellowPickCost());
                }
                else{
                    throw new InsufficientAmmoException("not sufficient ammo");
                }
            }
            weapons.add(tile.pickUpWeaponCard(w));
        }

        notifyAllObservers();
    }

    /**
     * Throws away the power up. The power up will be no longer in the hand of the player.
     *
     * @param p copy of the power up that has to be thrown away
     * @return the power up that has to be thrown away
     * @throws Exception if the power up is not in the hand of the player
     */
    public PowerUpCard throwPowerUp(PowerUpCard p) throws Exception {
        PowerUpCard retCard = null;
        if (!powerUps.contains(p)) {
            throw new Exception("non si pu?? throware il power up perch?? non ?? in mano al giocatore");
        } else {
            for (int i = 0; i < powerUps.size(); i++) {
                if (powerUps.get(i).equals(p)) {
                    retCard = powerUps.remove(i);
                }
            }
        }
        notifyAllObservers();
        return retCard;
    }

    /**
     * useAmmoCard reload the loader and if necessary or possible draw a PowerUp
     *
     * @param card AmmoCard to use
     * @param deck PowerUp deck of the match
     */
    public void useAmmoCard(AmmoCard card, Deck<PowerUpCard> deck) {
        //put ammo in the loader
        getPlayerBoard().getLoader().askReload(card.getNumBlue(), card.getNumRed(), card.getNumYellow());
//        verify if the card allow to draw a PowerUp and if the player has less than 3 PowerUp
        if (card.isDrawPowerUp() && getNumPowerUps() < MAX_POWERUP_CARDS) {
            powerUps.add(deck.draw());
        }
        //todo gestione 4 power up
        notifyAllObservers();
    }

    /**
     * moves a player of a given path if it is possible
     *
     * @param movement Movement containing the list of moves to do
     */
    public void move(Movement movement) throws NotValidMovesException {
        Tile newTile = explorePath(movement);
        try {
            tile.removePlayer(this);
        } catch (Exception e) {
            LOGGER.LOGGER.log(Level.WARNING,Arrays.toString(e.getStackTrace()));
        }
        newTile.addPlayer(this);
    }

    /**
     * Method needed to check if the player can move in a certain way or not
     * @param movement is the movement the player with make
     * @return the new tile position
     * @throws NotValidMovesException
     */
    private Tile explorePath(Movement movement) throws NotValidMovesException {
        Tile newTile = getTile();
        if (movement == null) throw new IllegalArgumentException("null movement");
        for (String str : movement.getSteps()) {
            switch (str) {
                case "up":
                    if (newTile.getNorthTile() != null) {
                        newTile = newTile.getNorthTile();
                    } else {
                        throw new NotValidMovesException("not valid moves");
                    }
                    break;
                case "right":
                    if (newTile.getEastTile() != null) {
                        newTile = newTile.getEastTile();
                    } else {
                        throw new NotValidMovesException("not valid moves");
                    }
                    break;
                case "down":
                    if (newTile.getSouthTile() != null) {
                        newTile = newTile.getSouthTile();
                    } else {
                        throw new NotValidMovesException("not valid moves");
                    }
                    break;
                case "left":
                    if (newTile.getWestTile() != null) {
                        newTile = newTile.getWestTile();
                    } else {
                        throw new NotValidMovesException("not valid moves");
                    }
                    break;
                default:
                    throw new NotValidMovesException("not valid moves");
            }
        }
        return newTile;
    }

    /**
     * Checks if the move is valid or not
     * @param movement is the movement of the player
     * @return new tile
     * @throws NotValidMovesException if the move can't be done
     */
    public boolean checkMove(Movement movement) throws NotValidMovesException {
        Tile newTile = explorePath(movement);
        return newTile.isPresentAmmoCard() || !newTile.getWeapons().isEmpty();
    }

    /**
     * returns name of player weapons
     *
     * @return string of name of weapons
     */
    public String weaponsToString() {
        StringBuilder toReturn = new StringBuilder();
        for (WeaponCard w : weapons) {
            toReturn.append(" ").append(w.getName());
        }
        return toReturn.toString();
    }

    /**
     * returns name of player powerUps
     *
     * @return string of name of powerUps
     */
    public String powerUpToString() {
        StringBuilder toReturn = new StringBuilder();
        for (PowerUpCard p : powerUps) {
            toReturn.append(" ").append(p.getPowerUpType());
        }
        return toReturn.toString();
    }

    /**
     * Method that checks if player has a certain powerup
     * @param type is the type of powerup
     * @param color color of the powerup
     * @return a boolean
     */
    public boolean hasPowerUp(PowerUpType type, Color color){
        for(PowerUpCard pc :getPowerUps()){
            if(pc.getPowerUpType().equals(type) && pc.getColor().equals(color)){
                return true;
            }
        }
        return false;
    }

    /**
     * return the power up in the hand of the player
     * @param type type of the power up
     * @param color color, can be only "red" "blu" "yellow"
     * @return
     */
    public PowerUpCard getPowerUp(PowerUpType type, Color color){
        for(PowerUpCard pc :powerUps){
            if(pc.getPowerUpType().equals(type) && pc.getColor().equals(color)){
                return pc;
            }
        }
        throw  new RuntimeException("missed control if the power up is present");
    }

    /**
     * Checks if player can pay for certain weaponCard or not
     * @param cost is the list of costs of the weaponCard
     * @return a boolean
     */
    public boolean canPay(List<String> cost){
        return playerBoard.getLoader().canPay(cost);
    }

    /**
     * Checks if ou can pay all optional effects also
     * @param opt list of optional effects
     * @return a boolean
     */
    public boolean canPayAll(List<OptionalEffect> opt){
        return playerBoard.getLoader().canPayAll(opt);
    }

    /**
     * payes for all the optionals
     * @param opt
     */
    public void payOpt(List<OptionalEffect> opt){
        for (OptionalEffect opts: opt) {
            playerBoard.getLoader().pay(opts.getCost());
        }
    }

    /**
     * pays only one optional
     * @param opt
     */
    public void payOpt(OptionalEffect opt){
        playerBoard.getLoader().pay(opt.getCost());
    }

    /**
     * Method that pays for an Effect to be used
     * @param effect is the effect of the card chosen by the user to be used
     */
    public void pay(Effect effect){
        playerBoard.getLoader().pay(effect.getCost());
    }

    public void payTargetScope(List<String> cost){
        playerBoard.getLoader().pay(cost);
    }

    /**
     * CHhecks whether or not the weaponcard has a name or not
     * @param name is the name of the weaponCard
     * @return the weapon card or else NULL
     */
    public WeaponCard hasWeapon(String name){
        for (WeaponCard wc: weapons){
            if(wc.getName().equals(name)){
                return wc;
            }
        }
        return null;
    }

    /**
     * discard a weapon card
     * @param wc
     */
    public void throwWeaponCard(WeaponCard wc){
        if(wc == null) throw new IllegalArgumentException("passed null weapon card");
        getTile().putWeaponCard(wc);
        weapons.remove(wc);
        notifyAllObservers();
    }

    /**
     * Method that returns name of the player and the tile
     * @return
     */
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", tile=" + tile +
                '}';
    }

    /**
     * applies the damage transporter on the player
     * @param damageTransporter
     */
    public void calculateDamage(DamageTransporter damageTransporter){
        playerBoard.calculateDamage(damageTransporter);
        notifyAllObservers();
    }



    /********************** changes observable **************************/
    @Override
    public void attach(ChangesObserver observer) {
        playerObservers.add(observer);
    }

    private void notifyAllObservers(){
        for(ChangesObserver ob : playerObservers) ob.notifyPlayerChange(this);
    }
}

