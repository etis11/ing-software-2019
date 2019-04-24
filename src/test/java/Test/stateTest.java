package Test;


import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class stateTest {

    private State state;
    private Player p;

    @BeforeEach
    private void init(){
        p = new Player("TestName");
    }

    @Test
    public void EndTurnStateTest(){

        state = new EndTurn();


        assertTrue(state instanceof EndTurn, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

    }

    @Test
    public void normalActionStateTest(){

        state = new EndTurn();
        state.nextState(p, "");
        state = p.getState();

        assertTrue(state instanceof NormalAction, "ERROR of Instance");

        assertTrue(state.tryToRun(), "ERROR has to be allowed to run");

        assertTrue(state.tryToPickUp(), "ERROR has to be allowed to pick up");

        assertTrue(state.tryToShoot(), "ERROR has to be allowed to shoot");

        assertTrue(state.tryToUsePowerUp(), "ERROR has to be allowed to use power up");

        state.nextState(p,"run");
        assertTrue(p.getState() instanceof Run, "ERROR not in run state" );

        state = p.getState();

        state.nextState(p,"");

        state = p.getState();

        state.nextState(p,"shoot");
        assertTrue(p.getState() instanceof Shoot, "ERROR not in shoot state" );

        state = p.getState();

        state.nextState(p,"");

        state = p.getState();

        state.nextState(p,"pickUp");
        assertTrue(p.getState() instanceof PickUp, "ERROR not in pick up state" );

        state = p.getState();

        state.nextState(p,"");

        state = p.getState();

        state.nextState(p,"reload");
        assertTrue(p.getState() instanceof Reload, "ERROR not in shoot state" );


    }

    @Test
    public void moreActionStateTest(){
        state = new EndTurn();
        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
        state.nextState(p, "");

        state = p.getState();

//        assertTrue(state instanceof MoreAction, "ERROR of Instance");
//
//        assertTrue(state.tryToRun(), "ERROR has to be allowed to run");
//
//        assertTrue(state.tryToPickUp(), "ERROR has to be allowed to pick up");
//
//        assertTrue(state.tryToShoot(), "ERROR has to be allowed to shoot");
//
//        assertTrue(state.tryToUsePowerUp(), "ERROR has to be allowed to use power up");
//
//        state.nextState(p,"run");
//        assertTrue(p.getState() instanceof Run, "ERROR not in run state" );

//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"shoot");
//        assertTrue(p.getState() instanceof Shoot, "ERROR not in shoot state" );
//
//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"pickUp");
//        assertTrue(p.getState() instanceof PickUpPlus, "ERROR not in pick up state" );
//
//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"reload");
//        assertTrue(p.getState() instanceof Reload, "ERROR not in shoot state" );
    }

    @Test
    public void mostActionStateTest(){
        state = new EndTurn();
        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 6, 0, false, false));
        state.nextState(p, "");

//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance");
//        state = p.getState();
//
//        assertTrue(state.tryToRun(), "ERROR has to be allowed to run");
//
//        assertTrue(state.tryToPickUp(), "ERROR has to be allowed to pick up");
//
//        assertTrue(state.tryToShoot(), "ERROR has to be allowed to shoot");
//
//        assertTrue(state.tryToUsePowerUp(), "ERROR has to be allowed to use power up");
//
//        state.nextState(p,"run");
//        assertTrue(p.getState() instanceof Run, "ERROR not in run state" );
//
//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"shoot");
//        assertTrue(p.getState() instanceof ShootPlus, "ERROR not in shoot state" );
//
//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"pickUp");
//        assertTrue(p.getState() instanceof PickUpPlus, "ERROR not in pick up state" );
//
//        state = p.getState();
//
//        state.nextState(p,"");
//
//        state = p.getState();
//
//        state.nextState(p,"reload");
//        assertTrue(p.getState() instanceof Reload, "ERROR not in shoot state" );
    }

    @Test
    public void reloadStateTest(){

        state = new Reload();

        assertTrue(state instanceof Reload, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof EndTurn, "ERROR of Instance (Reload has to go to EndTurn)");

    }

    @Test
    public void deadStateTest(){

        state = new Dead();

        assertTrue(state instanceof Dead, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof EndTurn, "ERROR of Instance (Dead has to go to EndTurn)");

    }

    @Test
    public void overkilledTest(){

        state = new OverKilled();

        assertTrue(state instanceof OverKilled, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof EndTurn, "ERROR of Instance (Overkilled has to go to EndTurn)");

    }

    @Test
    public void runTest(){

        state = new Run();

        assertTrue(state instanceof Run, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof NormalAction, "ERROR of Instance (0 damage so has to go to NormalAction)");

//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MoreAction, "ERROR of Instance (3 damage so has to go to MoreAction)");
//
//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance (6 damage so has to go to MostAction)");

    }

    @Test
    public void shootTest(){

        state = new Shoot();

        assertTrue(state instanceof Shoot, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof NormalAction, "ERROR of Instance (0 damage so has to go to NormalAction)");

//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MoreAction, "ERROR of Instance (3 damage so has to go to MoreAction)");
//
//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance (6 damage so has to go to MostAction)");

    }

    @Test
    public void pickUpTest(){

        state = new PickUp();

        assertTrue(state instanceof PickUp, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

        state.nextState(p, "");
        assertTrue(p.getState() instanceof NormalAction, "ERROR of Instance (0 damage so has to go to NormalAction)");

//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MoreAction, "ERROR of Instance (3 damage so has to go to MoreAction)");
//
//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance (6 damage so has to go to MostAction)");

    }

    @Test
    public void pickUpPlusTest(){

        state = new PickUpPlus();

        assertTrue(state instanceof PickUpPlus, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MoreAction, "ERROR of Instance (3 damage so has to go to MoreAction)");
//
//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 3, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance (0 damage so has to go to MostAction)");

    }

    @Test
    public void shootPlusTest(){

        state = new ShootPlus();

        assertTrue(state instanceof ShootPlus, "ERROR of Instance");

        assertFalse(state.tryToRun(), "ERROR not allowed to run");

        assertFalse(state.tryToPickUp(), "ERROR not allowed to pick up");

        assertFalse(state.tryToShoot(), "ERROR not allowed to shoot");

        assertFalse(state.tryToUsePowerUp(), "ERROR not allowed to use power up");

//        p.getPlayerBoard().calculateDamage(new DamageTransporter(p, null, 6, 0, false, false));
//        state.nextState(p, "");
//        assertTrue(p.getState() instanceof MostAction, "ERROR of Instance (0 damage so has to go to MostAction)");

    }

    @Test
    public void actotionToEnd(){
        state = new EndTurn();

        state.nextState(p,"");

        state = p.getState();

        state.nextState(p,"endTurn");
        assertTrue(p.getState() instanceof EndTurn, "ERROR not in end turn state" );
    }
}
