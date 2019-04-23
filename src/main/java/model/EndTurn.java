package model;

/**
 * this class is a type of state, it is used for end turn action
 *
 * @author Alessandro Passoni
 * @version 1.0
 *
 */
public class EndTurn extends State{

    /**
     * MAX_MOVES is number of moves for each turn
     */
    public final int MAX_MOVES = 2;

    /**
     * this is the constructor method
     */
    public EndTurn(){
        //is only beahavioural function
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
        p.setRemainingMoves(MAX_MOVES);
        super.nextState(p, mexState);

    }
}
