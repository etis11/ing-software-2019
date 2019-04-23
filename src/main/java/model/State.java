package model;


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
    protected int remainingSteps;


    /**
     * this method return the numeber of reamainig steps
     * @return int representing remaining steps
     */
    public int getRemainingSteps(){
        return this.remainingSteps;
    }
    //TODO
    //add to UML

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
     * this method verify for each subclass if player can use a power up
     */
    public abstract boolean tryToUsePowerUp();
    //TODO
    //anche se ho 0 mosse rimanenti posso

    /**
     * this function calls setState in class Player
     * @param p Player who change his state
     */
    public void nextState(Player p, String mexState){
        //TODO
        //change UML
        int playerDamage = p.getPlayerBoard().getNumDamagePoints();

        if (playerDamage < 3) {
            p.setState(new NormalAction(p.getRemainingMoves()));
        }
        else if (playerDamage <6){
            p.setState(new MoreAction(p.getRemainingMoves()));
        }
        else{
            p.setState(new MostAction(p.getRemainingMoves()));
        }
    }



}
