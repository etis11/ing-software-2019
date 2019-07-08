package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import exceptions.InsufficientAmmoException;
import jsonparser.StrategyDeserializer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The following class is supposed to extend Card by adding another type of Cards known as WeaponCards
 * There are 21 WeaponCards each different from the other. These cards are to be used during Gameplay to
 * inflict damage to the adversary.
 */

public class WeaponCard {
    /**
     * Each WeaponCard should contain a name to be distinguished by other cards
     */
    @SerializedName("NAME")
    private String name;
    /**
     * List containing colorAmmo.
     */
    private List<String> reloadCost;
    /**
     * The first effect showed on the card. Cant be empty
     */
    private List<Effect> baseEffect;
    /**
     * The second effect showed on the card, can be empty
     */
    private List<Effect> advancedEffect;
    /**
     * Boolean used to check if a Weapon is loaded or not
     */
    private boolean loaded;

    /*
    Default constructor of our class
     */
    public WeaponCard() {
        this.reloadCost = new LinkedList<>();
        this.loaded = true;
        this.name = "default";
    }

    /**
     * This constructor creates an imperfect copy of a given weapon. This constructor is used when the weapon has to be copied
     * in order to be given to the client.
     *
     * @param ref weapon that  has to be copied.
     */
    public WeaponCard(WeaponCard ref) {
        super();
        if (ref != null) {
            this.baseEffect =ref.getBaseEffect();
            this.advancedEffect =ref.getAdvancedEffect();
            this.loaded = ref.loaded;
            this.name = String.valueOf(ref.getName());
            this.reloadCost = (ref.getReloadCost());
        }

    }

    /*
    Method returns list of base Effects(and advanced Effects considering the fact that both have the same structure
    @return list of effects
     */
    public List<Effect> getBaseEffect() {
        return baseEffect;
    }

    /*
    Method used to set a base effect
    @param baseEffect is a list of base effects
     */
    public void setBaseEffect(List<Effect> baseEffect) {
        this.baseEffect = baseEffect;
    }

    /**
     * Method that gets the advancedEffect
     * @return the advanced effect
     */
    public List<Effect> getAdvancedEffect() {
        return advancedEffect;
    }

    /**
     * set up the values of damage and marks for the wepaons
     */
    public void setUpEffects(){
        for(Effect effect: baseEffect){
            effect.setDeafultDamageAndMarks();
        }
        for(Effect effect: advancedEffect){
            effect.setDeafultDamageAndMarks();
        }
    }

    /**
Method used to set an advanced effect
@param advancedEffect is a list of base effects
 */
    public void setAdvancedEffect(List<Effect> advancedEffect) {
        this.advancedEffect = advancedEffect;
    }

    /**
     * Returns the cost for a WeaponCard effect to be used. It returns a string of colouredAmmo
     */
    public List<String> getReloadCost() {
        return new ArrayList<>(reloadCost);
    }

    /*
    Method neded to set the cost for our weaponcards
    @param reloadCost is a List of strings containing the needed ammos so that our card can be used
     */
    public void setReloadCost(List<String> reloadCost) {
        this.reloadCost = reloadCost;
    }

    /**
     * Method used to get the name of a WeaponCard
     */
    public String getName() {
        return name;
    }

