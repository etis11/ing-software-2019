package Model;

import java.util.List;

public class WeaponCard extends Card{

    private List<String> reloadCost;
    private ChooseTargetStrategy targetStrategy1;
    private ChooseTargetStrategy targetStrategy2;
    private boolean loaded;
    private String NAME;
    private List<Effect> optionalEffects;

    public List<String> getReloadCost() {
        return reloadCost;
    }

    public void setReloadCost(List<String> reloadCost) {
        this.reloadCost = reloadCost;
    }

    public void setTargetStrategy1(ChooseTargetStrategy targetStrategy1) {
        this.targetStrategy1 = targetStrategy1;
    }

    public void setTargetStrategy2(ChooseTargetStrategy targetStrategy2) {
        this.targetStrategy2 = targetStrategy2;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public List<Effect> getOptionalEffects() {
        return optionalEffects;
    }

    public void setOptionalEffects(List<Effect> optionalEffects) {
        this.optionalEffects = optionalEffects;
    }

    public void reload() {

    }

    public int getBlueCost() {

    }

    public int getYellowCost() {

    }

    public int getRedCost() {

    }

    public boolean isLoaded() {

    }

    public String getName() {

    }

    public void chooseEffect() {

    }

    public void getTargetStrategy1() {

    }

    public void getTargetStrategy2() {

    }
}