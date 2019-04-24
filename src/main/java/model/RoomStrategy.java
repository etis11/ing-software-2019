package model;

import java.util.List;

public class RoomStrategy extends AbstractTargetStrategy {

    public  RoomStrategy(){

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
}
