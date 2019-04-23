package model;

/**
 * this class identify particular action for player based on his damage
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MostAction extends Action{

    /**
     * this is the constructor method
     */
    public MostAction(int moves){
        super(moves);
    }

    @Override
    public void nextState(Player p, String mexState) {
        super.nextState(p, mexState);
        if (mexState.equals("shoot")){
            p.setState(new ShootPlus());
            p.decrementMoves();
        }
        else if (mexState.equals("pickUp")){
            p.setState(new PickUpPlus());
            p.decrementMoves();
        }
    }
}
