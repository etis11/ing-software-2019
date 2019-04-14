package Model;

/**
 * this class identify particular action for player based on his damage
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MoreAction extends Action{

    /**
     * this is the constructor method
     */
    public MoreAction(int moves){
        super(moves);
    }

    @Override
    public void nextState(Player p, String mexState) {
        super.nextState(p, mexState);
        if (mexState.equals("shoot")){
            p.setState(new Shoot());
            p.setRemainigStep(p.getRemainigStep()-1); //TODO forse metodo ad hoc
        }
        else if (mexState.equals("pickUp")){
            p.setState(new PickUpPlus());
            p.setRemainigStep(p.getRemainigStep()-1);
        }
    }
}
