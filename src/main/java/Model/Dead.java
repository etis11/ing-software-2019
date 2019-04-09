package Model;

/**
 * this class is a type of player state, it is used when a player is dead
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Dead extends State{

    /**
     * this is the constructor method
     */
    public Dead(){

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
