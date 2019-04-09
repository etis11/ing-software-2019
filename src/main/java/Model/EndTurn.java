package Model;

/**
 * this class is a atype of state, it is used for end turn action
 *
 * @author Alessandro Passoni
 * @version 1.0
 *
 */
public class EndTurn extends State{

    /**
     * this is the constructor method
     */
    public EndTurn(){

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
