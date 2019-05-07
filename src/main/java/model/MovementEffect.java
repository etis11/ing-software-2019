package model;

import java.util.List;

public class MovementEffect extends Effect {
    private int steps;
    private String movementType;
    public MovementEffect(int steps,String movementType,boolean isGlobal,boolean isOptional){
        super.setGlobal(isGlobal);
        super.setOptional(isOptional);
        this.steps=steps;
        this.movementType=movementType;
    }
    @Override
    public void useEffect(List<Player> p) {

    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
}
