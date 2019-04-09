package Model;

import java.util.LinkedList;

public class Board {



    private int skulls;
    private GameMap map;
    private LinkedList<Player> players;
    private Deck<powerUpDeck> powerUpDeck;
    private Deck<weaponDeck> weaponDeck;
    private Deck<ammoDeck> ammoDeck;


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

    public Deck<powerUpDeck> getPowerUpDeck() {
        return powerUpDeck;
    }

    public void setPowerUpDeck(Deck<powerUpDeck> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    public Deck<weaponDeck> getWeaponDeck() {
        return weaponDeck;
    }

    public void setWeaponDeck(Deck<weaponDeck> weaponDeck) {
        this.weaponDeck = weaponDeck;
    }

    public Deck<ammoDeck> getAmmoDeck() {
        return ammoDeck;
    }

    public void setAmmoDeck(Deck<ammoDeck> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }
}
