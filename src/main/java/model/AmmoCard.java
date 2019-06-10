package model;

/**
 * AmmoCard refers to the Ammunition Cards that are to be used during play for certain tasks
 * like Weapon reloading or as a way of paying for a WeaponCard. There are 36 cards and each
 * one of them contains either 3 colored ammo or 2 ammo plus one power up
 * */
public class AmmoCard {

    /**
     * numBlue is an integer that represents the number of Blue ammo in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numBlue;

    /**
     * numYellow is an integer that represents the number of Yellow ammo in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numYellow;

    /**
     * numRed is an integer that represents the number of Red ammo in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numRed;

    /**
     * drawPowerUp is a boolean to check whether there's a PowerUp effect in an AmmoCard or not
     * */
    private boolean drawPowerUp;

    /**
     * Constructor method that contains:
     * @param numBlue number of blue ammo x Ammo Card
     * @param numYellow number of Yellow ammo x Ammo Card
     * @param numRed number of Red ammo x AmmoCard
     * @param drawPowerUp Power up or not
     * */
    public AmmoCard(int numBlue, int numYellow, int numRed, boolean drawPowerUp) {
        this.numBlue = numBlue;
        this.numYellow = numYellow;
        this.numRed = numRed;
        this.drawPowerUp = drawPowerUp;
    }

    public AmmoCard(){
    }

    public AmmoCard(AmmoCard card){
        numBlue = card.getNumBlue();
        numRed = card.getNumRed();
        numYellow= card.getNumYellow();
        drawPowerUp= card.isDrawPowerUp();
    }

    /**Method that returns number of blue ammo in an Ammo Card
     * @return numBlue
     * */
    public int getNumBlue() {
        return numBlue;
    }

    /**Method that returns number of yellow ammo in an Ammo Card
     * @return numYellow
     * */
    public int getNumYellow() {
        return numYellow;
    }
    /**Method that returns number of red ammo in an Ammo Card
     * @return numRed
     * */
    public int getNumRed() {
        return numRed;
    }
    /**Method that returns a boolean depending if a power up will be used or not
     * */
    public boolean isDrawPowerUp() {
        return drawPowerUp;
    }

}