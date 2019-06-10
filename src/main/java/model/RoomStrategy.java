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

    private Match match;


    public RoomStrategy() {
    }

    /**
     * creates a roomStrategy
     */
    public RoomStrategy(Match match) {
        this.match = match;
    }


    /**
     * verify if all players in target are in the same room of shooter
     *
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : targets) {
            if (!roomPlayer.contains(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone in the shooter room
     *
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> roomPlayer = shooter.getTile().getRoom().getPlayersInRoom();
        for (Player p : match.getPlayers()) {
            if (roomPlayer.contains(p) && p != shooter) {
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player in the shooter room
     *
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> toReturn = new ArrayList<>();
        for (Player p : shooter.getTile().getRoom().getPlayersInRoom()) {
            if (p != shooter) {
                toReturn.add(p);
            }
        }
        return toReturn;
    }
}
