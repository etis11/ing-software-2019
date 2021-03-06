package model;

import jsonparser.Exclude;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A effect of a weapon. Both base effects and advanced effects are made of this class, since there is no difference in
 * behaviour. An effect can have a cost, a strategy that defines the possible targets,  how much damage and marks does,
 * and the possibility of moving the player or some targets.
 */
public class Effect {

    /**
     * returns true or false based on whether the effect applies to all the
     * targets or not
     */
    private boolean isGlobal;
    /**
     * the cost of the effect
     */
    private List<String> cost;
    /**
     * Tells how the targets a re chosen
     */
     @Exclude
    private TargetStrategy strategy;

    /**
     * a map that correlates the color of the target with the damage that has to be dealt to him
     */
    private Map<String, Integer> damage;
    /**
     * a map that correlates color of the target with the given number of marks
     */
    private Map<String, Integer> marks;
    /**
     * the optional effect. Can be empty
     */
    private List<OptionalEffect> optionalEffects;

    /**
     * boolean that determines if the shooter can move or not
     */
    private boolean canMoveShooter;

    /**
     * If a shooter can move than this integer is needed to set how many steps
     * he can move
     */
    private int numStepsShooter;

    /**
     * boolean that determines if the target can move or not
     */
    private boolean canMoveTarget;

    /**
     * If a target can move(thanks to the effect of the weaponCard ,
     * than this integer is needed to set how many steps he can move
     */
    private int numStepsTarget;

    /**
     * This boolean returns true or false if the effect allows you or not
     * to move the chosen target for then hitting all
     */
    private boolean moveTargetAndHitAll;

    /**
     * Boolean that tells you whether the target has already moved or not
     */
    private boolean alreadyMovedTarget;

    /**
     * boolean that is false by default
     */
    private boolean alreadyMovedShooter = false;

    /**
     * Integer that sets the damage to be dealt to the first target
     */
    private int redDamage;
    /**
     * Integer that sets the damage to be dealt to the second target
     */
    private int blueDamage;
    /**
     * Integer that sets the damage to be dealt to the third target
     */
    private int greenDamage;

    /**
     * Integer that sets the marks to be dealt to the first target
     */
    private int redMarks;

    /**
     * Integer that sets the marks to be dealt to the second target
     */
    private int blueMarks;

    /**
     * Integer that sets the marks to be dealt to the third target
     */
    private int greenMarks;

    /**
     * Constructor of the class
     * @param cost is a list of colored ammos that is needed to us that effect
     * @param strategy is the strategy to be applied in case you use that effect
     */
    public Effect(List<String> cost, TargetStrategy strategy) {
        this.cost = cost;
        this.strategy = strategy;
        this.alreadyMovedShooter = false;
        damage = new HashMap<>();
        damage.put("red", 0);
        damage.put("blue", 0);
        damage.put("green", 0);
        marks = new HashMap<>();
        marks.put("red", 0);
        marks.put("blue", 0);
        marks.put("green", 0);
        optionalEffects = new LinkedList<>();
    }

    /**
     * Constructor for optionalEffects
     */
    public Effect() {
        optionalEffects = new LinkedList<>();
    }

    /**
     * Method to bind each damage to the red,blue,green damages
     * @return hashmap of damage
     */
    public Map<String, Integer> getDamage() {
        return damage;
    }

    /**
     * Sets damage for each target
     * @param damage is a hashmap that binds damage
     */
    public void setDamage(Map<String, Integer> damage) {
        this.damage = damage;
    }

    /**
     * Method to bind each value of marks to the red,blue,green damages
     * @return hashmap of marks
     */
    public Map<String, Integer> getMarks() {
        return marks;
    }
    /**
     * Sets marks for each target
     * @param marks is a hashmap that binds marks
     */
    public void setMarks(Map<String, Integer> marks) {
        this.marks = marks;
    }

    /**
     * Method that checks whether or not the shooter can move
     * @return true or false
     */
    public boolean isCanMoveShooter() {
        return canMoveShooter;
    }

