package model;

import java.util.List;

public interface TargetStrategy {
    boolean areTargetValid(Player shooter, List<Player> targets);
    boolean canHitSomeone(Player shooter, List<Player> players);
    List<Player> hittableTargets(Player shooter, List<Player> players);

}
