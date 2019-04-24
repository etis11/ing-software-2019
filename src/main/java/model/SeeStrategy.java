package model;

import java.util.List;

public class SeeStrategy extends AbstractTargetStrategy {

    private GameMap gameMap;

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
}
