package model.clientModel;

import model.Player;
import model.PlayerBoard;
import model.Tile;


public class SemplifiedPlayerEtis {

    private Player player ;

    public SemplifiedPlayerEtis(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {

        Tile tile = player.getTile();
        PlayerBoard playerBoard = player.getPlayerBoard();
        int marx = playerBoard.getNumMarks();
        String name = player.getName() ;
        int damagePoints = playerBoard.getNumDamagePoints();
        int remainingHp =  playerBoard.getRemainingHp();

        return "Giocatore si chiama : "+name
                +"con marks: "+marx
                +"con damagePoints : "+damagePoints
                +"e remainingHp : "+remainingHp
                +"posizionato in : "+tile.getID()
                ;
    }
}
