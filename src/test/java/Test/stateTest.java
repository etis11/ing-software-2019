package Test;


import Model.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class stateTest {

    private State state;
    private Player p;

    @Test
    public void EndTurnState(){

        state = new EndTurn();


        assertTrue(state instanceof EndTurn, "ERROR of Instance");

        assertTrue(state.getRemainingSteps()==2, "ERROR remaining steps bad initialized");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        //TODO
        //test for action, but necessary to assign damage
    }

    @Test
    public void normalActionState(){

        state = new NormalAction();
        p = new Player();

        assertTrue(state instanceof NormalAction, "ERROR of Instance");

        assertTrue(state.tryToRun(), "ERROR has to be allowed to run");

        assertTrue(state.tryToPickUp(), "ERROR has to be allowed to pick up");

        assertTrue(state.tryToShoot(), "ERROR has to be allowed to shoot");

        assertTrue(state.tryToUsePowerUp(), "ERROR has to be allowed to use power up");

        state.nextState(p,"run");
        assertTrue(p.getState() instanceof Run, "ERROR not in run state" );
        //TODO
        //test for action
    }
}
