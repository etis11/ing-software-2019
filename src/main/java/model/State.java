package model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represent player state during the game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class State {
    /**
     * MAX_POSSIBLE_STEPS is the number of steps a player can do in this state
     */
    private final int MAX_POSSIBLE_STEPS;

    /**
     * NAME identify the state
     */
    private final String NAME;

    /**
     * player who owns the instance of this state
     */
    private final Player owner;

    /**
     * normalAction identify if the player are in a state where he has to choose what to do
     */
    private final boolean normalAction;

    /**
     * normalAction identify if the player are in a state where he has to choose what to do and he has more than 3 damage
     */
    private final boolean moreAction;

    /**
     * normalAction identify if the player are in a state where he has to choose what to do and he has more than 6 damage
     */
    private final boolean mostAction;

    /**
     * run identify if the owner of the state can run of MAX_POSSIBLE_STEPS
     */
    private final boolean run;

    /**
     * pickUp identify if the owner of the state can pickUp something
     */
    private final boolean pickUp;

    /**
     * shoot identify if the owner of the state can shoot
     */
    private final boolean shoot;

    /**
     * usePowerUp identify if the owner of the state can use a powerUp
     */
    private final boolean usePowerUp;

    /**
     * dead represent if the owner of the state is dead or not
     */
    private final boolean dead;

    /**
     * overKilled represent if the owner of the state is dead or not
     */
    private final boolean overKilled;

    /**
     * possibleNextState contains a reference to the state which the owner can move from current state
     */
    private final Map<String, State> possibleNextState;

    /**
     * remainingSteps are the number of steps
     */
    private int remainingSteps;

    public State(String NAME, int MAX_POSSIBLE_STEPS,  Player owner, boolean normalAction, boolean moreAction, boolean mostAction, boolean run, boolean pickUp, boolean shoot, boolean usePowerUp, boolean dead, boolean overKilled, Map<String, State> possibleNextState) {
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
        this.remainingSteps = MAX_POSSIBLE_STEPS;
    }

    /**
     * tells the max number of step a player can do in this state
     * @return number of steps for this state
     */
    public int getMAX_POSSIBLE_STEPS() {
        return MAX_POSSIBLE_STEPS;
    }

    /**
     * tells the name of the state
     * @return name of state
     */
    public String getNAME(){
        return NAME;
    }

    /**
     * tells the owner of the state
     * @return player who owns thw state
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * tells if the owner could choose what to do in this state
     * @return if is in a normal type of action choice
     */
    public boolean isNormalAction() {
        return normalAction;
    }

    /**
     * tells if the owner could choose what to do in this state
     * @return if is in a more type of action choice (more than 3 damage)
     */
    public boolean isMoreAction() {
        return moreAction;
    }

    /**
     * tells if the owner could choose what to do in this state
     * @return if is in a most type of action choice (more than 6 damage)
     */
    public boolean isMostAction() {
        return mostAction;
    }

    /**
     * tells if the owner can run in this state
     * @return if the owner can run in this state
     */
    public boolean canRun() {
        return run;
    }

    /**
     * tells if the owner can pickUp in this state
     * @return if the owner can pickUp in this state
     */
    public boolean canPickUp() {
        return pickUp;
    }

    /**
     * tells if the owner can shoot in this state
     * @return if the owner can shoot in this state
     */
    public boolean canShoot() {
        return shoot;
    }

    /**
     * tells if the owner can use a powerUp in this state
     * @return if the owner can use a powerUp in this state
     */
    public boolean canUsePowerUp() {
        return usePowerUp;
    }

    /**
     * tells if the owner is dead
     * @return if the owner is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * tells if the owner is overkilled
     * @return if the owner is overkilled
     */
    public boolean isOverKilled() {
        return overKilled;
    }

    /**
     * tells which are reachable state from the current
     * @return a map of reachable state from the current
     */
    public Map<String, State> getPossibleNextStates() {
        return possibleNextState;
    }

    /**
     * tells how many steps the owner still can do
     * @return number of step the owner still can do
     */
    public int getRemainingSteps(){
        return remainingSteps;
    }

    /**
     * reset the number of remaining steps
     */
    public void resetRemainingSteps(){
        remainingSteps = MAX_POSSIBLE_STEPS;
    }

    /**
     * decrement the number of remaining steps by a number given
     * @param stepsToDecrement number of step to decrement remaining steps
     */
    public void decrementReaminingSteps(int stepsToDecrement){
        this.remainingSteps -= stepsToDecrement;
    }

    /**
     * sets the nextState for the player by a given string
     * @param state string containing the wanted nextState
     */
    public void nextState(String state){
        if (state == null) throw new IllegalArgumentException();
        if (!possibleNextState.containsKey(state)) throw  new IllegalStateException("Error: from "+getNAME()+" state you can't go to "+state+" state");
        owner.setState(possibleNextState.get(state));
    }
}
