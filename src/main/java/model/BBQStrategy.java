package model;

import java.util.List;

//this strategy extends the flameThrowerStrategy, but  just the areTargetsValid method, since the others work fine
public class BBQStrategy extends FlameThrowerStrategy {

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks if the parameters are correct
        super.areTargetValid(shooter, targets);

        String[] directions = { "north", "east", "south", "west"};


    }
