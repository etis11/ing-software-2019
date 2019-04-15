package Model;

public class AmmoCard extends Card {

    private int numBlue;
    private int numYellow;
    private int numRed;
    private boolean drawPowerUp;

    public AmmoCard(int numBlue, int numYellow, int numRed, boolean drawPowerUp) {
        this.numBlue = numBlue;
        this.numYellow = numYellow;
        this.numRed = numRed;
        this.drawPowerUp = drawPowerUp;
    }

    public AmmoCard(){
    }

    public int getNumBlue() {

        return numBlue;
    }

    public void setNumBlue(int numBlue) {
        this.numBlue = numBlue;
    }

    public int getNumYellow() {

        return numYellow;
    }

    public void setNumYellow(int numYellow) {
        this.numYellow = numYellow;
    }

    public void setNumRed(int numRed) {
        this.numRed = numRed;
    }

    public boolean isDrawPowerUp() {
        return drawPowerUp;
    }

    public void setDrawPowerUp(boolean drawPowerUp) {
        this.drawPowerUp = drawPowerUp;
    }

    public int getNumRed() {

        return numRed;
    }

    public boolean getDrawPowerUp() {

        return drawPowerUp;
    }
}