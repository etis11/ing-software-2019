package model;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    }

    public TargetStrategy getStrategy() {
        return strategy;
    }

    public DamageTransporter useEffect(Player shooter, Player target, String color){
        int damages = damage.get(color);
        int marks = this.marks.get(color);
        return new DamageTransporter(target, shooter, damages, marks);
    }

    public List<String> getCost() {
        return new LinkedList<>(cost);
    }

}
