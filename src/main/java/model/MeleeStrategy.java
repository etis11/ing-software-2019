package model;

import java.util.List;

/**
 * MeleeStrategy class determinate the possibility to shoot player in your tile
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MeleeStrategy extends AbstractTargetStrategy {

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
}
