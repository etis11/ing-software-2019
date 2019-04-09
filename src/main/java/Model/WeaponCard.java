package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeaponCard extends Card{

    private List<String> reloadCost;
    private ChooseTargetStrategy targetStrategy1;
    private ChooseTargetStrategy targetStrategy2;
    private boolean loaded;
    private String NAME;
    private List<Effect> optionalEffects;



    public WeaponCard(){
        reloadCost = null;
        targetStrategy1 = null;
        targetStrategy2 = null;
        loaded = true;
        NAME = "";
        reloadCost = null;

    }

    /**
     * This constructor creates an imperfect copy of a given weapon. This constructor is used when the weapon has to be copied
     * in order to be given to the client.
     * L'inglese non è un gran che e contiene un po' di porcherie il codice, poi ne parliamo. Mi serve per la funzione
     * getWeaponCards in player
     * @param ref weapon that  has to be copied.
     */
    public WeaponCard(WeaponCard ref){
        reloadCost = new LinkedList<>(ref.getReloadCost());
        targetStrategy1 = null;
        targetStrategy2 = null;
        loaded = ref.loaded;
        NAME = String.valueOf(ref.getNAME());
        reloadCost = null;
    }

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