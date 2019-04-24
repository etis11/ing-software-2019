package model;

import java.util.List;

/**
 * MeleeStrategy class determinate the possibility to shoot player in your tile
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MeleeStrategy extends AbstractTargetStrategy {

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        if (shooter == null){
            throw new IllegalArgumentException("shooter mustn't be null");
        }
        if (targets == null){
            throw new IllegalArgumentException("targets mustn't be null");
        }
        for (Player p : targets){
            if (!shooter.getTile().isPlayerIn(p)){
                return false;
            }
        }
        return true;
    }
}
