package model;

import java.util.List;

public class DontSeeStrategy extends AbstractTargetStrategy {

    private GameMap gameMap;

    public DontSeeStrategy(GameMap gameMap){
        this.gameMap= gameMap;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> notVisiblePlayers = gameMap.allNotVisiblePlayers(shooter);
        return notVisiblePlayers.containsAll(targets);
    }

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        List<Player> notVisiblePlayers = gameMap.allNotVisiblePlayers(shooter);
        return players.stream().anyMatch(p ->  notVisiblePlayers.contains(p) ) ;
    }

    //all not visible targets
    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        return gameMap.allNotVisiblePlayers(shooter);
    }
}
