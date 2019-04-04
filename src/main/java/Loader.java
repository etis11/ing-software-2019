/**
 * this class rappresent player's loader and contains ammos avaible to reload weaponsCard
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Loader {
    /**
     * this is the player pool which contains used ad gettable ammos
     */
    AmmoPool ammoPool;

    //TODO
    //array di ammo per colore?

    public Loader(){
        ammoPool = new AmmoPool();
    }


    /**
     * this method calculate if max blue player ammos are in the loader
     * @return if the loader is full of blue ammos
     */
    public boolean isFullBlue(){

        return false;
    }

    /**
     * this method calculate if max red player ammos are in the loader
     * @return if the loader is full of red ammos
     */
    public boolean isFullRed(){

        return false;
    }

    /**
     * this method calculate if max yellow player ammos are in the loader
     * @return if the loader is full of yellow ammos
     */
    public boolean isFullYellow(){

        return false;
    }

    /**
     * this method calculate blue ammos in the loader
     * @return the number of blue ammos in the loader
     */
    public int getNumBlueAmmo(){

        return 0;
    }

    /**
     * this method calculate red ammos in the loader
     * @return the number of red ammos in the loader
     */
    public int getNumRedAmmo(){

        return 0;
    }

    /**
     * this method calculate yellow ammos in the loader
     * @return the number of yellow ammos in the loader
     */
    public int getNumYellowAmmo(){

        return 0;
    }
}
