package Model;

/**
 * this class is a type of state used to identify allowed action for player
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Action extends State{

    /**
     * this is the constructor method
     */
    public Action(){

    }

    @Override
    public boolean tryToRun() {
        //TODO
        return false;
    }

    @Override
    public boolean tryToPickUp() {
        //TODO
        return false;
    }

    @Override
    public boolean tryToShoot() {
        //TODO
        return false;
    }

    @Override
    public boolean tryToUsePowerUp() {
        //TODO
        return false;
    }
}
