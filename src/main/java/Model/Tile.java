package Model;

import java.util.LinkedList;
import java.util.List;


public  abstract class Tile {



    private Tile eastTile;
    private Tile westTile;
    private Tile northTile;
    private Tile southTile;
    private List<Player> players;
    private AmmoCard ammoCard;
    private LinkedList<WeaponCard> weapons;

    public Tile(){
        eastTile = null;
        westTile = null;
        northTile = null;
        southTile = null;
        players = new LinkedList<>();
        ammoCard = new AmmoCard();
        
    }

    public Tile getEastTile() {
        return eastTile;
    }

    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    public Tile getWestTile() {
        return westTile;
    }

    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    public Tile getNorthTile() {
        return northTile;
    }

    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    public Tile getSouthTile() {
        return southTile;
    }

    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public abstract AmmoCard getAmmoCard();

    public abstract boolean isPresentAmmoCard();

    public abstract LinkedList<WeaponCard> getWeapons();

    public abstract boolean arePresentWeapons();

    public abstract void putWeaponCard(WeaponCard w);



}
