package model.clientModel;

import java.util.List;

public class SemplifiedWeaponCard {

    private String name;
    private List<String> cost;
    private boolean loaded;
    private List<SemplifiedEffect> baseEffect;
    private List<SemplifiedEffect> advancedEffect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCost() {
        return cost;
    }

    public void setCost(List<String> cost) {
        this.cost = cost;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public List<SemplifiedEffect> getBaseEffect() {
        return baseEffect;
    }

    public void setBaseEffect(List<SemplifiedEffect> baseEffect) {
        this.baseEffect = baseEffect;
    }

    public List<SemplifiedEffect> getAdvancedEffect() {
        return advancedEffect;
    }

    public void setAdvancedEffect(List<SemplifiedEffect> advancedEffect) {
        this.advancedEffect = advancedEffect;
    }
}
