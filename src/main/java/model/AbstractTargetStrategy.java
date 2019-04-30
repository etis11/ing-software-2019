package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class used to extend TargetStrategy
 * */
public abstract class AbstractTargetStrategy implements TargetStrategy{

    /**
     * Method used to check whether or not the targets chosen by the shooter are valid.In case targets or shooter
     * are NULL, it must throw an Exception
     * @param shooter  is the player who is trying to perform the action
     * @param targets is a list of players chosen by the shooter as possible targets
     * */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        if (shooter == null){
            throw new IllegalArgumentException("shooter mustn't be null");
        }
        if (targets == null){
            throw new IllegalArgumentException("targets mustn't be null");
        }
        if(targets.contains(shooter)) throw new IllegalArgumentException("The shooter cant be in the targets");
        return false;
    }

    /**
     * Method used to return a boolean based on the possibility for the shooter to target at least another player
     * @param shooter is the player whose turn is
     * @return a boolean
     * */
    @Override
    public boolean canHitSomeone(Player shooter) {
        return false;
    }

    /**
     * Method that returns the list of valid targets for the shooter
     * @param shooter is the player whose turn is
     * @return an Arraylist of all targets that can be picked up by the shooter
     * */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return new ArrayList<>();
    }
}
