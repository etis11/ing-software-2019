package Model;

import java.util.ArrayList;
import java.util.List;


public class Player {
    /**
     * name of the player
     */
    private String NAME;

    /**
     * current points of the player
     */
    private int points;

    /**
     * current state of the player
     */
    private State state;

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
    public  Player(){
        NAME = "";
        points = 0;
        state = new EndTurn();
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
        tile = null;
        playerBoard = null;
    }

    /**
     * A player with a name is created. His hand is empty and the state is EndTurn (he cant do anything). Since no map or tile
     * has been created, this player has not been positioned on any tile
     * @param name name of the player
     */
    public  Player(String name){
        NAME = name;
        points = 0;
        state = new EndTurn();
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
        tile = null;
        playerBoard = null;
    }

    /**
     * getter
     * @return the name of the player
     */
    public String getName() {
        return NAME;
    }

    /**
     * getter
     * @return the current points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * getter
     * @return the current state of the player
     */
    public State getState(){
        return state;
    }

    /**
     * getter. Sono indeciso se passare una copia delle armi oppure se passare la lista per valore. La seconda idea mi
     * sembra una pessima idea
     * @return the list of all weapons in the hand of the player
     */
    public List<WeaponCard> getWeapons(){
        //return weapons.stream().map(WeaponCard::new).collect(toList());
        //TODO
    }

    /**
     *getter
     * @return the number of weapons in the player's hand
     */
    public int getNumWeapons(){
        return weapons.size();
    }

    /**
     * getter. Sono indeciso se passare una copia dei power ups oppure se passare la lista per valore. La seconda idea mi
     * sembra una pessima idea
     * @return the list of all power ups in the player's hand
     */
    public List<PowerUpCard> getPowerUps(){
        //TODO
    }

    /**
     * getter
     * @return the number of power ups in the player's hand
     */
    public int getNumPowerUps(){
        return powerUps.size();
    }

    /**
     * getter
     * @return returns the tile where the player is located
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * getter
     * @return a reference of the player's playerBoard, where all his public information are stored
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }


    /**
     * This methods updates the player's score
     * @param points points that have to be added to the current score
     */
    public void addPoints(int points){
        this.points += points;
    }


    /**
     * change the state of the player
     * @param s new state where the player should be
     */
    public void setState(State s){
        state = s;
    }

    /**
     * Add the weapon to the player hand if he has less then 3 weapons. If the player has more than three weapons, this
     * method throws an exception
     * @param w weapon that has to be picked
     * @throws Exception
     */
    public void pickUpWeapon(WeaponCard w) throws Exception{
        if (weapons.size() > 3){
            throw new Exception("Il giocatore ha già 3 armi in mano");
        }
        else weapons.add(w);
    }


    /**
     * Draws a power up from the power up deck. Probabilmente questo metodo è da rivedere, non tollera di avere più di tre
     * power ups in mano, ma in realtà ci sono casi in cui MOMENTANEAMENTE il giocatore ne ha 4 in mano.
     * @param d power up deck
     * @throws Exception
     */
    public void drawPowerUp(Deck<PowerUpCard> d) throws  Exception{
        //TODO
        if (powerUps.size() > 3){
            throw new Exception("Il giocatore ha già 3 armi in mano");
        }
        else{
            PowerUpCard drawnPowerUp= d.draw();
            powerUps.add(drawnPowerUp);
        }

    }

    /**
     * Throws away the power up. The power up will be no longer in the hand of the player.
     * @param p copy of the power up that has to be thrown away
     * @return the power up that has to be thrown away
     * @throws Exception if the power up is not in the hand of the player
     */
    public PowerUpCard throwPowerUp(PowerUpCard p) throws Exception{
        PowerUpCard ret_card = null;
        if (!powerUps.contains(p)){
            throw new Exception("non si può throware il power up perchè non è in mano al giocatore");
        }
        else{
            for (int i =0; i < powerUps.size(); i++){
                if(powerUps.get(i).equals(p)){
                    ret_card= powerUps.remove(i);
                }
            }
        }
        return ret_card;
    }

    /**
     *
     */
    public void usePowerUp(PowerUpCard c){
        //TODO
    }

    /**
     *
     * @param w
     */
    public void reloadWeapon(Weapon w){
        //TODO
        //String reloadCost = w.getReloadCost();
        //playerBoard.getLoader().
    }

    /**
     * 
     */
    public void pickUpAmmoCard(){
        //TODO 
    }
}
