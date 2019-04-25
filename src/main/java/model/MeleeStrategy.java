package model;

import java.util.ArrayList;
import java.util.List;

/**
 * MeleeStrategy is used to know by a weapon if the target given are in the same tile of the shooter player and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MeleeStrategy extends AbstractTargetStrategy {

    /**
     * creates a MeleeStrategy
     */
    public MeleeStrategy(){

    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        for (Player p : targets){
            if (!shooter.getTile().isPlayerIn(p)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        for (Player p : players){
            if (shooter.getTile().isPlayerIn(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        List<Player> hittable = new ArrayList<>();
        for (Player p : players){
            if (shooter.getTile().isPlayerIn(p)){
                hittable.add(p);
            }
        }
        return hittable;
    }
}
