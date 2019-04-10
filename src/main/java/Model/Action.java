package Model;

/**
 * this class is a type of state used to identify allowed action for player
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Action extends State{

    /**
     * this is the constructor method, it has no body as this class is behavioural
     */
    public Action(){
        //this method is only behavioural so it doesn't need to initialize anything
    }

    @Override
    public boolean tryToRun() {
        //TODO
        return remainingSteps>0;    //maggiore o maggiore uguale?
    }

    @Override
    public boolean tryToPickUp() {
        return remainingSteps>0;
    }

    @Override
    public boolean tryToShoot() {
        return remainingSteps>0;
    }

    @Override
    public boolean tryToUsePowerUp() {
        return remainingSteps>0;
    }

    @Override
    public void nextState(Player p, String mexState) {

        if (mexState.equals("run")){
            p.setState(new Run());
        }
        else if (mexState.equals("reload")){
            p.setState(new Reload());
        }
        else if (mexState.equals("endTurn")){
            p.setState(new EndTurn());
        }
    }
}
