package model;

import java.util.List;

public abstract class AbstractTargetStrategy implements TargetStrategy{

    private List<Effect> effects;

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        return false;
    }
}
