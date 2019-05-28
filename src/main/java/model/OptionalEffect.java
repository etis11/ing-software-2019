package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OptionalEffect {



    private List<String> cost;
    private Map<String,Integer> additionalDamage;
    private Map<String,Integer> additionalMarks;
    private boolean activated;
    private boolean canMove;



    private int steps;
    private Player p;


    public Map<String, Integer> getAdditionalDamage() {
        return additionalDamage;
    }

    public Map<String, Integer> getAdditionalMarks() {
        return additionalMarks;
    }
    public boolean isActivated() {
        return activated;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    public List<String> getCost() {
        return new LinkedList<>(cost);
    }
    public int getSteps() {
        return steps;
    }

}
