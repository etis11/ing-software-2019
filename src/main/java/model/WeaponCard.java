package model;

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
    private TargetStrategy targetStrategy1;
    private TargetStrategy targetStrategy2;

    /**
     * Boolean used to check if a Weapon is loaded or not
     * */
    private boolean loaded;

    /**
     * Each WeaponCard should contain a name to be distinguished by other cards
     * */
    private String Name;

    /**
     * List containing optional effects for a Weapon Card
     * */
    private List<Effect> optionalEffects;



    public WeaponCard(){
        reloadCost = null;
        targetStrategy1 = null;
        targetStrategy2 = null;
        loaded = true;
        Name = "default";

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
            this.targetStrategy1 = null;
            this.targetStrategy2 = null;
            this.loaded = ref.loaded;
            this.Name = String.valueOf(ref.getName());
            this.reloadCost = null;
        }

    }

    /**
     * creates the instance of each weapon by given information
     * @param reloadCost cost to reload weapon
     * @param targetStrategy1 primary strategy to choose target
     * @param targetStrategy2 secondary strategy to choose target
     * @param NAME  name of the weapon
     * @param optionalEffects weapon optional effect usable by paying extra reload cost
     */
    public WeaponCard(List<String> reloadCost, TargetStrategy targetStrategy1, TargetStrategy targetStrategy2, String NAME, List<Effect> optionalEffects) {
        this.reloadCost = reloadCost;
        this.targetStrategy1 = targetStrategy1;
        this.targetStrategy2 = targetStrategy2;
        this.Name = NAME;
        this.optionalEffects = optionalEffects;
    }

    /**
     * Returns the cost for a WeaponCard effect to be used. It returns a string of colouredAmmos
     * */
    public List<String> getReloadCost() {
        return reloadCost;
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
    public String getName() {
        return Name;
    }

    /**
     * The following method returns list of Optional Effects to a certain cards. THere might be 0,1
     * or 2 optional effects based on the Weapon Card
     * */
    public List<Effect> getOptionalEffects() {
        return optionalEffects;
    }

    public void reload() {
    //TODO
    }

    /**
     * The following method is used to return the number of blue ammos that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammos
     * @return toReturn number of blue ammos needed to use the WeaponCard Effect
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
     * The following method is used to return the number of yellow ammos that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammos
     * @return toReturn number of yellow ammos needed to use the WeaponCard Effect
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
     * The following method is used to return the number of red ammos that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammos
     * @return toReturn number of red ammos needed to use the WeaponCard Effect
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
     * Method that checkeds whether or not a Weapon Card is loaded
     * @return loaded if Weapon is loaded or not
     * */
    public boolean isLoaded() {
        return loaded;
    }


    public void chooseEffect() {
    //TODO
    }

    public void getTargetStrategy1() {
    //TODO
    }

    public void getTargetStrategy2() {
    //TODO
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
        return w.getName().equals(this.getName());


    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}