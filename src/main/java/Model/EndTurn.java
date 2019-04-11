package Model;

/**
 * this class is a type of state, it is used for end turn action
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
        remainingSteps = 2;
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
