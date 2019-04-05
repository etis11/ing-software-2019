package Model;

import java.util.ArrayList;
import java.util.List;

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
    private AmmoPool ammoPool;

    /**
     * blueLoader is a list which contains blue ammos for the player ready to use
     */
    private List<Ammo> blueLoader;
    /**
     * redLoader is a list which contains red ammos for the player ready to use
     */
    private List<Ammo> redLoader;
    /**
     * yellowLoader is a list which contains yellow ammos for the player ready to use
     */
    private List<Ammo> yellowLoader;

    /**
     * this is the constructor method
     */
    public Loader(){
        this.ammoPool = new AmmoPool();
        this.blueLoader = new ArrayList<Ammo>();
        this.redLoader = new ArrayList<Ammo>();
        this.yellowLoader = new ArrayList<Ammo>();
        //TODO
        //inizializzazione loader???
    }


    /**
     * this method calculate if max blue player ammos are in the loader
     * @return if the loader is full of blue ammos
     */
    public boolean isFullBlue(){
        if(blueLoader.size()==AmmoPool.MAX_AMMO){
            return true;
        }
        return false;
    }

    /**
     * this method calculate if max red player ammos are in the loader
     * @return if the loader is full of red ammos
     */
    public boolean isFullRed(){
        if(redLoader.size()==AmmoPool.MAX_AMMO){
            return true;
        }
        return false;
    }

    /**
     * this method calculate if max yellow player ammos are in the loader
     * @return if the loader is full of yellow ammos
     */
    public boolean isFullYellow(){
        if(yellowLoader.size()==AmmoPool.MAX_AMMO){
            return true;
        }
        return false;
    }

    /**
     * this method calculate blue ammos in the loader
     * @return the number of blue ammos in the loader
     */
    public int getNumBlueAmmo(){

        return blueLoader.size();
    }

    /**
     * this method calculate red ammos in the loader
     * @return the number of red ammos in the loader
     */
    public int getNumRedAmmo(){

        return redLoader.size();
    }

    /**
     * this method calculate yellow ammos in the loader
     * @return the number of yellow ammos in the loader
     */
    public int getNumYellowAmmo(){

        return yellowLoader.size();
    }

    //TODO
    //method to insert ammos in the loader
}
