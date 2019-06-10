package model.clientModel;

import model.BloodToken;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SemplifiedPlayerBoard {
    private List<BloodToken> damageTokens;
    private List<BloodToken> marksTokens;
    private List<Integer> killValue;
    private int numBlueAmmo;
    private int numRedAmmo;
    private int numYellowAmmo;

    public List<BloodToken> getDamageTokens() {
        return damageTokens;
    }

    public void setDamageTokens(BloodToken[] damageTokens) {
        this.damageTokens = new LinkedList<>(Arrays.asList(damageTokens));
    }

    public List<BloodToken> getMarksTokens() {
        return marksTokens;
    }

    public void setMarksTokens(BloodToken[] marksTokens) {
        this.marksTokens = new LinkedList<>(Arrays.asList(marksTokens));
    }

    public List<Integer> getKillValue() {
        return killValue;
    }

    public void setKillValue(Integer[] killValue) {
        this.killValue = new LinkedList<>(Arrays.asList(killValue));
    }

    public int getNumBlueAmmo() {
        return numBlueAmmo;
    }

    public void setNumBlueAmmo(int numBlueAmmo) {
        this.numBlueAmmo = numBlueAmmo;
    }

    public int getNumRedAmmo() {
        return numRedAmmo;
    }

    public void setNumRedAmmo(int numRedAmmo) {
        this.numRedAmmo = numRedAmmo;
    }

    public int getNumYellowAmmo() {
        return numYellowAmmo;
    }

    public void setNumYellowAmmo(int numYellowAmmo) {
        this.numYellowAmmo = numYellowAmmo;
    }
}
