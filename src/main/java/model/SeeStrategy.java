package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SeeStrategy is used to know by a weapon if the target given can be seen by the shooter player and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class SeeStrategy extends AbstractTargetStrategy {

    /**
     * gameMap is a reference to game map as to get player in visible position
     */
    private GameMap gameMap;

    /**
     * creates a seeStrategy
     * @param gameMap of the match
     */
    public SeeStrategy(GameMap gameMap){
        this.gameMap = gameMap;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> visiblePlayer = gameMap.allVisiblePlayers(shooter);
        for (Player p: targets){
            if (!visiblePlayer.contains(p)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        List<Player> visiblePlayer = gameMap.allVisiblePlayers(shooter);
        for (Player p: players){
            if (visiblePlayer.contains(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        return new ArrayList<>(gameMap.allVisiblePlayers(shooter));
    }
}
