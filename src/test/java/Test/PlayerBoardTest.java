package Test;

import model.PlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerBoardTest {

    private PlayerBoard p;
    private int[] rightPoints = {8, 6, 4, 2, 1, 1, 1, 1, 1};


    @BeforeEach
    public void inizializePlayerBoard() {
        p = new PlayerBoard();
    }

    @Test
    void testConstructor() {
        assertNotNull(p.getLoader(), () -> "ERRORE the loader is null, but should be instantiated");
        assertEquals(0, p.getNumDamagePoints(), () -> "ERRORE, the player's damage is not 0.");
        assertEquals(0, p.getNumMarks(), () -> "ERRORE, the player has more than 0 marks.");
        assertEquals(rightPoints.length, p.getKillValue().size(), () -> "ERRORE, the poiny list has the wrong size.");

        for (int i = 0; i < rightPoints.length; i++) {
            if (rightPoints[i] != p.getKillValue().get(i)) {
                fail(() -> "The point list has been initializated in the wrong way");
            }
        }


    }
}
