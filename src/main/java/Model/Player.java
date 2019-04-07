package Model;

import java.util.ArrayList;



public class Player {
    private  String NAME;
    private int points;
    private State state;
    private List<WeaponCard> weapons;
    private List<PowerUpCard> powerUps;
    private Tile tile;
    private PlayerBoard playerBoard;
    private final int MAX_WEAPON_CARDS = 3;
    private final int MAX_POWERUP_CARDS = 3;

    //creators

    void Player(){
        NAME = "";
        points = 0;
        state = new EndTurn();
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
        tile = null;
        playerBoard = null;
    }

    void Player(String name){
        NAME = name;
        points = 0;
        state = new EndTurn();
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
        tile = null;
        playerBoard = null;
    }

    public String getName() {
        return NAME;
    }

    public int getPoints() {
        return points;
    }

    public State getState(){
        return state;
    }

    public List<WeaponCard> getWeapons(){
        //return weapons.stream().map(WeaponCard::new).collect(toList());
        //TODO
    }

    public int getNumWeapons(){
        return weapons.size();
    }
    public List<PowerUpCard> getPowerUps(){
        //TODO
    }

    public int getNumPowerUps(){
        return powerUps.size();
    }

    public Tile getTile() {
        return tile;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }


    public void addPoints(int points){
        this.points += points;
    }

    public void setState(State s){
        state = s;
    }

    public void pickUpWeapon(WeaponCard w) throws Exception{
        if (weapons.size() > 3){
            throw new Exception("Il giocatore ha già 3 armi in mano");
        }
        else weapons.add(w);
    }

    public void drawPowerUp(Deck<PowerUpCard> d){
        if (powerUps.size() > 3){
            throw new Exception("Il giocatore ha già 3 armi in mano");
        }
        else{
            PowerUpCard drawnPowerUp= d.draw();
            powerUps.add(drawnPowerUp);
        }

    }

    public PowerUpCard throwPowerUp(PowerUpCard p) throws Exception{
        PowerUpCard ret_card = null;
        if (!powerUps.cointains(p)){
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

    public void usePowerUp(PowerUpCard){
        //TODO
    }

    public void reloadWeapon(Weapon w){
        String reloadCost = w.getReloadCost();
        playerBoard.getLoader().
    }

    public void pickUpAmmoCard(){
        //TODO
    }
}