    /**
    Method used to set for the Weapon card
    @param name is a String type needed to set up a name for our card
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Loads the weapon
     *
     * @param p player from which the ammo are taken
     * @throws InsufficientAmmoException The player has not enough ammo
     */
    public void reload(Player p) throws InsufficientAmmoException {
        if (p.canPay(getReloadCost())) {
            p.getPlayerBoard().getLoader().pay(getReloadCost());
            loaded = true;
        } else {
            throw new InsufficientAmmoException("you don't have ammo");
        }
    }
    /**
     * The following method is used to return the number of blue ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     *
     * @return toReturn number of blue ammo needed to use the WeaponCard Effect
     */
    public int getBlueCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s : reloadCost) {
            if (s.equals("Blue")) {
                toReturn++;
            }
        }
        return toReturn;
    }

    /**
     * The following method is used to return the number of yellow ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     *
     * @return toReturn number of yellow ammo needed to use the WeaponCard Effect
     */
    public int getYellowCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s : reloadCost) {
            if (s.equals("Yellow")) {
                toReturn++;
            }
        }
        return toReturn;
    }

    /**
     * The following method is used to return the number of red ammo that needed for the WeaponCard to be used
     * Each weaponCard has its own cost based on ammo
     *
     * @return toReturn number of red ammo needed to use the WeaponCard Effect
     */
    public int getRedCost() {
        int toReturn = 0;
        //TODO probabilmente si può usare java funzionale
        for (String s : reloadCost) {
            if (s.equals("Red")) {
                toReturn++;
            }
        }
        return toReturn;
    }

    /**
     * Method that checks whether or not a Weapon Card is loaded
     *
     * @return loaded if Weapon is loaded or not
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Returns a boolean in case the loader is loaded or not.
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Checks if the current player can use OptionalEffects or not
     * @param currentPlayer is the player whose turn is
     * @return a boolean
     */
    public boolean canOpt(Player currentPlayer){
        if(getBaseEffect().get(0).getOptionalEffects()!= null && !getBaseEffect().get(0).getOptionalEffects().isEmpty()){
            for(OptionalEffect opt: getBaseEffect().get(0).getOptionalEffects()){
                if(currentPlayer.canPay(opt.getCost())){
                    return  true;
                }
            }
        }
        return false;
    }

    /**
     * Gets cost of blues
     * @return reload cost of blues
     */
    public int getBluePickCost(){
        int toReturn = 0;
        if(reloadCost.size()>1){
            for(int i=1; i<reloadCost.size();i++){
                if(reloadCost.get(i).equals("blue")){
                    toReturn++;
                }
            }
        }
        return toReturn;
    }
    /**
     * Gets cost of reds
     * @return reload cost of reds
     */
    public int getRedPickCost(){
        int toReturn = 0;
        if(reloadCost.size()>1){
            for(int i=1; i<reloadCost.size();i++){
                if(reloadCost.get(i).equals("red")){
                    toReturn++;
                }
            }
        }
        return toReturn;
    }
    /**
     * Gets cost of yellows
     * @return reload cost of yellows
     */
    public int getYellowPickCost(){
        int toReturn = 0;
        if(reloadCost.size()>1){
            for(int i=1; i<reloadCost.size();i++){
                if(reloadCost.get(i).equals("yellow")){
                    toReturn++;
                }
            }
        }
        return toReturn;
    }

    /**
     * Two weaponsCard are equals if they have the same name
     *
     * @param o object that has to be testes
     * @return true if the weapons have the same name, false othwerwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!(o instanceof WeaponCard))
            return false;

        WeaponCard w = (WeaponCard) o;
        return w.getName().equals(this.getName());


    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*
    Default method needed to connect our pre-defined strings to their values
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WeapondCard{name: ").append("\n");
        stringBuilder.append(this.name).append("\n");
        stringBuilder.append(", reloadCost: ").append("\n");
        stringBuilder.append(reloadCost).append("\n");
        stringBuilder.append(", baseEffect: ").append("\n");
        stringBuilder.append(baseEffect).append("\n");
        stringBuilder.append(", advancedEffect: ").append("\n");
        stringBuilder.append(advancedEffect).append("\n");
        stringBuilder.append(", loaded: ").append("\n");
        stringBuilder.append(loaded).append("\n");
        stringBuilder.append("}").append("\n");
        return stringBuilder.toString();
    }

    /**
     * Method that gets all Weapons from the jSON file
     * @param jsonFile is the file that has all weaponCards with their jSON format
     * @param match is the running game
     * @return list of all weaponcards
     */
    public static List<WeaponCard> getWeaponsFromJson(InputStream jsonFile, Match match){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TargetStrategy.class, new StrategyDeserializer(match));
        Gson gson = gsonBuilder.create();

        WeaponCard[] weaponCards = gson.fromJson(new InputStreamReader(jsonFile), WeaponCard[].class);
        return new LinkedList<>(Arrays.asList(weaponCards));

    }
}