package controller;

import model.Match;
import model.Movement;
import model.Player;
import model.WeaponCard;

import java.util.ArrayList;

public class MatchCommandExecutor extends AbstractCommandExecutor{

    public MatchCommandExecutor(Match match) {
        this.match = match;
        this.commandList = new ArrayList<>();
    }

    /**
     * routine to walk
     */
    public void walkingRoutine(){
        //TODO controllo se lo stato mi permette di muovermi
        //TODO recupero #movimenti dal model

        /*do{
            //TODO richiedi di insrire movimenti
        }while(!match.getMap().isMovementValid(new Movement(new ArrayList<>()("up")));*/
        //match.getMap().apply();

    }

    /**
     * routine to shoot
     */
    public void shootRoutine(){
        //TODO controllo se nello stato posso sparare

    }

    /**
     * routine to pick up weapon
     */
    public void pickWeaponRoutine(){
        //TODO controllo se lo stato mi permette di raccogliere
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        WeaponCard w = new WeaponCard();
        //if o eccezione apposita notvalidaction???
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
    public void pickAmmoRoutine(){
        //TODO controllo se lo stato mi permette di raccogliere
        //TODO penso vada eliminato ma almeno posso scrivere il codice
        Player owner = new Player();
        //if o eccezione apposita notvalidaction???
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
    public void usePowerUpRoutine(){
        //TODO
    }

    /**
     * routine to reload
     */
    public void reload(){
        //TODO posso ricaricare da quello stato
        //TODO verifico che abbia le munizioni
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
