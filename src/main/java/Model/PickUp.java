package Model;

/**
 * this class is a type of player state, it is used for pick up action
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class PickUp extends State {

    /**
     * this is the constructor method
     */
    public PickUp(){
        remainingSteps--;
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
        //se non poso avere dano autoinflitto nextState è per forza normalAction
        int playerDamage = p.getPlayerBoard().getNumDamagePoints();

        if (playerDamage < 3) {
            p.setState(new NormalAction());
        }
        else if (playerDamage <6){
            p.setState(new MoreAction());
        }
        else{
            p.setState(new MostAction());
        }
    }
}
