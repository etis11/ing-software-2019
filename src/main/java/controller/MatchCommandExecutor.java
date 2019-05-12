package controller;

import exceptions.NotValidActionException;
import model.Match;
import model.Movement;
import model.Player;
import model.WeaponCard;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MatchCommandExecutor extends AbstractCommandExecutor{

    public MatchCommandExecutor(Match match) {
        this.match = match;
        this.commandList = new ConcurrentLinkedQueue<>();
    }

    /**
     * routine to walk
     */
    public void walkingRoutine(Player owner) throws NotValidActionException{
        //probabilmente no serve
    }

    public void askToWalk(Player owner) throws NotValidActionException{
        //probabilmente no serve
    }

    /**
     * routine to shoot
     */
    public void shootRoutine() throws NotValidActionException{
        //controllo se almeno un'arma è carica
        //chiedo con quali effetti sparare ed eventualmente verifico i costi aggiuntivi
        //controllo che la strategia dell'arma mi permetta di colpire almeno uno
        //pago i costi aggiuntivi
        //chiedo  target e li verifico
        //applico l'effetto
        //for del controller che applica gli effetti

    }

    /**
     * routine to pick up weapon
     */
    public void pickWeaponRoutine() throws NotValidActionException{
        //probabilmente no serve
    }

    /**
     * routine to pick up ammo
     */
    public void pickAmmoRoutine() throws NotValidActionException{
        //probabilmente no serve
    }

    /**
     * routine to use power up routine
     */
    public void usePowerUpRoutine() throws NotValidActionException{
        //probabilmente no serve
    }

    /**
     * routine to reload
     */
    public void reload() throws NotValidActionException {
        //probabilmente no serve
    }

    /**
     * routine to end turn
     */
    public void endTurn(){
        //probabilmente no serve
    }

    /**
     * return to the view player weapon
     */
    public void getWeapons(){
        //TODO
    }

    /**
     * routine to the view weapons manual
     */
    public void weaponsManual(){
        //TODO
    }

    /**
     * routine to the view player power up
     */
    public void getPowerUps(){
        //TODO
    }

    /**
     * routine to the view power up manual
     */
    public void powerUpManual(){
        //TODO
    }

    /**
     * routine to the view player board
     */
    public void getBoard(){
        //TODO
    }

    /**
     * routine to the view the map
     */
    public void getMap(){
        //TODO
    }

    /**
     * routine to the view player current tile
     */
    public void getTile(){
        //TODO
    }

    /**
     * routine to the view pickable weapons in the current player weapon tile
     */
    public void getPickableWeapons(){
        //TODO
    }

    /**
     * routine to the view number of remaining deaths
     */
    public void getRemainingDeaths(){
        //TODO
    }

    /**
     * routine to the view player points
     */
    public void getPoints(){
        //TODO
    }

    /**
     * routine to the view enemy weapons not loaded
     */
    public void getEnemyWeapons(){
        //TODO
    }

    /**
     * routine to the view info of deaths
     */
    public void getDeathsInfos(){
        //TODO
    }
}
