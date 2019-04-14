package Model;

/**
 * this class is a type of player state, it is used for run action
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Run extends State {

    /**
     * this is the constructor method, ti creates the state and decrement possible action to do in the turn
     */
    public Run(){

        // is only behavioural function

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
        //TODO
        //vedi commento in pickUp
        int playerDamage = p.getPlayerBoard().getNumDamagePoints();

        if (playerDamage < 3) {
            p.setState(new NormalAction(p.getRemainigStep()));
        }
        else if (playerDamage <6){
            p.setState(new MoreAction(p.getRemainigStep()));
        }
        else{
            p.setState(new MostAction(p.getRemainigStep()));
        }
    }
}
