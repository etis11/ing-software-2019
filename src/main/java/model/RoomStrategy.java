package model;

import java.util.ArrayList;
import java.util.List;

public class RoomStrategy extends AbstractTargetStrategy {

    private GameMap gameMap;

    public RoomStrategy(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : targets){
            if (!roomPlayer.contains(p)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : players){
            if (roomPlayer.contains(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        List<Player> hittable = new ArrayList<>();
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : players) {
            if (roomPlayer.contains(p)) {
                hittable.add(p);
            }
        }
        return hittable;
    }
}
