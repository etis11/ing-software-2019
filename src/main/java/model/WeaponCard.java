package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The following class is supposed to extend Card by adding another type of Cards known as WeaponCards
 * There are 21 WeaponCards each different from the other. These cards are to be used during Gameplay to
 * inflict damage to the adversary.
 * */

public class WeaponCard{


    /**
     *List containing colorAmmo.
     */
    private List<String> reloadCost;

    private Effect baseEffect;
    private Effect advancedEffect;

    /**
     * Boolean used to check if a Weapon is loaded or not
     * */
    private boolean loaded;

    /**
     * Each WeaponCard should contain a name to be distinguished by other cards
     * */
    private String NAME;

    public WeaponCard(){
        this.reloadCost = null;
        this.baseEffect = null;
        this.advancedEffect = null;
        this.loaded = true;
        this.NAME = "default";

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
            this.baseEffect = null;
            this.advancedEffect = null;
            this.loaded = ref.loaded;
            this.NAME = String.valueOf(ref.getNAME());
            this.reloadCost = null;
        }

    }



    /**
     * Returns the cost for a WeaponCard effect to be used. It returns a string of colouredAmmo
     * */
    public List<String> getReloadCost() {
        //TODO si può forse migliorare
        return new ArrayList<>(reloadCost);
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

    public void reload() {
    //TODO
    }

    /**
     * The following method is used to return the number of blue ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     * @return toReturn number of blue ammo needed to use the WeaponCard Effect
     * */
    public int getBlueCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s:reloadCost){
            if (s.equals("Blue")){
                toReturn++;
            }
        }
        return toReturn;
    }
    /**
     * The following method is used to return the number of yellow ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     * @return toReturn number of yellow ammo needed to use the WeaponCard Effect
     * */
    public int getYellowCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s:reloadCost){
            if (s.equals("Yellow")){
                toReturn++;
            }
        }
        return toReturn;
    }
    /**
     * The following method is used to return the number of red ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     * @return toReturn number of red ammo needed to use the WeaponCard Effect
     * */
    public int getRedCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s:reloadCost){
            if (s.equals("Red")){
                toReturn++;
            }
        }
        return toReturn;
    }

    /**
     * Method that checks whether or not a Weapon Card is loaded
     * @return loaded if Weapon is loaded or not
     * */
    public boolean isLoaded() {
        return loaded;
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
        return w.getNAME().equals(this.getNAME());


    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}