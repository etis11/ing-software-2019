package Model;

import java.awt.*;

/**
 * this class rappresent ammos for weaponCard, they are contained in ammoPool and in Loader
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Ammo {

    /**
     * this rappresent ammo color
     */
    private Color color;

    /**
     * this is the constructor method
     */
    public Ammo(Color ammoColor){
        this.color = ammoColor;
    }

    public Color getColor(){
        return this.color;
    }
    //TODO
    //setters are necessary?
}
