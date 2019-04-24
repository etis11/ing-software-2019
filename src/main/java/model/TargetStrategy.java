package model;

import java.util.List;

public interface TargetStrategy {
    public boolean areTargetValid(Player shooter, List<Player> targets);
}
