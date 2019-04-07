package Model;

import java.awt.*;

public class PowerUpCard  extends Card{

    private Color color;

    public Color getColor() {
        return this.color;

    }

    public PowerUpType getPowerUp() {
        return this.PowerUpType;
    }


    enum PowerUpType {
        gunsight,
        kineticBeam,
        venomGranade,
        teleport;
    }
}
