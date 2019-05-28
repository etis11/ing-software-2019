package model;


import javafx.scene.paint.Color;
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
     * MAX_AMMO represent max ammo for each color that can be taken from any loader
     */
    public static final int MAX_AMMO = 3;

    /**
     * redAmmo is a list which contains aviable red ammo for the player, not in the loader yet
     */
    private List<Ammo> redAmmo;
    /**
     * yellowAmmo is a list which contains aviable yellow ammo for the player, not in the loader yet
     */
    private List<Ammo> yellowAmmo;
    /**
     * blueAmmo is a list which contains aviable blue ammo for the player, not in the loader yet
     */
    private List<Ammo> blueAmmo;

    /**
     * initializes pool with two ammo for each color
     */
    public AmmoPool(){
        this.redAmmo = new LinkedList<>();
        this.yellowAmmo = new LinkedList<>();
        this.blueAmmo = new LinkedList<>();

        redAmmo.add(new Ammo(Color.RED));
        redAmmo.add(new Ammo(Color.RED));
        blueAmmo.add(new Ammo(Color.BLUE));
        blueAmmo.add(new Ammo(Color.BLUE));
        yellowAmmo.add(new Ammo(Color.YELLOW));
        yellowAmmo.add(new Ammo(Color.YELLOW));
    }

    /**
     * this method gives, if possible, blue ammo as requested by a parameter
     * @param ammoNum number of blue ammo requested
     * @return list of blue ammo requested
     */
    public List<Ammo> getBlueAmmo(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(blueAmmo.remove(0));
        }
        return toReturn;
    }

    /**
     * this method gives, if possible, yellow ammo as requested by a parameter
     * @param ammoNum number of yellow ammo requested
     * @return list of yellow ammo requested
     */
    public List<Ammo> getYellowAmmo(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(yellowAmmo.remove(0));
        }
        return toReturn;
    }

    /**
     * this method gives, if possible, red ammo as requested by a parameter
     * @param ammoNum number of red ammo requested
     * @return list of red ammo requested
     */
    public List<Ammo> getNumRedAmmo(int ammoNum){
        List<Ammo> toReturn= new LinkedList<>();
        for (int i = 0; i < ammoNum; i++){
            toReturn.add(redAmmo.remove(0));
        }
        return toReturn;
    }

    /**
     * this method insert used ammo in the pool
     * @param ammoToInsert List of ammo to insert in the pool
     */
    public void insertAmmo(List<Ammo> ammoToInsert){
        Ammo tempAmmo;
        while(!ammoToInsert.isEmpty()){
            tempAmmo = ammoToInsert.remove(0);
            if (tempAmmo.getColor()== Color.BLUE)
            {
                blueAmmo.add(tempAmmo);
            }
            else if (tempAmmo.getColor()== Color.RED){
                redAmmo.add(tempAmmo);
            }
            else if (tempAmmo.getColor() == Color.YELLOW){
                yellowAmmo.add(tempAmmo);
            }
        }
    }

}
