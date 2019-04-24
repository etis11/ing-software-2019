package model;

import java.util.List;

public abstract class AbstractTargetStrategy implements TargetStrategy{

    private List<Effect> effects;

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        if (shooter == null){
            throw new IllegalArgumentException("shooter mustn't be null");
        }
        if (targets == null){
            throw new IllegalArgumentException("targets mustn't be null");
        }
        return false;
    }
}
