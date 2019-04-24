package model;

import java.util.List;

public class FixedDistanceStrategy extends SeeStrategy {

    public  FixedDistanceStrategy(GameMap gameMap){
        super(gameMap);
    }
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        boolean toReturn = true;
        toReturn = super.areTargetValid(shooter, targets);
        //TODO
        return toReturn;
    }
}
