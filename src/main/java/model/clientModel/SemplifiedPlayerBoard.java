package model.clientModel;

import model.BloodToken;
import model.Loader;

import java.util.ArrayList;
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
    private Loader loader;

    public SemplifiedPlayerBoard() {
        this.damageTokens = new LinkedList<>();
        this.marksTokens = new LinkedList<>();
        this.killValue = new LinkedList<>();
    }

    public List<SemplifiedBloodToken> getDamageTokens() {
        return damageTokens;
    }
    public int getNumDamagePoints() {
        return damageTokens.size();
    }
    public int getNumMarks() {
        return marksTokens.size();
    }
    public Loader getLoader() {
        return loader;
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
