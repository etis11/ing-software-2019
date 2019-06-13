package model;


import exceptions.NotValidMovesException;

import javax.naming.OperationNotSupportedException;
import java.util.LinkedList;
import java.util.List;
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
    private final String name;
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
        playerObservers = new LinkedList<>();
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
        playerObservers = new LinkedList<>();
    }

    public Player(String name, State s) {
        this.name = name;
        points = 0;
        state = s;
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
        oldState = null;
        playerObservers = new LinkedList<>();
    }

    /**
     * This method returns the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
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
        notifyAllObservers();
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
        notifyAllObservers();
    }

    /**
     * decrements number of moves after an action has chosen
     */
    public void decrementMoves() {
        this.remainingMoves--;
        notifyAllObservers();
    }

    /**
     * This method returns a copy of the weaponsCards in the player's hand.
     *
     * @return the list of all weapons in the hand of the player
     */
    public List<WeaponCard> getWeapons() {
        return weapons.stream().map(WeaponCard::new).collect(Collectors.toCollection(LinkedList::new));
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

    public void pickUpPowerUp(PowerUpCard p) {
        //TODO mancano tutti i controlli e l'implementazione del metodo
        powerUps.add(p);
        notifyAllObservers();
        throw  new RuntimeException("da implementare per bene");
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
        notifyAllObservers();
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
     * @throws Exception if the player has already 4 weapons
     */
    public void pickUpWeapon(WeaponCard w) throws Exception {
        if (w == null) throw new IllegalArgumentException("Passato un valore nullo");
        if (weapons.size() > 4) {
            throw new Exception("Il giocatore ha già 4 armi in mano");
        } else weapons.add(w);
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
            throw new Exception("non si può throware il power up perchè non è in mano al giocatore");
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
        notifyAllObservers();
    }

    /**
     *
     */
    public void usePowerUp(PowerUpCard c) {
        //TODO
        notifyAllObservers();
    }

    /**
     * @param w
     */
    public void reloadWeapon(WeaponCard w) {
        //TODO probabilmente non necessario
        notifyAllObservers();
    }

    /**
     * This method picks up the Ammo card in the current tile. The legality of this action must be controlled before calling
     * this method. The ammoCard is used (passed to the loader). The player draws a powerUp if the drawPowerUp is true
     */
    public void pickUpAmmoCard() throws Exception {
        //TODO forse non ci serve
        notifyAllObservers();
    }

    /**
     * moves a player of a given path if it is possible
     *
     * @param movement Movement containing the list of moves to do
     */
    public void move(Movement movement) {
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
        newTile.addPlayer(this);
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", tile=" + tile +
                '}';
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

