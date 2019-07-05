package model.clientModel;

import model.AmmoCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Semplified version of the tile for the client. It has all the information regarding a tile and what it's contained in
 * it
 */
public class SemplifiedTile {
    private  int id;
    private  boolean ammoTile;
    private  boolean weaponTile;
    private List<SemplifiedPlayer> players;
    private AmmoCard ammoCard;
    private List<SemplifiedWeaponCard> weapons;

    public SemplifiedTile(){

    }


    public SemplifiedTile(int id, boolean ammoTile, boolean weaponTile) {
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
        this.players = new ArrayList<>();
    }

    public void setAmmoTile(boolean ammoTile) {
        this.ammoTile = ammoTile;
    }

    public void setWeaponTile(boolean weaponTile) {
        this.weaponTile = weaponTile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AmmoCard getAmmoCard(){
        return ammoCard;
    }

    public boolean isAmmoTile(){
        return ammoTile;
    }

    public void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    public List<SemplifiedWeaponCard> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<SemplifiedWeaponCard> weapons) {
        this.weapons = weapons;
    }

    public int getId(){
        return id;
    }

    public List<SemplifiedPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SemplifiedPlayer> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        String weaponCardsString = "";
        if(this.weapons !=null && this.weapons.size()>0){
            for(SemplifiedWeaponCard weaponCard : this.weapons){
                weaponCardsString= weaponCardsString +"  ||  "+weaponCard.getName();
                weaponCard.getRedCost();
            }
        }


        String playersString = "";
        if(this.players !=null && this.players.size()>0){
            for(SemplifiedPlayer player : this.players){
                playersString= playersString+" "+player.getName();
            }
        }
        return weaponCardsString;
    }

}
