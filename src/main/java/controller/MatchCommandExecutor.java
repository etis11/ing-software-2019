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
        //check if the player can pickUp in his current state
        if (!owner.getState().canRun()) throw new NotValidActionException("You can't run in this state");
        //TODO recupero #movimenti dal model

        /*do{
            //TODO richiedi di insrire movimenti
        }while(!match.getMap().isMovementValid(new Movement(new ArrayList<>()("up")));*/
        //match.getMap().apply();

    }

    /**
     * routine to shoot
     */
    public void shootRoutine() throws NotValidActionException{
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //check if the player can pickUp in his current state
        if (!owner.getState().canShoot()) throw new NotValidActionException("You can't shoot in this state");
        //TODO controllo se almeno un'arma è carica
        //TODO chiedo con quali effetti sparare ed eventualmente verifico i costi aggiuntivi
        //TODO controllo che la strategia dell'arma mi permetta di colpire almeno uno
        //TODO pago i costi aggiuntivi
        //TODO chiedo  target e li verifico
        //TODO applico l'effetto
        //TODO for del controller che applica gli effetti

    }

    /**
     * routine to pick up weapon
     */
    public void pickWeaponRoutine() throws NotValidActionException{
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //check if the player can pickUp in his current state
        if (!owner.getState().canPickUp()) throw new NotValidActionException("You can't pickup in this state");
        WeaponCard w = new WeaponCard();
        if(owner.getTile().canContainWapons()){
            //TODO controllo player possa raccogliere l'arma e se può invio lista armi raccoglibili
            //TODO controllo se l'arma scelta s può raccogliere
            try {
                owner.pickUpWeapon(w);
                //TODO controllo se necessario scarto ed eventualmente invia lista armi e attende scarto
                //notify to view
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * routine to pick up ammo
     */
    public void pickAmmoRoutine() throws NotValidActionException{
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //check if the player can pickUp in his current state
        if (!owner.getState().canPickUp()) throw new NotValidActionException("You can't pickup in this state");
        if (owner.getTile().canContainAmmo()){
            try {
                owner.pickUpAmmoCard();
                //notify to view
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * routine to use power up routine
     */
    public void usePowerUpRoutine() throws NotValidActionException{
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //check if the player can pickUp in his state
        if (!owner.getState().canUsePowerUp()) throw new NotValidActionException("You can't use power up  in this state");
        //TODO
    }

    /**
     * routine to reload
     */
    public void reload() throws NotValidActionException {
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //check if the player can pickUp in his current state
        if (!owner.getState().canReload()) throw new NotValidActionException("You can't reload in this state");
        //TODO verifico che abbia le munizioni
        //TODO ricarico
        //posso più armi?
        endTurn();//posso farlo direttamente o devo aspettare
    }

    /**
     * routine to end turn
     */
    public void endTurn(){
        //TODO
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
