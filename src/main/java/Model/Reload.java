package Model;

/**
 * this class is a type of player state, it is used for reload action
 *
 * @author Alesandro Passoni
 * @version 1.0
 */
public class Reload extends State{

    /**
     * this is the constructor method
     */
    public Reload(){

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
}
