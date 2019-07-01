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
    private boolean canMoveTarget;

    private int numStepsShooter;
    private int numStepsTarget;
    private boolean shooterAlreadyMoved;
    private boolean targetAlreadyMoved;


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

    public boolean canTargetMove() {
        return canMoveTarget;
    }

    public void setCanMoveTarget(boolean canMoveTarget) {
        this.canMoveTarget = canMoveTarget;
    }

    public void activate(boolean activated) {
        this.activated = activated;
    }

    public List<String> getCost() {
        return new LinkedList<>(cost);
    }

    public int getNumStepsShooter() {
        return numStepsShooter;
    }

    public boolean hasShooterAlreadyMoved() {
        return shooterAlreadyMoved;
    }

    public int getNumStepsTarget(){
        return numStepsTarget;
    }

    public void setShooterAlreadyMoved(boolean alreadyMoved) {
        this.shooterAlreadyMoved = alreadyMoved;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(", cost :").append(cost).append("\n");
        stringBuilder.append(", additionalDamage :").append(additionalDamage).append("\n");
        stringBuilder.append(", additionalMarks :").append(additionalMarks).append("\n");
        stringBuilder.append(", activated :").append(activated).append("\n");
        stringBuilder.append(", canMoveShooter :").append(canMoveShooter).append("\n");
        stringBuilder.append(", numStepsShooter :").append(numStepsShooter).append("\n");
        stringBuilder.append(", canMoveTarget :").append(canMoveTarget).append("\n");
        stringBuilder.append(", numStepsTarget :").append(numStepsTarget).append("\n");
        stringBuilder.append(", shooterAlreadyMoved :").append(shooterAlreadyMoved).append("\n");
        return stringBuilder.toString();
    }

    public void setTargetAlreadyMoved( boolean alreadyMoved){
        this.targetAlreadyMoved =alreadyMoved;
    }

    public boolean isShooterAlreadyMoved(){
        return shooterAlreadyMoved;
    }

    public boolean isTargetAlreadyMoved(){
        return targetAlreadyMoved;
    }



}
