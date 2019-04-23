package model;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * AmmoPool represent ammo available for each player that they can take by an ammoCard, it can return ammo to a Loader
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AmmoPool {

    /**
     * MAX_AMMO represent max ammo for each color that can be teken from any loader
     */
    public static final int MAX_AMMO = 3;

    /**
     * redAmmos is a list which contains aviable red ammo for the player, not in the loader yet
     */
    private List<Ammo> redAmmos;
    /**
     * yellowAmmos is a list which contains aviable yellow ammos for the player, not in the loader yet
     */
    private List<Ammo> yellowAmmos;
    /**
     * blueAmmos is a list which contains aviable blue ammos for the player, not in the loader yet
     */
    private List<Ammo> blueAmmos;

    /**
     * initializes pool with two ammo for each color
     */
    public AmmoPool(){
        this.redAmmos = new LinkedList<>();
        this.yellowAmmos = new LinkedList<>();
        this.blueAmmos = new LinkedList<>();

        redAmmos.add(new Ammo(Color.RED));
        redAmmos.add(new Ammo(Color.RED));
        blueAmmos.add(new Ammo(Color.BLUE));
        blueAmmos.add(new Ammo(Color.BLUE));
        yellowAmmos.add(new Ammo(Color.YELLOW));
        yellowAmmos.add(new Ammo(Color.YELLOW));
    }

    /**
     * this method gives, if possible, blue ammos as requested by a parameter
     * @param ammoNum number of blue ammo requested
     * @return list of blue ammo requested
     */
    public List<Ammo> getBlueAmmos(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(blueAmmos.remove(0));
        }
        return toReturn;
    }

    /**
     * this method gives, if possible, yellow ammos as requested by a parameter
     * @param ammoNum number of yellow ammo requested
     * @return list of yellow ammo requested
     */
    public List<Ammo> getYellowAmmos(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(yellowAmmos.remove(0));
        }
        return toReturn;
    }

    /**
     * this method gives, if possible, red ammos as requested by a parameter
     * @param ammoNum number of red ammo requested
     * @return list of red ammo requested
     */
    public List<Ammo> getNumRedAmmos(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(redAmmos.remove(0));
        }
        return toReturn;
    }

    /**
     * this method insert used ammos in the pool
     * @param ammmoToInsert List of ammos to insert in the pool
     */
    public void insertAmmos(List<Ammo> ammmoToInsert){
        Ammo tempAmmo;
        while(!ammmoToInsert.isEmpty()){
            tempAmmo = ammmoToInsert.remove(0);
            if (tempAmmo.getColor()== Color.BLUE)
            {
                blueAmmos.add(tempAmmo);
            }
            else if (tempAmmo.getColor()== Color.RED){
                redAmmos.add(tempAmmo);
            }
            else if (tempAmmo.getColor() == Color.YELLOW){
                yellowAmmos.add(tempAmmo);
            }
        }
    }

}
