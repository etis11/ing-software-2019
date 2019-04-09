package Model;

import java.util.LinkedList;

public class Board {



    private int skulls;
    private GameMap map;
    private LinkedList<Player> players;
    private Deck<PowerUpCard> powerUpDeck;
    private Deck<WeaponCard> weaponDeck;
    private Deck<AmmoCard> ammoDeck;


    public int getSkulls() {
        return skulls;
    }

    public void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public Deck<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    public void setPowerUpDeck(Deck<PowerUpCard> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    public Deck<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }

    public void setWeaponDeck(Deck<WeaponCard> weaponDeck) {
        this.weaponDeck = weaponDeck;
    }

    public Deck<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }

    public void setAmmoDeck(Deck<AmmoCard> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }
}
