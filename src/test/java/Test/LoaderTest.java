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
    public void testFilledAfterConctructed() {

        loader = new Loader();

        assertTrue(!loader.isFullBlue(), "ERRORE Munizioni blu piene");

        assertTrue(!loader.isFullRed(), "ERRORE Munizioni rosse piene");

        assertTrue(!loader.isFullYellow(), "ERRORE Munizioni gialle piene");

    }

    @Test
    public void testAskingReload(){

        loader = new Loader();

        int ammoToAsk = 1;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.getNumBlueAmmo() == 1+ ammoToAsk, "ERRORE Munizioni blu caricate");

        assertTrue(loader.getNumRedAmmo() == 1+ ammoToAsk, "ERRORE Munizioni rosse caricate");

        assertTrue(loader.getNumYellowAmmo() == 1+ ammoToAsk, "ERRORE Munizioni gialle caricate");

    }

    @Test
    public void testAskingReloadOverMaxAmmo(){

        loader = new Loader();

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.getNumBlueAmmo() == 1+ ammoToAsk, "ERRORE Munizioni blu caricate");

        assertTrue(loader.getNumRedAmmo() == 1+ ammoToAsk, "ERRORE Munizioni rosse caricate");

        assertTrue(loader.getNumYellowAmmo() == 1+ ammoToAsk, "ERRORE Munizioni gialle caricate");

    }

    @Test
    public void testFull(){

        loader = new Loader();

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.isFullBlue(), "ERRORE Munizioni blu non piene");

        assertTrue(loader.isFullRed(), "ERRORE Munizioni rosse non piene");

        assertTrue(loader.isFullYellow(), "ERRORE Munizioni gialle non piene");

    }

    @Test
    public void testPutToAmmo(){
        loader = new Loader();

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);
        
        loader.ammoToPool(ammoToAsk, ammoToAsk, ammoToAsk );

        assertTrue(loader.getNumBlueAmmo() == 0, "ERRORE Munizioni blu non svuotate");

        assertTrue(loader.getNumRedAmmo() == 0, "ERRORE Munizioni rosse svuotate");

        assertTrue(loader.getNumYellowAmmo() == 0, "ERRORE Munizioni gialle svuotate");
    }


}
