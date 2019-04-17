package Model;

/**
 * this class identify particular action for player based on his damage
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class NormalAction extends Action {

    /**
     * this is the constructor method
     */
    public NormalAction(int moves){
        super(moves);
    }

    @Override
    public void nextState(Player p, String mexState) {
        super.nextState(p, mexState);
        if (mexState.equals("shoot")){
            p.setState(new Shoot());
            p.decrementMoves();
        }
        else if (mexState.equals("pickUp")){
            p.setState(new PickUp());
            p.decrementMoves();
        }
    }
}
