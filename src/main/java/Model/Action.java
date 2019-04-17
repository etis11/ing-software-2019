package Model;

/**
 * this class is a type of state used to identify allowed action for player
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Action extends State{

    private  int remainingMoves;

    /**
     * this is the constructor method, it has no body as this class is behavioural
     */
    public Action(int moves){
        this.remainingMoves = moves;
    }

    @Override
    public boolean tryToRun() {
        return remainingMoves>0;
    }

    @Override
    public boolean tryToPickUp() {
        return remainingMoves>0;
    }

    @Override
    public boolean tryToShoot() {
        return remainingMoves>0;
    }

    @Override
    public boolean tryToUsePowerUp() {
        return remainingMoves>0;
    } //maggiore o maggiore uguale?

    @Override
    public void nextState(Player p, String mexState) {

        if (mexState.equals("run")){
            p.setState(new Run());
            p.setRemainigMoves(p.getRemainigMoves()-1);   //TODO forse può essre implemtato ad hoc
        }
        else if (mexState.equals("reload")){
            p.setState(new Reload());
        }
        else if (mexState.equals("endTurn")){
            p.setState(new EndTurn());
        }
    }
}
