package Model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class rappresent ammos aviable for each player that they can take by an ammoCard
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class AmmoPool {

    /**
     * MAX_AMMO rappresent max ammos for each color that can be teken from any loader
     */
    public static final int MAX_AMMO = 3;

    /**
     * redAmmos is a list which contains aviable red ammos for the player, not in the loader yet
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
     * this is the constructor method, it initialize pool of every ammo color
     */
    public AmmoPool(){
        this.redAmmos = new ArrayList<Ammo>();
        this.yellowAmmos = new ArrayList<Ammo>();
        this.blueAmmos = new ArrayList<Ammo>();

        redAmmos.add(new Ammo(Color.RED));
        redAmmos.add(new Ammo(Color.RED));
        blueAmmos.add(new Ammo(Color.BLUE));
        blueAmmos.add(new Ammo(Color.BLUE));
        yellowAmmos.add(new Ammo(Color.YELLOW));
        yellowAmmos.add(new Ammo(Color.YELLOW));
    }

    /**
     * this method gives, if possible, blue ammos as requested by a parameter
     * @param ammoNum number of blue ammos requested
     * @return list of blue ammos requested
     */
    public List<Ammo> getNumBlueAmmos(int ammoNum){

        return null;
    }

    /**
     * this method gives, if possible, yellow ammos as requested by a parameter
     * @param ammoNum number of yellow ammos requested
     * @return list of yellow ammos requested
     */
    public List<Ammo> getNumYellowAmmos(int ammoNum){

        return null;
    }

    /**
     * this method gives, if possible, red ammos as requested by a parameter
     * @param ammoNum number of red ammos requested
     * @return list of red ammos requested
     */
    public List<Ammo> getNumRedAmmos(int ammoNum){

        return null;
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
            tempAmmo = null;
        }
    }

}
