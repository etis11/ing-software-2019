package Model;

/**
 * AmmoCard refers to the Ammunition Cards that are to be used during play for certain tasks
 * like Weapon reloading or as a way of paying for a WeaponCard. There are 36 cards and each
 * one of them contains either 3 colored ammos or 2 ammos plus one powerup
 * */
public class AmmoCard extends Card {

    /**
     * numBlue is an integer that represents the number of Blue ammos in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numBlue;

    /**
     * numYellow is an integer that represents the number of Yellowammos in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numYellow;

    /**
     * numRed is an integer that represents the number of Red ammos in an AmmoCard.
     * Such number varies from 0 upto 3
     * */
    private int numRed;

    /**
     * drawPowerUp is a boolean to check whether there's a PowerUp effect in an AmmoCard or not
     * */
    private boolean drawPowerUp; //TODO define it

    /**
     * Constructor method that contains:
     * @param numBlue number of blue ammos x Ammo Card
     * @param numYellow number of Yellow ammos x Ammo Card
     * @param numRed number of Red ammos x AmmoCard
     * @param drawPowerUp Powerup or not
     * */
    public AmmoCard(int numBlue, int numYellow, int numRed, boolean drawPowerUp) {
        this.numBlue = numBlue;
        this.numYellow = numYellow;
        this.numRed = numRed;
        this.drawPowerUp = drawPowerUp;
    }

    public AmmoCard(){
    }

    /**Method that returns number of blue ammos in an Ammo Card
     * @return numBlue
     * */
    public int getNumBlue() {
        return numBlue;
    }

    /**Method that returns number of yellow ammos in an Ammo Card
     * @return numYellow
     * */
    public int getNumYellow() {
        return numYellow;
    }
    /**Method that returns number of red ammos in an Ammo Card
     * @return numRed
     * */
    public int getNumRed() {
        return numRed;
    }
    /**Method that returns a boolean depending if a powerup will be used or not
     * */
    public boolean isDrawPowerUp() {
        return drawPowerUp;
    }

}