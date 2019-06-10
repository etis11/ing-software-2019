package model.clientModel;

import model.SemplifiedBloodToken;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SemplifiedPlayerBoard {
    private List<SemplifiedBloodToken> damageTokens;
    private List<SemplifiedBloodToken> marksTokens;
    private List<Integer> killValue;
    private int numBlueAmmo;
    private int numRedAmmo;
    private int numYellowAmmo;

    public List<SemplifiedBloodToken> getDamageTokens() {
        return damageTokens;
    }

    public void setDamageTokens(SemplifiedBloodToken[] damageTokens) {
        this.damageTokens = new LinkedList<>(Arrays.asList(damageTokens));
    }

    public List<SemplifiedBloodToken> getMarksTokens() {
        return marksTokens;
    }

    public void setMarksTokens(SemplifiedBloodToken[] marksTokens) {
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
