package model;

import com.google.gson.GsonBuilder;
import jsonparser.WeaponCardDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * MeleeStrategy is used to know by a weapon if the target given are in the same tile of the shooter player and so if the weapon effect ca be applied
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class MeleeStrategy extends AbstractTargetStrategy {

    /**
     * reference to the current match
     */
    private Match match;

    /**
     * creates a MeleeStrategy
     */
    public MeleeStrategy(Match m){
        this.match = m;
    }

    /**
     * verify if all players in target are in the same tile of shooter
     * @param shooter player who wants to shoot
     * @param targets player to be shot
     * @return true if all targets are correctly selected, false otherwise
     */
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

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone in the shooter tile
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        for (Player p : match.getPlayers()){
            if (shooter.getTile().isPlayerIn(p) && p!=shooter){
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player in the shooter tile
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> hittable = new ArrayList<>();
        for (Player p : match.getPlayers()){
            if (shooter.getTile().isPlayerIn(p) && p!=shooter){
                hittable.add(p);
            }
        }
        return hittable;
    }
}
