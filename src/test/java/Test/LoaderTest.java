package Test;

import Model.Loader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {

    private Loader loader;

    @BeforeAll
    public void initInstance(){
        loader = new Loader();
    }

    @Test
    public void testConstructor() {

        assertTrue(loader instanceof Loader, "ERROR of Instance");

        assertTrue(loader.getNumBlueAmmo() == 1, "ERROR blue ammo constructed");

        assertTrue(loader.getNumRedAmmo() == 1, "ERROR blue ammo constructed");

        assertTrue(loader.getNumYellowAmmo() == 1, "ERROR blue ammo constructed");

    }

    @Test
    public void testFilledAfterConctructed() {

        assertTrue(!loader.isFullBlue(), "ERROR full blue ammo");

        assertTrue(!loader.isFullRed(), "ERROR full red ammo");

        assertTrue(!loader.isFullYellow(), "ERROR full yellow ammo");

    }

    @Test
    public void testAskingReload(){

        int ammoToAsk = 1;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.getNumBlueAmmo() == 1+ ammoToAsk, "ERROR wrong blue ammo loaded");

        assertTrue(loader.getNumRedAmmo() == 1+ ammoToAsk, "ERROR wrong red ammo loaded");

        assertTrue(loader.getNumYellowAmmo() == 1+ ammoToAsk, "ERROR wrong yellow ammo loaded");

    }

    @Test
    public void testAskingReloadOverMaxAmmo(){

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.getNumBlueAmmo() == 1+ ammoToAsk, "ERROR wrong blue ammo loaded");

        assertTrue(loader.getNumRedAmmo() == 1+ ammoToAsk, "ERROR wrong red ammo loaded");

        assertTrue(loader.getNumYellowAmmo() == 1+ ammoToAsk, "ERROR wrong yellow ammo loaded");

    }

    @Test
    public void testFull(){

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        assertTrue(loader.isFullBlue(), "ERROR blue ammo not full");

        assertTrue(loader.isFullRed(), "ERROR red ammo not full");

        assertTrue(loader.isFullYellow(), "ERROR yellow ammo not full");

    }

    @Test
    public void testPutToAmmo(){

        int ammoToAsk = 3;

        loader.askReload(ammoToAsk, ammoToAsk, ammoToAsk);

        loader.ammoToPool(ammoToAsk, ammoToAsk, ammoToAsk );

        assertTrue(loader.getNumBlueAmmo() == 0, "ERROR blue ammo not thrown");

        assertTrue(loader.getNumRedAmmo() == 0, "ERROR red ammo not thrown");

        assertTrue(loader.getNumYellowAmmo() == 0, "ERROR yellow ammo not thrown");
    }


}
