package model;

import java.util.LinkedList;
import java.util.List;


/**
 * This class handles nearly everything in the game. Starting from the GameMap,Decks,Players and so on.
 * THe Match contains every Player and handles all moves done by players in the gamemap each turn.
 * */
public class Match {


/**
 * Integer used to count number of lifes till GameOver.An integer that is between 5(included) and 8(included)
 * */
    private int skulls;

    /**
     * This is the map of the game made as a graph where Tiles are connected between them.Moreover there are 4 ways
     * of combining the Map based on a selection made in the beginning of the game
     * */
    private GameMap map;

    /**
     * List of players playing when game starts. Such list is longer no lesser then three and no bigger then five.
     * Each player has  his own playerboard and cards
     * */
    private List<Player> players = new LinkedList<>();

    /**
     * One of the three Decks used during gameplay.It contains all PowerUp cards (playable or not during game)
     * */
    private Deck<PowerUpCard> powerUpDeck;

    /**
     * THe second deck used for WeaponCards. It contains 21 Weapon Cards,each with different effect from the others.
     * */
    private Deck<WeaponCard> weaponDeck;

    /**
     * THe third and last deck of Cards. It contains all Ammocards (36). Moreover, in case these cards end up
     * being used all, the deck gets shuffled and ready to be used again.
     * */
    private Deck<AmmoCard> ammoDeck;


    /**
     * Method that returns number of skulls left during gameplay.
     * */
    public int getSkulls() {
        return skulls;
    }

    /**
     * Method called before gameplay. Used to store the number of skulls before gameplay so that each player
     * before game starting, gets to know how long the game might last and so on.
     * */
    public void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    /**
     * As stated above, this method returns the type of map that is going to be used during gameplay. The choice
     * has been made before with its setter
     * */
    public GameMap getMap() {
        return map;
    }

    /**
     * Chooses what kind of map to be used during the next game. There are 4 different combinatios.Once chosen,
     * it mustn't change during gameplay
     * */
    public void setMap(GameMap map) {
        this.map = map;
    }


    /**
     * This method returns the list of players that are going to/will play when game starts
     * */
    public static List<Player> getPlayers() {
        //TODO
        return new LinkedList<>();
    }

    /**
     * Method used to set list of players before game starting.
     * */
    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    /**
     * Method used to return the deck of PowerUp cards
     * */
    public Deck<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }
//TODO need to know if this method should be used or not
    public void setPowerUpDeck(Deck<PowerUpCard> powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }
    /**
     * Method used to return the deck of Weapon cards
     * */
    public Deck<WeaponCard> getWeaponDeck() {
        return weaponDeck;
    }
//TODO need to know if this method should be used or not
    public void setWeaponDeck(Deck<WeaponCard> weaponDeck) {
        this.weaponDeck = weaponDeck;
    }
    /**
     * Method used to return the deck of Ammo cards
     * */
    public Deck<AmmoCard> getAmmoDeck() {
        return ammoDeck;
    }
//TODO need to know if this method should be used or not
    public void setAmmoDeck(Deck<AmmoCard> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }
}
