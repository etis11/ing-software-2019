package Test;

import model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static model.State.fromJson;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    private static Player player;

    @BeforeAll
    public static void init(){
        String path = "." + File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "stateMachine"+File.separatorChar+"stateMachine.json";
        player = new Player();
        player.setState(fromJson(path));
    }

    @Test
    public void initTest(){
        //TEST END TURN
        assertEquals("EndTurn", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can't pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can't run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't use powerUp");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Dead"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertSame(4,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("NormalAction", player);


        //TEST NORMAL ACTION
        assertEquals("NormalAction", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can run");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertTrue(player.getState().isNormalAction(), "ERROR: it's normal action");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's normal actio");
        assertFalse(player.getState().isMostAction(), "ERROR: it's normal actio");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("PickUp"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Shoot"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Run"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Reload"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(5,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("PickUp", player);

        //TEST PICKUP ACTION
        assertEquals("PickUp", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(1, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("NormalAction", player);
        player.getState().nextState("Run", player);

        //TEST RUN ACTION
        assertEquals("Run", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can't pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(3, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(3,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("NormalAction", player);
        player.getState().nextState("Shoot", player);

        //TEST SHOOT ACTION
        assertEquals("Shoot", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can' t run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can use powerup");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertSame(2,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("NormalAction", player);

        //TEST RELOAD
        player.getState().nextState("Reload", player);
        assertEquals("Reload", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can' t run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("EndTurn", player);
        player.getState().nextState("MoreAction", player);

        //TEST MORE ACTION
        assertEquals("MoreAction", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can run");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's more action");
        assertTrue(player.getState().isMoreAction(), "ERROR: it's more action");
        assertFalse(player.getState().isMostAction(), "ERROR: it's more action");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("PickUp"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Shoot"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Run"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Reload"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(5,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("Run", player);

        //TEST MORE RUN ACTION
        assertEquals("Run", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can't pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(3, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(3,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MoreAction", player);
        player.getState().nextState("Shoot", player);

        //TEST MORE SHOOT ACTION
        assertEquals("Shoot", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can' t run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can use powerup");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertSame(2,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MoreAction", player);
//        System.out.println(player.getState().getPossibleNextStates());
        player.getState().nextState("PickUp", player);

        //TEST MORE PICKUP ACTION
        assertEquals("PickUpPlus", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(2, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(2,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MoreAction", player);

        //TEST MORE RELOAD
        player.getState().nextState("Reload", player);
        assertEquals("Reload", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can' t run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("EndTurn", player);
        player.getState().nextState("MostAction", player);

        //TEST MOST ACTION
        assertEquals("MostAction", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can run");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's most action");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's most action");
        assertTrue(player.getState().isMostAction(), "ERROR: it's most action");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("PickUp"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Shoot"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Run"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Reload"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(5,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("Run", player);

        //TEST MOST RUN ACTION
        assertEquals("Run", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can't pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(3, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("NormalAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(3,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MostAction", player);
        player.getState().nextState("Shoot", player);

        //TEST MOST SHOOT ACTION
        assertEquals("ShootPlus", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can' t run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can use powerup");
        assertTrue(player.getState().canShoot(), "ERROR: you can shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(1, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MostAction", player);
        player.getState().nextState("PickUp", player);

        //TEST MOST PICKUP ACTION
        assertEquals("PickUpPlus", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertTrue(player.getState().canRun(), "ERROR: you can run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(2, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MoreAction"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("MostAction"), "ERROR: there are not a possible state");
        assertSame(2,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("MostAction", player);

        //TEST MOST RELOAD
        player.getState().nextState("Reload", player);
        assertEquals("Reload", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can't pick");
        assertTrue(player.getState().canReload(), "ERROR: you can reload");
        assertTrue(player.getState().canRun(), "ERROR: you can't run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("EndTurn", player);

        //TEST DEAD E OVERKILL
        player.getState().nextState("Dead", player);
        assertEquals("Dead", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can't pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can't run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is dead");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is dead");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Overkilled"), "ERROR: there are not a possible state");
        assertSame(2,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("Overkilled", player);

        assertEquals("Overkilled", player.getState().getName(), "ERROR: bad initialization");
        assertFalse(player.getState().canPickUp(), "ERROR: you can pick");
        assertFalse(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can't run");
        assertFalse(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertFalse(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is overkilled");
        assertTrue(player.getState().isOverKilled(), "ERROR: he is overkilled");
        assertFalse(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("EndTurn", player);

        player.getState().nextState("Dead", player);
        player.getState().nextState("EndTurn", player);

        //TODO gestire la diminuzione mosse

    }


}
