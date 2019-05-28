package model;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A effect of a weapon. Both base effects and advanced effects are made of this class, since there is no difference in
 * behaviour. An effect can have a cost, a strategy that defines the possible targets,  how much damage and marks does,
 * and the possibility of moving the player or some targets.
 */
public abstract class Effect {
    /**
     * the cost of the effect
     */
    private List<String> cost;
    /**
     * Tells how the targets a re chosen
     */
    private TargetStrategy strategy ;

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

    //TODO da settare. Fare l'azione inversa dell'apply
    private int redDamage;
    private int blueDamage;
    private int yellowDamage;
    private int redMarks;
    private int blueMarks;
    private int yellowMarks;

    public Effect(List<String> cost, TargetStrategy strategy){
        this.cost = cost;
        this.strategy = strategy;
        damage = new HashMap<>();
        damage.put("red", 0);
        damage.put("blue", 0);
        damage.put("yellow", 0);
        marks = new HashMap<>();
        marks.put("red", 0);
        marks.put("blue", 0);
        marks.put("yellow", 0);
        optionalEffects = new LinkedList<>();
    }

    public TargetStrategy getStrategy() {
        return strategy;
    }

    /**
     * This method sums the damages and the marks of the optional effect in the current effect.
     */
    public void applyOptionalEffect(){
        String[] colors = {"red", "blue", "yellow"};
        for(OptionalEffect o: optionalEffects){
            if(o.isActivated()){
                Map<String, Integer> additionalDamage = o.getAdditionalDamage();
                Map<String, Integer> additionalMarks = o.getAdditionalDamage();
                for(String color: colors){
                    int dmg = this.damage.get(color);
                    int addDmg = additionalDamage.get(color);
                    int marks = this.marks.get(color);
                    int addMarks = additionalMarks.get(color);
                    this.damage.put(color, dmg+addDmg);
                    this.marks.put(color, marks +addMarks);
                }
            }
        }
    }

    /**
     * Reset the damage and the marks of the players
     */
    public void resetDmgAndMarks(){
        this.damage.put("red", redDamage);
        this.damage.put("blue", blueDamage);
        this.damage.put("yellow", yellowDamage);
    }

    /**
     * creates a damage transporter for the given target
     * @param shooter the person that is shooting
     * @param target the person that will receive the effect
     * @param color the  corresponding color of the target on the card
     * @return a damage transporter
     */
    public DamageTransporter useEffect(Player shooter, Player target, String color){
        int damages = damage.get(color);
        int marks = this.marks.get(color);
        return new DamageTransporter(target, shooter, damages, marks);
    }

    /**
     * @return the cost of the effect
     */
    public List<String> getCost() {
        return new LinkedList<>(cost);
    }

    /**
     * returns a copy of the optional effects
     * @return
     */
    public List<OptionalEffect> getOptionalEffects() {
        return new LinkedList<>(optionalEffects);
    }

    /**
     * tells if the player can move
     * @return
     */
    public boolean canMoveShooter(){
        return canMoveShooter;
    }

    /**
     * num of steps the shooter can do
     * @return
     */
    public int getNumStepsShooter(){
        return numStepsShooter;
    }

    /**
     * if the effect can move a target
     * @return
     */
    public boolean canMoveTarget(){
        return canMoveTarget;
    }

    /**
     * returns the number of the steps u can make do the target
     * @return
     */
    public int getNumStepsTarget(){
        return numStepsTarget;
    }



}
