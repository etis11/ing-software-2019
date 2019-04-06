package Test;

import Model.Loader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {

    private Loader loader;



    @Test
    public void testConstructor() {

        loader = new Loader();

        assertTrue(loader instanceof Loader, "ERRORE Istanza");

        assertTrue(loader.getNumBlueAmmo() == 1, "ERRORE Munizioni blu costruite");

        assertTrue(loader.getNumRedAmmo() == 1, "ERRORE Munizioni rosse costruite");

        assertTrue(loader.getNumYellowAmmo() == 1, "ERRORE Munizioni gialle costruite");

    }

    @Test
    public void testFilled() {

        loader = new Loader();

        assertTrue(!loader.isFullBlue(), "ERRORE Munizioni blu piene");

        assertTrue(!loader.isFullRed(), "ERRORE Munizioni rosse piene");

        assertTrue(!loader.isFullYellow(), "ERRORE Munizioni gialle piene");

    }
}
