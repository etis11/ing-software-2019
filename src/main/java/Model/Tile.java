package Model;

import java.util.LinkedList;
import java.util.List;


public  abstract class Tile {
    /**
     * reference to the tile plaed to the east
     */
    private Tile eastTile;

    /**
     * reference to the tile plaed to the west
     */
    private Tile westTile;

    /**
     * reference to the tile plaed to the north
     */
    private Tile northTile;

    /**
     * reference to the tile plaed to the south
     */
    private Tile southTile;

    /**
     * list of all players that are in the tile
     */
    private List<Player> players;

    /**
     * ammoCard of the tile. This value is null if there is no ammo or the tile is a regen point
     */
    private AmmoCard ammoCard;

    /**
     * the weapons that are in the tile. this field is null only if the tile is a normaltile
     */
    private LinkedList<WeaponCard> weapons;

    /**
     * creates a tile with all the values set to null
     */
    public Tile(){
        eastTile = null;
        westTile = null;
        northTile = null;
        southTile = null;
        players = new LinkedList<>();
        ammoCard = new AmmoCard();
        
    }

    /**
     * returns the tile place to the east
     * @return the east tile, can be null
     */
    public Tile getEastTile() {
        return eastTile;
    }

    /**
     * assign to the east tile a know tile.
     * @param eastTile a tile
     */
    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    /**
     * returns the tile place to the west
     * @return the west tile, can be null
     */
    public Tile getWestTile() {
        return westTile;
    }

    /**
     * assign to the west tile a know tile.
     * @param westTile a tile
     */
    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    /**
     * returns the tile place to the north
     * @return the north tile, can be null
     */
    public Tile getNorthTile() {
        return northTile;
    }

    /**
     * assign to the north tile a know tile.
     * @param northTile a tile
     */
    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    /**
     * returns the tile place to the south
     * @return the south tile, can be null
     */
    public Tile getSouthTile() {
        return southTile;
    }

    /**
     * assign to the south tile a know tile.
     * @param southTile a tile
     */
    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }


    /**
     * Gets the players that are present in this tile
     * @return list of players. can be null
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the ammo card in the tile
     * @return an ammo card
     */
    public abstract AmmoCard getAmmoCard();


    /**
     * checks if the tile contains the ammo card.
     * @return true when the ammo card is present, false when it's not
     */
    public abstract boolean isPresentAmmoCard();

    /**
     * Gets the weapons in the tile.
     * @return weapons, cant be null
     */
    public abstract LinkedList<WeaponCard> getWeapons();

    /**
     * Checks if the tile contains the weapons
     * @return True if the tile is a regen point, false if it's a normal tile.
     */
    public abstract boolean arePresentWeapons();

    /**
     *Puts a weapon card in the weapons list. Used when the player is givin up a weapon.
     * @param w the weapon that the player wants to drop
     */
    public abstract void putWeaponCard(WeaponCard w);



}