    /**
     * Method that sets  if the shooter is able to move or not
     * @param canMoveShooter is a boolean
     */
    public void setCanMoveShooter(boolean canMoveShooter) {
        this.canMoveShooter = canMoveShooter;
    }

    /**
     * Method that checks whether or not the target can move
     * @return true or false
     */
    public boolean isCanMoveTarget() {
        return canMoveTarget;
    }

    /**
     * Method that sets  if the target is able to move or not
     * @param canMoveTarget is a boolean
     */
    public void setCanMoveTarget(boolean canMoveTarget) {
        this.canMoveTarget = canMoveTarget;
    }
    /**
     * Method that checks if you can move target and then hit all the targetd
     * @param moveTargetAndHitAll is a boolean
     */
    public void setMoveTargetAndHitAll(boolean moveTargetAndHitAll) {
        this.moveTargetAndHitAll = moveTargetAndHitAll;
    }
    /**
     * Method that checks whether or not the targets has already moved
     * @param alreadyMovedTarget returns true or false
     */
    public void setAlreadyMovedTarget(boolean alreadyMovedTarget) {
        this.alreadyMovedTarget = alreadyMovedTarget;
    }
    /**
     * Method that checks whether or not the targets has already moved
     * @param alreadyMovedShooter returns true or false
     */
    public void setAlreadyMovedShooter(boolean alreadyMovedShooter) {
        this.alreadyMovedShooter = alreadyMovedShooter;
    }
    /**
     * Method that returns true or false if the target has already moved or not
     * @return a boolean
     */
    public boolean isAlreadyMovedTarget(){
        return alreadyMovedTarget;
    }
    /**
     * Method that returns true or false if the shooter has already moved or not
     * @return a boolean
     */
    public boolean isAlreadyMovedShooter(){
        return alreadyMovedShooter;
    }
    /**
     * Method that gets the strategy applied to a certain weaponCard
     * @return name of the strategy applied
     */
    public TargetStrategy getStrategy() {
        return strategy;
    }
    /**
     * Sets the strategy of a weaponCard
     * @param strategy is the strategy set to a weaponCard
     */
    public void setStrategy(TargetStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Sets the damage and marks default value
     */
    public void setDeafultDamageAndMarks(){
        redDamage = damage.get("red");
        blueDamage = damage.get("blue");
        greenDamage = damage.get("green");
        redMarks = marks.get("red");
        blueMarks = marks.get("blue");
        greenMarks = marks.get("green");
    }

    /**
     * This method sums the damages and the marks of the optional effect in the current effect.
     */
    public void applyOptionalEffect(List<OptionalEffect> optionalEffects) {
        String[] colors = {"red", "blue", "green"};
        for (OptionalEffect o : optionalEffects) {
            Map<String, Integer> additionalDamage = o.getAdditionalDamage();
            Map<String, Integer> additionalMarks = o.getAdditionalMarks();
            for (String color : colors) {
                int dmg = this.damage.get(color);
                int addDmg = additionalDamage.get(color);
                int marks = this.marks.get(color);
                int addMarks = additionalMarks.get(color);
                this.damage.put(color, dmg + addDmg);
                this.marks.put(color, marks + addMarks);
            }
        }
    }

    public void applyTargetScope(String targetColor){
        int dmg = this.damage.get(targetColor);
        this.damage.put(targetColor, dmg + 1);
    }

    /**
     * Method that checks whether or not the targets or shooter has already moved.
     * This applies only when entering in an optional effect
     * @param optionalEffects is the list of optional effects binded to the weaponCard
     * @return a boolean
     */
    public boolean areOptionalAlreadyMoved(List<OptionalEffect> optionalEffects){
        if(optionalEffects != null && !optionalEffects.isEmpty()) {
            for (OptionalEffect opt : optionalEffects) {
                if (opt.isShooterAlreadyMoved() || opt.isTargetAlreadyMoved()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Reset the damage and the marks of the players
     */
    public void resetDmgAndMarks() {
        this.damage.put("red", redDamage);
        this.damage.put("blue", blueDamage);
        this.damage.put("green", greenDamage);
        this.marks.put("red", redMarks);
        this.marks.put("blue", blueMarks);
        this.marks.put("green", greenMarks);
    }

    /**
     * creates a damage transporter for the given target
     *
     * @param shooter the person that is shooting
     * @param target  the person that will receive the effect
     * @param color   the  corresponding color of the target on the card
     * @return a damage transporter
     */
    public DamageTransporter useEffect(Player shooter, Player target, String color) {
        int damages = damage.get(color);
        int marks = this.marks.get(color);
        return new DamageTransporter(target, shooter, damages, marks);
    }

    /**
     * @return the cost of the effect
     */
    public List<String> getCost() {
        return cost;
    }
    /**
     * Sets the cost of a chosen effect
     * @param cost
     */
    public void setCost(List<String> cost) {
        this.cost = cost;
    }

    /**
     * returns a copy of the optional effects
     *
     * @return
     */
    public List<OptionalEffect> getOptionalEffects() {
        return this.optionalEffects;
    }
    /**
     * Sets optional effects to a certain Effect
     * @param optionalEffects is a list of optional Effects
     */
    public void setOptionalEffects(List<OptionalEffect> optionalEffects) {
        this.optionalEffects = optionalEffects;
    }

    /**
     * tells if the player can move
     *
     * @return true or false
     */
    public boolean canMoveShooter() {
        return canMoveShooter;
    }

    /**
     * num of steps the shooter can do
     *
     * @return number of steps a shooter can move
     */
    public int getNumStepsShooter() {
        return numStepsShooter;
    }

    /**
     * Sets number of steps a shooter can make
     * @param numStepsShooter returns number of steps
     */
    public void setNumStepsShooter(int numStepsShooter) {
        this.numStepsShooter = numStepsShooter;
    }

    /**
     * if the effect can move a target
     *
     * @return a boolean
     */
    public boolean canMoveTarget() {
        return canMoveTarget;
    }


    /**
     * returns the number of the steps u can make do the target
     *
     * @return numbers of steps a chosen target can make
     */
    public int getNumStepsTarget() {
        return numStepsTarget;
    }

    /**
     * sets number of steps for a targets
     * @param numStepsTarget returns number of steps of a certain target
     */
    public void setNumStepsTarget(int numStepsTarget) {
        this.numStepsTarget = numStepsTarget;
    }

    /**
     * Method that returns the status of global,true or false if the effect can be applied to all or not
     * @return a boolean
     */
    public boolean isGlobal() {
        return isGlobal;
    }

    /**
     * Sets a boolean for the variable isGlobal
     * @param global returns true or false
     */
    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    /**
     * Useful method to return useful data for an Effect
     * @return useful data of an Effect
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ isGlobal :").append(isGlobal).append("\n");
        stringBuilder.append(", cost :").append(cost).append("\n");
        stringBuilder.append(", strategy :").append(strategy).append("\n");
        stringBuilder.append(", damage :").append(damage).append("\n");
        stringBuilder.append(", marks :").append(marks).append("\n");
        stringBuilder.append(", canMoveShooter :").append(canMoveShooter).append("\n");
        stringBuilder.append(", numStepsShooter :").append(numStepsShooter).append("\n");
        stringBuilder.append(", canMoveTarget :").append(canMoveTarget).append("\n");
        stringBuilder.append(", numStepsTarget :").append(numStepsTarget).append("\n");
        stringBuilder.append(", moveTargetAndHitAll :").append(moveTargetAndHitAll).append("\n");
//        stringBuilder.append(", redDamage :").append(redDamage).append("\n");
//        stringBuilder.append(", blueDamage :").append(blueDamage).append("\n");
//        stringBuilder.append(", greenDamage :").append(greenDamage).append("\n");
//        stringBuilder.append(", redMarks :").append(redMarks).append("\n");
//        stringBuilder.append(", blueMarks :").append(blueMarks).append("\n");
//        stringBuilder.append(", greenMarks :").append(greenMarks).append("\n");
        stringBuilder.append(",\t optional Effects :").append(optionalEffects).append("\n");
        return stringBuilder.toString();
    }
}
