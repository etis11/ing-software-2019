package model.clientModel;

import model.*;

import java.nio.file.Watchable;
import java.util.List;

public class SemplifiedTileEtis {
    private final int id;

    private List<Player> players;

    private int row;
    private int column;

    private final boolean ammoTile;

    private AmmoCard ammoCard;

    private final boolean weaponTile;

    private List<WeaponCard> weaponCards;

    public SemplifiedTileEtis(int id, boolean ammoTile, boolean weaponTile){
        this.id = id;
        this.ammoTile = ammoTile;
        this.weaponTile = weaponTile;
    }

    public SemplifiedTileEtis(int id, List<Player> players, boolean ammoTile, AmmoCard ammoCard, boolean weaponTile, List<WeaponCard> weaponCards) {
        this.id = id;
        this.players = players;
        this.ammoTile = ammoTile;
        this.ammoCard = ammoCard;
        this.weaponTile = weaponTile;
        this.weaponCards = weaponCards;
    }

    @Override
    public String toString() {

        String weaponCardsString = "";
        if(this.weaponCards !=null && this.weaponCards.size()>0){
            for(WeaponCard weaponCard : this.weaponCards){
                weaponCardsString= weaponCardsString +" "+weaponCard.getName();
            }
        }


        String playersString = "";
        if(this.players !=null && this.players.size()>0){
        for(Player player : this.players){
            playersString= playersString+" "+player.getName();
        }
        }
        return "SemplifiedTile{" +
                "id=" + id +
                " Tile[" + row +"]"+
                "[" + column +"]"+
                " , players=" + playersString +
          //      ", ammoTile=" + ammoTile +
            //    ", ammoCard=" + ammoCard.toString() +
           //     ", weaponTile=" + weaponTile +
                ", weaponCards=" + weaponCardsString +
                '}';
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
