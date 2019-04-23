package model;

/**
 * this class is a type of player state, it is used for shoot action
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Shoot extends State{

    /**
     * this is constructor method
     */
    public Shoot(){
        remainingSteps--;
    }


    @Override
    public boolean tryToRun() {

        return false;
    }

    @Override
    public boolean tryToPickUp() {

        return false;
    }

    @Override
    public boolean tryToShoot() {

        return false;
    }

    @Override
    public boolean tryToUsePowerUp() {

        return false;
    }

    @Override
    public void nextState(Player p, String mexState) {
        super.nextState(p, mexState);
    }
}
