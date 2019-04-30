package model;


import java.util.List;

/**
 * represent player state during the game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class State {
    private final int MAX_POSSIBLE_STEPS;
    private final String NAME;
    private Player owner;
    private boolean normalAction;
    private boolean moreAction;
    private boolean mostAction;
    private boolean run;
    private boolean pickUp;
    private boolean shoot;
    private boolean usePowerUp;
    private boolean dead;
    private boolean overKilled;
    private List<State> possibleNextState;

    public State(String NAME, int MAX_POSSIBLE_STEPS,  Player owner, boolean normalAction, boolean moreAction, boolean mostAction, boolean run, boolean pickUp, boolean shoot, boolean usePowerUp, boolean dead, boolean overKilled, List<State> possibleNextState) {
        this.NAME = NAME;
        this.MAX_POSSIBLE_STEPS = MAX_POSSIBLE_STEPS;
        this.owner = owner;
        this.normalAction = normalAction;
        this.moreAction = moreAction;
        this.mostAction = mostAction;
        this.run = run;
        this.pickUp = pickUp;
        this.shoot = shoot;
        this.usePowerUp = usePowerUp;
        this.dead = dead;
        this.overKilled = overKilled;
        this.possibleNextState = possibleNextState;
    }

    public int getMAX_POSSIBLE_STEPS() {
        return MAX_POSSIBLE_STEPS;
    }

    public String getNAME(){
        return NAME;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isNormalAction() {
        return normalAction;
    }

    public boolean isMoreAction() {
        return moreAction;
    }

    public boolean isMostAction() {
        return mostAction;
    }

    public boolean canRun() {
        return run;
    }

    public boolean canPickUp() {
        return pickUp;
    }

    public boolean canShoot() {
        return shoot;
    }

    public boolean canUsePowerUp() {
        return usePowerUp;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isOverKilled() {
        return overKilled;
    }

    public List<State> getPossibleNextStates() {
        return possibleNextState;
    }

    public void nextState(String state){

    }
}
