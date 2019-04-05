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
    public abstract void tryToRun();

    /**
     * this method verify for each subclass if playare can pick up
     */
    public abstract void tryToPickUp();

    /**
     * this method verify for each subclass if playare can shoot
     */
    public abstract void tryToShoot();

    /**
     * this method verify for each subclass if playare can use a power up
     */
    public abstract void tryToUsePowerUp();

    //TODO
    //write documentation
    //param is necessary ?
    public void nextState(/*Player p*/){
        //TODO
        //astratto o no?
    }



}
