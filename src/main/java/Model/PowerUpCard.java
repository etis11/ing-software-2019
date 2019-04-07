package Model;

import java.awt.*;

public class PowerUpCard  extends Card{

    private Color color;

    public Color getColor() {
        return this.color;

    }
    public void setColor(Color color) {
        this.color = color;
    }

    public PowerUpCard(Color color) {
        this.color = color;
    }
    public PowerUpCard() {
    }

    public PowerUpType getPowerUp() {
        return this.PowerUpType;
    }

}