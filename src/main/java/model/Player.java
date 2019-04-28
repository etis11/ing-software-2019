package model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class Player {
    /**
     * name of the player
     */
    private final String NAME;

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
     * max number of weapons that can be in the player's hand
     */
    private final int MAX_WEAPON_CARDS = 3;

    /**
     * max number of power ups that can be in the player's hand
     */
    private final int MAX_POWERUP_CARDS = 3;


    /**
     * A player without name is created. His hand is empty and the state is EndTurn (he cant do anything). Since no map or tile
     * has been created, this player has not been positioned on any tile
     */
    public Player() {
        NAME = "";
        points = 0;
        state = new EndTurn();
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
    }

    /**
     * A player with a name is created. His hand is empty and the state is EndTurn (he cant do anything). Since no map or tile
     * has been created, this player has not been positioned on any tile
     *
     * @param name name of the player
     */
    public Player(String name) {
        NAME = name;
        points = 0;
        state = new EndTurn();
        weapons = new LinkedList<>();
        powerUps = new LinkedList<>();
        tile = null;
        playerBoard = new PlayerBoard();
        remainingMoves = 0;
    }

    /**
     * This method returns the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return NAME;
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
    public void decrementMoves(){
        this.remainingMoves--;
    }

    /**
     * This method returns a copy of the weaponsCards in the player's hand.
     *
     * @return the list of all weapons in the hand of the player
     */
    public LinkedList<WeaponCard> getWeapons() {
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
    public LinkedList<PowerUpCard> getPowerUps() {
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
     * This method return the current location of the player
     *
     * @return returns the tile where the player is located
     */
    public Tile getTile() {
        return tile;
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
     * This method updates the player's score
     *
     * @param points points that have to be added to the current score
     */
    public void addPoints(int points) {
        this.points += points;
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
     * Add the weapon to the player hand if he has less then 3 weapons. If the player has more than three weapons, this
     * method throws an exception
     *
     * @param w weapon that has to be picked
     * @throws Exception            if the player has already 4 weapons
     * @throws NullPointerException if the weapon is null
     */
    public void pickUpWeapon(WeaponCard w) throws Exception, NullPointerException {
        if (w == null) throw new NullPointerException("Passato un valore nullo");
        if (weapons.size() > 4) {
            throw new Exception("Il giocatore ha già 4 armi in mano");
        } else weapons.add(w);
    }


    /**
     * Throws away the power up. The power up will be no longer in the hand of the player.
     *
     * @param p copy of the power up that has to be thrown away
     * @return the power up that has to be thrown away
     * @throws Exception if the power up is not in the hand of the player
     */
    public PowerUpCard throwPowerUp(PowerUpCard p) throws Exception {
        PowerUpCard ret_card = null;
        if (!powerUps.contains(p)) {
            throw new Exception("non si può throware il power up perchè non è in mano al giocatore");
        } else {
            for (int i = 0; i < powerUps.size(); i++) {
                if (powerUps.get(i).equals(p)) {
                    ret_card = powerUps.remove(i);
                }
            }
        }
        return ret_card;
    }

    /**
     *
     */
    public void usePowerUp(PowerUpCard c) {
        //TODO
    }

    /**
     * @param w
     */
    public void reloadWeapon(WeaponCard w) {
        //TODO
    }

    /**
     * This method picks up the Ammo card in the current tile. The legality of this action must be controlled before calling
     * this method. The ammoCard is used (passed to the loader). The player draws a powerUp if the drawPowerUp is true
     */
    public void pickUpAmmoCard() throws Exception {
        //TODO
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }
}

