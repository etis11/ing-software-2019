package Model;

/**
 * this class is a type of player state, it is used after a player was Overkilled
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class OverKilled extends State {

    /**
     * this is the constructor method
     */
    public OverKilled(){

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
        p.setState(new EndTurn());
    }
}
