package Model;

import java.awt.*;
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
     * this is the constructor method, ance initialize loader of every color
     */
    public Loader(){
        this.ammoPool = new AmmoPool();
        this.blueLoader = new ArrayList<Ammo>();
        this.redLoader = new ArrayList<Ammo>();
        this.yellowLoader = new ArrayList<Ammo>();
        
        this.blueLoader.add(new Ammo(Color.BLUE));
        this.redLoader.add(new Ammo(Color.RED));
        this.yellowLoader.add(new Ammo(Color.YELLOW));
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

    /**
     * this method add to the blueLoader the given blue ammos
     * @param blueAmmo List of blue ammos to put in the blueLoader
     */
    private void addBlueAmmo(List<Ammo> blueAmmo){

        blueLoader.addAll(blueAmmo);
    }

    /**
     * this method add to the redLoader the given red ammos
     * @param redAmmo List of red ammos to put in the redLoader
     */
    private void addRedAmmo(List<Ammo> redAmmo){

        blueLoader.addAll(redAmmo);
    }

    /**
     * this method add to the yellowLoader the given yellow ammos
     * @param yellowAmmo List of blue ammos to put in the yellowLoader
     */
    private void addYellowAmmo(List<Ammo> yellowAmmo){

        blueLoader.addAll(yellowAmmo);
    }

    /**
     * this method ask to ammoPool ammos to reload, for example after drawing an ammoCard, and insert them in the appropriate loader
     * @param blueAmmo number of blue ammos requested
     * @param redAmmo numeber of red ammos requested
     * @param yellowAmmo number of yellow ammos requested
     */
    public void askReload(int blueAmmo, int redAmmo, int yellowAmmo){
        if (isFullBlue()){
            blueAmmo = 0;
        }
        else if (blueLoader.size()+blueAmmo > 3){
            blueAmmo = AmmoPool.MAX_AMMO - blueLoader.size();
        }
        addBlueAmmo(ammoPool.getNumBlueAmmos(blueAmmo));

        if (isFullRed()){
            redAmmo = 0;
        }
        else if (redLoader.size()+redAmmo > 3){
            redAmmo = AmmoPool.MAX_AMMO - redLoader.size();
        }
        addRedAmmo(ammoPool.getNumRedAmmos(redAmmo));

        if (isFullBlue()){
            yellowAmmo = 0;
        }
        else if (yellowLoader.size()+yellowAmmo > 3){
            yellowAmmo = AmmoPool.MAX_AMMO - yellowLoader.size();
        }
        addYellowAmmo(ammoPool.getNumYellowAmmos(yellowAmmo));
    }

    /**
     * this method put used ammos back in the pool until they will be recharged in the loader
     * @param blueAmmo number of blue ammos to put in the pool
     * @param redAmmo number of red ammos to put in the pool
     * @param yellowAmmo number of yellow ammos to put in the pool
     */
    public void ammoToPool(int blueAmmo, int redAmmo, int yellowAmmo){
        List<Ammo> toReturn = new ArrayList<Ammo>();
        while (blueAmmo > 0){
            toReturn.add(blueLoader.remove(0));
            blueAmmo--;
        }

        while (redAmmo > 0){
            toReturn.add(redLoader.remove(0));
            redAmmo--;
        }

        while (yellowAmmo > 0){
            toReturn.add(yellowLoader.remove(0));
            yellowAmmo--;
        }

        ammoPool.insertAmmos(toReturn);
    }
}
