package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTargetStrategy implements TargetStrategy{

    protected List<Effect> effects;

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

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        return false;
    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        return new ArrayList<>();
    }
}
