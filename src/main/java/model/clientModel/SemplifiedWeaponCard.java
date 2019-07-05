package model.clientModel;

import java.util.List;

/**
 * Sempnlified version of a weaponcard for the client. All the implementations field from the original card have been removed,
 * since the client should not care.
 */
public class SemplifiedWeaponCard {

    private String name;
    private List<String> cost;
    private boolean loaded;
    private List<SemplifiedEffect> baseEffect;
    private List<SemplifiedEffect> advancedEffect;
    private int redCost;
    private int blueCost;
    private int yellowCost;

    public int getRedCost() {
        return redCost;
    }

    public int getBlueCost() {
        return blueCost;
    }

    public int getYellowCost() {
        return yellowCost;
    }

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

    public void canAfford(){
        int red=0;
        int blue=0;
        int yellow=0;
        for(int i=0;i<getCost().size();i++){
            if(getCost().contains("red")){
                red++;
            }
            if(getCost().contains("blue")){
                blue++;
            }
            if(getCost().contains("yellow")){
                yellow++;
            }
           ;
        }
    redCost=red;
    blueCost=blue;
    yellowCost=yellow;}
}
