package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represent all the optional effect of a card
 */
public class OptionalEffect {

    /**
     * cost of the weapon
     */
    private List<String> cost;
    /**
     * Additional damage that can be dealt using this optional
     */
    private Map<String, Integer> additionalDamage;
    /**
     * Additional marks that can be dealt
     */
    private Map<String, Integer> additionalMarks;
    /**
     * tells if the optional effect is currently used or not
     */
    private boolean activated;
    /**
     * true if the shooter can move during the shooting phase
     */
    private boolean canMoveShooter;
    /**
     * true if the target can be moved during the shooting phase
     */
    private boolean canMoveTarget;

    /**
     * num steps that the shooter can do
     */
    private int numStepsShooter;
    /**
     * num of stapes that can be used to move the target
     */
    private int numStepsTarget;
    /**
     * tells if the shooter already moved
     */
    private boolean shooterAlreadyMoved;
    /**
     * tells if the targets have been already moved
     */
    private boolean targetAlreadyMoved;

    /**
     * return the association between  player and damage that can be dealt
     * @return
     */
    public Map<String, Integer> getAdditionalDamage() {
        return new HashMap<>(additionalDamage);
    }

    /**
     * returns the association between player color and marks that can be dealt
     * @return
     */
    public Map<String, Integer> getAdditionalMarks() {
        return new HashMap<>(additionalMarks);
    }

    /**
     * true if the optional has been sued
     * @return
     */
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
