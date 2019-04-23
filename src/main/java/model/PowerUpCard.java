package model;

import java.awt.*;

public class PowerUpCard{

    /**
     * color is the color of the PowerUpCard, it is used to identify the first regen point or to pay ammo cost
     */
    private Color color;

    /**
     * powerUpType is the type a power up for the card, this is related to the effect of the card
     */
    private PowerUpType powerUpType ;

    /**
     * create a powerUpCard by a PowerUpCard given
     * @param p card to copy
     */
    public PowerUpCard(PowerUpCard p) {
        this.color = p.getColor();
        this.powerUpType = p.getPowerUpType();
    }


    /**
     * create powerUpCard by parameters given
     * @param color color of the powerUpCard
     * @param powerUpType type of powerUp
     */
    public PowerUpCard(Color color, PowerUpType powerUpType) {
        this.color = color;
        this.powerUpType = powerUpType;
    }

    /**
     * return the color of power up
     * @return color of powerUp
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * return the type of the powerUp
     * @return type of powerUp
     */
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

}