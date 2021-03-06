package model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import jsonparser.StateMachineDeserializer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * represent player state during the game
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class State {

    /**
     * LOGGER needed for debug and to avoid Sonar errors
     */
    private static final Logger LOGGER = Logger.getLogger(State.class.getName());

    /**
     * maxPossibleSteps is the number of steps a player can do in this state
     */
    private final int maxPossibleSteps;

    /**
     * name identify the state
     */
    private final String name;

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
     * run identify if the owner of the state can run of maxPossibleSteps
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
     * reload identify if the owner of the state can reload
     */
    private final boolean reload;

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
    private Map<String, State> possibleNextState;

    /**
     * remainingSteps are the number of steps
     */
    private int remainingSteps;

    /**
     * Constructor of class
     * @param name
     * @param maxPossibleSteps number of maximum steps the player can make
     * @param normalAction boolean that has a value based on if the action is normal or not
     * @param moreAction
     * @param mostAction
     * @param run boolean set as true if the player is in run state or not,else false
     * @param pickUp boolean set as true if the player is in pickUp state or not,else false
     * @param shoot boolean set as true if the player is in shoot state or not,else false
     * @param usePowerUp boolean  whether the player has used powerup or not
     * @param reload boolean
     * @param dead boolean that tells if player is dead or not
     * @param overKilled
     * @param possibleNextState
     */
    public State(String name, int maxPossibleSteps, boolean normalAction, boolean moreAction, boolean mostAction, boolean run, boolean pickUp, boolean shoot, boolean usePowerUp, boolean reload, boolean dead, boolean overKilled, Map<String, State> possibleNextState) {
        this.name = name;
        this.maxPossibleSteps = maxPossibleSteps;
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
        this.remainingSteps = maxPossibleSteps;
        this.reload = reload;
    }

    /**
     * CLassic method offered by Gson that returns endTurn
     * @param path is the path
     * @return the state in which the player is
     */
    public static State fromJson(String path) {
        //creates a builder with the state machine deserializer
        GsonBuilder gb = new GsonBuilder().registerTypeAdapter(State[].class, new StateMachineDeserializer());
        State[] states;
        Gson g = gb.create();
        InputStreamReader jsonStateMachine = null;
        jsonStateMachine = new InputStreamReader(State.class.getResourceAsStream(path));

        states = g.fromJson(jsonStateMachine, State[].class);
        for (State s : states) {
            if (s.getName().equals("EndTurn")) {
                return s;
            }
        }

        throw new JsonParseException("the json was not well built, the EndTurn state is not present");
    }

    /**
     * tells the max number of step a player can do in this state
     *
     * @return number of steps for this state
     */
    public int getMaxPossibleSteps() {
        return maxPossibleSteps;
    }

    /**
     * tells the name of the state
     *
     * @return name of state
     */
    public String getName() {
        return name;
    }

    /**
     * tells if the owner could choose what to do in this state
     *
     * @return if is in a normal type of action choice
     */
    public boolean isNormalAction() {
        return normalAction;
    }

    /**
     * tells if the owner could choose what to do in this state
     *
     * @return if is in a more type of action choice (more than 3 damage)
     */
    public boolean isMoreAction() {
        return moreAction;
    }

    /**
     * tells if the owner could choose what to do in this state
     *
     * @return if is in a most type of action choice (more than 6 damage)
     */
    public boolean isMostAction() {
        return mostAction;
    }

    /**
     * tells if the owner can run in this state
     *
     * @return if the owner can run in this state
     */
    public boolean canRun() {
        return run;
    }

    /**
     * tells if the owner can pickUp in this state
     *
     * @return if the owner can pickUp in this state
     */
    public boolean canPickUp() {
        return pickUp;
    }

    /**
     * tells if the owner can shoot in this state
     *
     * @return if the owner can shoot in this state
     */
    public boolean canShoot() {
        return shoot;
    }

    /**
     * tells if the owner can use a powerUp in this state
     *
     * @return if the owner can use a powerUp in this state
     */
    public boolean canUsePowerUp() {
        return usePowerUp;
    }

    /**
     * tells if the owner can reload in this state
     *
     * @return if the owner can reload in this state
     */
    public boolean canReload() {
        return reload;
    }

    /**
     * tells if the owner is dead
     *
     * @return if the owner is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * tells if the owner is overkilled
     *
     * @return if the owner is overkilled
     */
    public boolean isOverKilled() {
        return overKilled;
    }

    /**
     * tells which are reachable state from the current
     *
     * @return a map of reachable state from the current
     */
    public Map<String, State> getPossibleNextStates() {
        return possibleNextState;
    }

    /**
     * tells how many steps the owner still can do
     *
     * @return number of step the owner still can do
     */
    public int getRemainingSteps() {
        return remainingSteps;
    }

    /**
     * reset the number of remaining steps
     */
    public void resetRemainingSteps() {
        remainingSteps = maxPossibleSteps;
    }

    /**
     * decrement the number of remaining steps by a number given
     *
     * @param stepsToDecrement number of step to decrement remaining steps
     */
    public void decrementRemainingSteps(int stepsToDecrement) {
        this.remainingSteps -= stepsToDecrement;
    }

    /**
     * decrement the number of remaining steps to zero
     */
    public void remainingStepsToZero() {
        this.remainingSteps = 0;
    }


    /**
     * sets the nextState for the player by a given string
     *
     * @param state string containing the wanted nextState
     */
    public void nextState(String state, Player player) {
        if (state == null) throw new IllegalArgumentException();
        if (!possibleNextState.containsKey(state))
            throw new IllegalStateException("Error: from " + getName() + " state you can't go to " + state + " state");
        player.setState(possibleNextState.get(state));
    }

    /**
     * Creates the list of adjacency in the map
     */
    public void allocatePossibleNextState() {
        this.possibleNextState = new HashMap<>();
    }

    /**
     * Adds to the adjacency list the state with the given name
     *
     * @param name the name of the state
     * @param s    the state
     */
    public void addProxState(String name, State s) {
        if (name != null && s != null) {
            possibleNextState.put(name, s);
        }
    }
}
