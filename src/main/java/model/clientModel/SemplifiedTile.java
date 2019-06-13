package model.clientModel;

import model.AmmoCard;
import model.Player;
import model.WeaponCard;

import java.util.List;

public class SemplifiedTile {
    private final int id;
    private final boolean ammoTile;
    private final boolean weaponTile;
    private List<SemplifiedPlayer> players;
    private AmmoCard ammoCard;
    private List<SemplifiedWeaponCard> weaponCards;

    public SemplifiedTile(int id, boolean ammoTile, boolean weaponTile) {
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
    }

    public int getRow(){
        return
    }

    public int getCol(){

    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {

        String weaponCardsString = "";
        if(this.weaponCards !=null && this.weaponCards.size()>0){
            for(SemplifiedWeaponCard weaponCard : this.weaponCards){
                weaponCardsString= weaponCardsString +" "+weaponCard.toString();
            }
        }


        String playersString = "";
        if(this.players !=null && this.players.size()>0){
            for(SemplifiedPlayer player : this.players){
                playersString= playersString+" "+player.getName();
            }
        }
        return "SemplifiedTile{" +
                "id=" + id +
                " , players=" + playersString +
                ", weaponCards=" + weaponCardsString +
                '}';
    }

}
