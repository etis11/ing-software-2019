package Model;

import java.awt.*;

/**
 * Ammo represent ammo to reload weaponCard, they are contained in ammoPool and in Loader
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Ammo {

    /**
     * color is ammo color
     */
    private Color color;

    /**
     * creates ammo of color given
     *
     * @param ammoColor Color for the ammo
     */
    public Ammo(Color ammoColor){
        this.color = ammoColor;
    }


    /**
     * return color of the ammo
     *
     * @return color of the ammo
     */
    public Color getColor(){
        return this.color;
    }

}
