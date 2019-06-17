package model.clientModel;

import model.AmmoCard;
import model.Player;
import model.WeaponCard;

import java.util.ArrayList;
import java.util.List;

public class SemplifiedTile {
    private final int id;
    private final boolean ammoTile;
    private final boolean weaponTile;
    private List<SemplifiedPlayer> players;
    private AmmoCard ammoCard;

    public List<SemplifiedWeaponCard> getWeaponCards() {
        return weaponCards;
    }

    public void setWeaponCards(List<SemplifiedWeaponCard> weaponCards) {
        this.weaponCards = weaponCards;
    }

    private List<SemplifiedWeaponCard> weaponCards;

    public SemplifiedTile(int id, boolean ammoTile, boolean weaponTile) {
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
        this.players = new ArrayList<>();
    }

    public AmmoCard getAmmoCard(){
        return ammoCard;
    }

    public boolean isAmmoTile(){
        return ammoTile;
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
        if(this.weaponCards !=null && this.weaponCards.size()>0){
            for(SemplifiedWeaponCard weaponCard : this.weaponCards){
                weaponCardsString= weaponCardsString +"  ||  "+weaponCard.getName();
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
