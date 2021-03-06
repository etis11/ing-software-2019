package model;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Loader represent player's loader and contains ammo available to reload weaponsCard, a Player can ask for ammo o give back to his AmmoPool
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Loader {
    /**
     * this is the player pool which contains used ad gettable ammo
     */
    private AmmoPool ammoPool;

    /**
     * blueLoader is a list which contains blue ammo for the player ready to use
     */
    private List<Ammo> blueLoader;
    /**
     * redLoader is a list which contains red ammo for the player ready to use
     */
    private List<Ammo> redLoader;
    /**
     * yellowLoader is a list which contains yellow ammo for the player ready to use
     */
    private List<Ammo> yellowLoader;

    /**
     * initialize loader with an ammo for each color
     */
    public Loader() {
        this.ammoPool = new AmmoPool();
        this.blueLoader = new LinkedList<>();
        this.redLoader = new LinkedList<>();
        this.yellowLoader = new LinkedList<>();

        this.blueLoader.add(new Ammo(Color.BLUE));
        this.redLoader.add(new Ammo(Color.RED));
        this.yellowLoader.add(new Ammo(Color.YELLOW));
    }


    /**
     * calculates if max blue player ammo are in the loader
     *
     * @return if the loader is full of blue ammo
     */
    public boolean isFullBlue() {
        return blueLoader.size() == AmmoPool.MAX_AMMO;
    }

    /**
     * calculates if max red player ammo are in the loader
     *
     * @return if the loader is full of red ammo
     */
    public boolean isFullRed() {
        return redLoader.size() == AmmoPool.MAX_AMMO;
    }

    /**
     * calculates if max yellow player ammo are in the loader
     *
     * @return if the loader is full of yellow ammo
     */
    public boolean isFullYellow() {
        return yellowLoader.size() == AmmoPool.MAX_AMMO;
    }

    /**
     * calculates blue ammo in the loader
     *
     * @return the number of blue ammo in the loader
     */
    public int getNumBlueAmmo() {
        return blueLoader.size();
    }

    /**
     * calculates red ammo in the loader
     *
     * @return the number of red ammo in the loader
     */
    public int getNumRedAmmo() {
        return redLoader.size();
    }

    /**
     * calculates yellow ammo in the loader
     *
     * @return the number of yellow ammo in the loader
     */
    public int getNumYellowAmmo() {
        return yellowLoader.size();
    }

    /**
     * adds to the blueLoader the given blue ammo
     *
     * @param blueAmmo List of blue ammo to put in the blueLoader
     */
    private void addBlueAmmo(List<Ammo> blueAmmo) {
        blueLoader.addAll(blueAmmo);
    }

    /**
     * adds to the redLoader the given red ammo
     *
     * @param redAmmo List of red ammo to put in the redLoader
     */
    private void addRedAmmo(List<Ammo> redAmmo) {
        redLoader.addAll(redAmmo);
    }

    /**
     * adds to the yellowLoader the given yellow ammo
     *
     * @param yellowAmmo List of blue ammo to put in the yellowLoader
     */
    private void addYellowAmmo(List<Ammo> yellowAmmo) {
        yellowLoader.addAll(yellowAmmo);
    }

    /**
     * asks to ammoPool ammo to reload, for example after drawing an ammoCard, and insert them in the appropriate loader
     *
     * @param blueAmmo   number of blue ammo requested
     * @param redAmmo    number of red ammo requested
     * @param yellowAmmo number of yellow ammo requested
     */
    public void askReload(int blueAmmo, int redAmmo, int yellowAmmo) {
        if (isFullBlue()) {
            blueAmmo = 0;
        } else if (blueLoader.size() + blueAmmo > 3) {
            blueAmmo = AmmoPool.MAX_AMMO - blueLoader.size();
        }
        addBlueAmmo(ammoPool.getBlueAmmo(blueAmmo));

        if (isFullRed()) {
            redAmmo = 0;
        } else if (redLoader.size() + redAmmo > 3) {
            redAmmo = AmmoPool.MAX_AMMO - redLoader.size();
        }
        addRedAmmo(ammoPool.getNumRedAmmo(redAmmo));

        if (isFullYellow()) {
            yellowAmmo = 0;
        } else if (yellowLoader.size() + yellowAmmo > 3) {
            yellowAmmo = AmmoPool.MAX_AMMO - yellowLoader.size();
        }
        addYellowAmmo(ammoPool.getYellowAmmo(yellowAmmo));
    }

    /**
     * puts used ammo back in the pool until they will be recharged in the loader
     *
     * @param blueAmmo   number of blue ammo to put in the pool
     * @param redAmmo    number of red ammo to put in the pool
     * @param yellowAmmo number of yellow ammo to put in the pool
     */
    public void ammoToPool(int blueAmmo, int redAmmo, int yellowAmmo) {
        List<Ammo> toReturn = new LinkedList<>();
        while (blueAmmo > 0) {
            toReturn.add(blueLoader.remove(0));
            blueAmmo--;
        }

        while (redAmmo > 0) {
            toReturn.add(redLoader.remove(0));
            redAmmo--;
        }

        while (yellowAmmo > 0) {
            toReturn.add(yellowLoader.remove(0));
            yellowAmmo--;
        }

        ammoPool.insertAmmo(toReturn);
    }

    /**
     * Method used to check whether or not the player can afford to pickup the weapon or not
     * @param cost is the list of the cost of a weaponCard
     * @return true if the ammos the player has are more or equal to the cost of the weaponCard
     */
    public boolean canPay(List<String> cost){
        int y = getNumYellowAmmo();
        int b = getNumBlueAmmo();
        int r = getNumRedAmmo();
        for (String str: cost){
            if(str.equals("red")){
                r--;
            }else if(str.equals("blue")){
                b--;
            }else if (str.equals("yellow")){
                y--;
            }else{
                throw new RuntimeException("Invalid cost");
            }
        }
        return y>=0 && b>= 0 && r>=0;
    }

    /**
     * Method to check whether the player can afford to pay the cost of the optional effects also or not
     * @param opt is a list of optional effects
     * @return true or false
     */
    public boolean canPayAll(List<OptionalEffect> opt){
        int y = getNumYellowAmmo();
        int b = getNumBlueAmmo();
        int r = getNumRedAmmo();
        for(OptionalEffect opts: opt ){
            for(String str: opts.getCost()){
                if(str.equals("red")){
                    r--;
                }else if(str.equals("blue")){
                    b--;
                }else if (str.equals("yellow")){
                    y--;
                }else{
                    throw new RuntimeException("Invalid cost");
                }
            }
        }
        return y>=0 && b>= 0 && r>=0;
    }

    /**
     * Method that is applied after the current player can afford to pay for the weapon.This method
     * detracts from the ammos he has, the cost for the certain ammo he is going to pickup or use
     * @param cost is list of costs of the weaponCard
     */
    public void pay(List<String> cost){
        int r =0;
        int y = 0;
        int b = 0;
        for(String str : cost) {
            if (str.equals("red")) {
                r++;
            } else if (str.equals("blue")) {
                b++;
            } else if (str.equals("yellow")) {
                y++;
            } else {
                throw new RuntimeException("Invalid cost");
            }
        }
        ammoToPool(b,r,y);
    }
}
