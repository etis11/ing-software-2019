package model.clientModel;

import model.PlayerBoard;
import model.PowerUpCard;
import model.Tile;
import model.WeaponCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * semplified version of the player for the client.
 */
public class SemplifiedPlayer {

    private String name;
    private int remainingMoves;
    private int numWeaponCard;
    private List<SemplifiedWeaponCard> weaponCards;
    private int numPowerUps;
    private List<PowerUpCard> powerUpCards = new LinkedList<>();
    private int tile;
    private SemplifiedPlayerBoard playerBoard;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public int getNumWeaponCard() {
        return numWeaponCard;
    }

    public void setNumWeaponCard(int numWeaponCard) {
        this.numWeaponCard = numWeaponCard;
    }

    public List<SemplifiedWeaponCard> getWeaponCards() {
        return weaponCards;
    }

    public void setWeaponCards(SemplifiedWeaponCard[] weaponCards) {
        this.weaponCards = new LinkedList<>(Arrays.asList(weaponCards));
    }

    public int getNumPowerUps() {
        return numPowerUps;
    }

    public void setNumPowerUps(int numPowerUps) {
        this.numPowerUps = numPowerUps;
    }

    public List<PowerUpCard> getPowerUpCards() {
        return powerUpCards;
    }

    public void setPowerUpCards(PowerUpCard[] powerUpCards) {
        this.powerUpCards = new ArrayList<>(Arrays.asList(powerUpCards));
    }

    public int getTile() {
        return tile;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }
    public int getNumWeapons() {
        return weaponCards.size();
    }

    public SemplifiedPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(SemplifiedPlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public int getNumLoadedWeapons(){
        int i = 0;
        for (SemplifiedWeaponCard w: weaponCards){
            if(w.isLoaded()){
                i++;
            }
        }
        return i;
    }

    public int getNumEmptyWeapons(){
        return weaponCards.size() - getNumLoadedWeapons();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(this == obj) return true;

        if(!(obj instanceof SemplifiedPlayer)) return false;

        SemplifiedPlayer player = (SemplifiedPlayer) obj;

        return this.getName().equals(player.getName());
    }
}
