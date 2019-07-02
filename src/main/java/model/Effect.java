package model;

import jsonparser.Exclude;

import java.io.File;
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
    private boolean canMoveShooter;
    private int numStepsShooter;
    private boolean canMoveTarget;
    private int numStepsTarget;
    private boolean moveTargetAndHitAll;
    private boolean alreadyMovedTarget;
    private boolean alreadyMovedShooter = false;
    //TODO da settare. Fare l'azione inversa dell'apply
    private int redDamage;
    private int blueDamage;
    private int greenDamage;
    private int redMarks;
    private int blueMarks;
    private int greenMarks;

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

    public Effect() {
        optionalEffects = new LinkedList<>();
    }

    public Map<String, Integer> getDamage() {
        return damage;
    }

    public void setDamage(Map<String, Integer> damage) {
        this.damage = damage;
    }

    public Map<String, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, Integer> marks) {
        this.marks = marks;
    }

    public boolean isCanMoveShooter() {
        return canMoveShooter;
    }

    public void setCanMoveShooter(boolean canMoveShooter) {
        this.canMoveShooter = canMoveShooter;
    }

    public boolean isCanMoveTarget() {
        return canMoveTarget;
    }

    public void setCanMoveTarget(boolean canMoveTarget) {
        this.canMoveTarget = canMoveTarget;
    }

    public void setMoveTargetAndHitAll(boolean moveTargetAndHitAll) {
        this.moveTargetAndHitAll = moveTargetAndHitAll;
    }

    public void setAlreadyMovedTarget(boolean alreadyMovedTarget) {
        this.alreadyMovedTarget = alreadyMovedTarget;
    }

    public void setAlreadyMovedShooter(boolean alreadyMovedShooter) {
        this.alreadyMovedShooter = alreadyMovedShooter;
    }

    public boolean isAlreadyMovedTarget(){
        return alreadyMovedTarget;
    }
    public boolean isAlreadyMovedShooter(){
        return alreadyMovedShooter;
    }

    public TargetStrategy getStrategy() {
        return strategy;
    }

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
            Map<String, Integer> additionalMarks = o.getAdditionalDamage();
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

    public void setOptionalEffects(List<OptionalEffect> optionalEffects) {
        this.optionalEffects = optionalEffects;
    }

    /**
     * tells if the player can move
     *
     * @return
     */
    public boolean canMoveShooter() {
        return canMoveShooter;
    }

    /**
     * num of steps the shooter can do
     *
     * @return
     */
    public int getNumStepsShooter() {
        return numStepsShooter;
    }

    public void setNumStepsShooter(int numStepsShooter) {
        this.numStepsShooter = numStepsShooter;
    }

    /**
     * if the effect can move a target
     *
     * @return
     */
    public boolean canMoveTarget() {
        return canMoveTarget;
    }


    /**
     * returns the number of the steps u can make do the target
     *
     * @return
     */
    public int getNumStepsTarget() {
        return numStepsTarget;
    }

    public void setNumStepsTarget(int numStepsTarget) {
        this.numStepsTarget = numStepsTarget;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

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
        stringBuilder.append(", redDamage :").append(redDamage).append("\n");
        stringBuilder.append(", blueDamage :").append(blueDamage).append("\n");
        stringBuilder.append(", greenDamage :").append(greenDamage).append("\n");
        stringBuilder.append(", redMarks :").append(redMarks).append("\n");
        stringBuilder.append(", blueMarks :").append(blueMarks).append("\n");
        stringBuilder.append(", greenMarks :").append(greenMarks).append("\n");
        stringBuilder.append(",\t optional Effects :").append(optionalEffects).append("\n");
        return stringBuilder.toString();
    }
}
