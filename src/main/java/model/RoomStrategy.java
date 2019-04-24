package model;

import java.util.List;

public class RoomStrategy extends AbstractTargetStrategy {
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        if (shooter == null){
            throw new IllegalArgumentException("shooter mustn't be null");
        }
        if (targets == null){
            throw new IllegalArgumentException("targets mustn't be null");
        }
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : targets){
            if (!roomPlayer.contains(p)){
                return false;
            }
        }
        return true;
    }
}
