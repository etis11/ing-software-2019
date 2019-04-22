package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The following class is supposed to extend Card by adding another type of Cards known as WeaponCards
 * There are 21 WeaponCards each different from the other. THese cards are to be used during Gameplay to
 * inflict damage to the adversary.
 * */

public class WeaponCard extends Card{


    /**
     *List containing colorAmmos.
     */
    private List<String> reloadCost;

    //TODO define the following two strategies
    private ChooseTargetStrategy targetStrategy1;
    private ChooseTargetStrategy targetStrategy2;

    /**
     * Boolean used to check if a Weapon is loaded or not
     * */
    private boolean loaded;

    /**
     * Each WeaponCard should contain a name to be distinguished by other cards
     * */
    private String NAME;

    /**
     * List containing optional effects for a Weapon Card
     * */
    private List<Effect> optionalEffects;



    public WeaponCard(){
        reloadCost = null;
        targetStrategy1 = null;
        targetStrategy2 = null;
        loaded = true;
        NAME = "default";
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
        super();
        if (ref!= null){
            targetStrategy1 = null;
            targetStrategy2 = null;
            loaded = ref.loaded;
            NAME = String.valueOf(ref.getNAME());
            reloadCost = null;
        }

    }

    /**
     * Returns the cost for a WeaponCard effect to be used. It returns a string of colouredAmmos
     * */
    public List<String> getReloadCost() {
        return reloadCost;
    }

    /**
     * Sets cost for each WeaponCard effect to be used.
     * */
    public void setReloadCost(List<String> reloadCost) {
        this.reloadCost = reloadCost;
    }
//TODO: define targetstrategy
    public void setTargetStrategy1(ChooseTargetStrategy targetStrategy1) {
        this.targetStrategy1 = targetStrategy1;
    }

    public void setTargetStrategy2(ChooseTargetStrategy targetStrategy2) {
        this.targetStrategy2 = targetStrategy2;
    }

 /**
  * Returns a boolean in case the loader is loaded or not.
  * */

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Method used to get the name of a WeaponCard
     * */
    public String getNAME() {
        return NAME;
    }
//TODO make sure if this setter is needed or not
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    /**
     * The followin method returns list of Optional Effects to a certain cards. THere might be 0,1
     * or 2 optional effects based on the Weapon Card
     * */
    public List<Effect> getOptionalEffects() {
        return optionalEffects;
    }
//TODO maybe not needed
    public void setOptionalEffects(List<Effect> optionalEffects) {
        this.optionalEffects = optionalEffects;
    }

    public void reload() {

    }

    public int getBlueCost() {
    //TODO
        return 0;
    }

    public int getYellowCost() {
        //TODO
        return 0;
    }

    public int getRedCost() {
        //TODO
        return 0;
    }

    public boolean isLoaded() {
    //TODO
        return false;
    }

    public String getName() {
    //TODO
        return null;
    }

    public void chooseEffect() {

    }

    public void getTargetStrategy1() {

    }

    public void getTargetStrategy2() {

    }

    /**
     * Two weaponsCard are equals if they have the same name
     * @param o object that has to be testes
     * @return true if the weapons have the same name, false othwerwise
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        else if (o == this) {
            return true;
        }
        else if (!(o instanceof WeaponCard))
            return false;

        WeaponCard w = (WeaponCard) o;
        return w.getNAME().equals(this.getName());


    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}