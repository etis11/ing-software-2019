package model;

import java.util.ArrayList;
import java.util.List;

/**
 * RoomStrategy is used to know by a weapon if the target given are in the same room of the shooter player and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class RoomStrategy extends AbstractTargetStrategy {

    /**
     * gameMap is a reference to game map as to get player in visible position
     */
    private GameMap gameMap;

    /**
     * creates a roomStrategy
     * @param gameMap of the match
     */
    public RoomStrategy(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    /**
     * verify if all players in target are in the same room of shooter
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
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

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone in the shooter room
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter, List<Player> players) {
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : Match.getPlayers()){
            if (roomPlayer.contains(p)){
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player in the shooter room
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        return new ArrayList<>(shooter.getTile().getRoom().getPlayersInRoom());
    }
}
