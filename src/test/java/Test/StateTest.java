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
        assertSame(1,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        //player.getState().nextState("NormalAction", player);
    }

    /*@Test
    public void normalActionTest(){
        assertEquals("NormalAction", player.getState().getName(), "ERROR: bad initialization");
        assertTrue(player.getState().canPickUp(), "ERROR: you can't pick");
        assertTrue(player.getState().canReload(), "ERROR: you can't reload");
        assertFalse(player.getState().canRun(), "ERROR: you can't run");
        assertTrue(player.getState().canUsePowerUp(), "ERROR: you can't run");
        assertTrue(player.getState().canShoot(), "ERROR: you can't shoot");
        assertFalse(player.getState().isDead(), "ERROR: he is still alive");
        assertFalse(player.getState().isOverKilled(), "ERROR: he is still alive");
        assertTrue(player.getState().isNormalAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMoreAction(), "ERROR: it's end turn");
        assertFalse(player.getState().isMostAction(), "ERROR: it's end turn");
        assertSame(0, player.getState().getMaxPossibleSteps(), "ERROR: steps error");
        assertSame(player.getState().getMaxPossibleSteps(), player.getState().getRemainingSteps(), "ERROR: steps initialization error");
        assertTrue(player.getState().getPossibleNextStates().containsKey("PickUp"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Shoot"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Run"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("Reload"), "ERROR: there are not a possible state");
        assertTrue(player.getState().getPossibleNextStates().containsKey("EndTurn"), "ERROR: there are not a possible state");
        assertSame(5,player.getState().getPossibleNextStates().size(), "ERROR: possibleNextState initialization error");
        player.getState().nextState("PickUp", player);

    }*/


}
