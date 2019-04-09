package Model;


/**
 * this abstract class rappresent player state during the game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public abstract class State {

    /**
     * this represent number of steps which the player can do
     */
    private int remainingSteps;
    //TODO
    //è necessario inizializzarlo?


    /**
     * this method return the numeber of reamainig steps
     * @return int representing remaining steps
     */
    public int getRemainingSteps(){
        return this.remainingSteps;
    }

    /**
     * this method verify for each subclass if playare can run
     */
    public abstract boolean tryToRun();

    /**
     * this method verify for each subclass if playare can pick up
     */
    public abstract boolean tryToPickUp();

    /**
     * this method verify for each subclass if playare can shoot
     */
    public abstract boolean tryToShoot();

    /**
     * this method verify for each subclass if playare can use a power up
     */
    public abstract boolean tryToUsePowerUp();

    //TODO
    //write documentation
    public void nextState(Player p){
        //TODO
        //astratto o no?
    }



}
