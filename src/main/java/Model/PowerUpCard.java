package Model;

import java.awt.*;

public class PowerUpCard  extends Card{

    private Color color;
    private PowerUpType powerUpType ;

    public PowerUpCard(PowerUpCard p) {
        this.color = p.getColor();
        this.powerUpType = p.getPowerUpType();
    }

    public PowerUpCard(Color color, PowerUpType powerUpType) {
        this.color = color;
        this.powerUpType = powerUpType;
    }

    public Color getColor() {
        return this.color;

    }

    public void setColor(Color color) {
        this.color = color;
    }


    public PowerUpCard(Color color) {
        this.color = color;
    }


    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

}