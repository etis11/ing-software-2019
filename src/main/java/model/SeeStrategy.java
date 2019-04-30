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

    /**
     * verify if all players in target are visible by shooter
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
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

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone visible by shooter
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> visiblePlayer = gameMap.allVisiblePlayers(shooter);
        for (Player p: Match.getPlayers()){
            if (visiblePlayer.contains(p)){
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player visible by shooter
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        return new ArrayList<>(gameMap.allVisiblePlayers(shooter));
    }
}
