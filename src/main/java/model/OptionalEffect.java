package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OptionalEffect {

    private List<String> cost;
    private Map<String, Integer> additionalDamage;
    private Map<String, Integer> additionalMarks;
    private boolean activated;
    private boolean canMoveShooter;
    private int shooterSteps;
    private boolean ShooterAlreadyMoved;


    public Map<String, Integer> getAdditionalDamage() {
        return new HashMap<>(additionalDamage);
    }

    public Map<String, Integer> getAdditionalMarks() {
        return new HashMap<>(additionalMarks);
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean canShooterMove() {
        return canMoveShooter;
    }

    public void activate(boolean activated) {
        this.activated = activated;
    }

    public List<String> getCost() {
        return new LinkedList<>(cost);
    }

    public int getShooterSteps() {
        return shooterSteps;
    }

    public boolean hasShooterAlreadyMoved() {
        return ShooterAlreadyMoved;
    }

    public void setShooterAlreadyMoved(boolean alreadyMoved) {
        this.ShooterAlreadyMoved = alreadyMoved;
    }
}
