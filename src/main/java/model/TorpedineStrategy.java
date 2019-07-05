package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TorpedineStrategy extends AbstractTargetStrategy {

    /**
     * variable that sets a Match
     */
    private Match match;

    /*
    Creates a TorpedineStrategy
     */
    public TorpedineStrategy(Match match) {
        this.match = match;
    }

    public TorpedineStrategy() {
    }

    /**
     * Method used to check whether or not the targets are valid or not
     * @param shooter is the player whose turn is
     * @param targets is the list of all possible targets
     * @return true or false
     */
    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        List<Player> visiblePlayer = match.getMap().allVisiblePlayers(shooter);

        List<Player> visiblePlayer2 = new LinkedList<>();
        List<Player> visiblePlayer3 = new LinkedList<>();

        if( !targets.isEmpty() ){
            if (!visiblePlayer.contains(targets.get(0))) {
                return false;
            }

        }
        if(targets.size()>1){
            visiblePlayer2 = match.getMap().allVisiblePlayers(targets.get(0));
            if (!visiblePlayer2.contains(targets.get(1))) {
                return false;
            }
        }
        if(targets.size()>2){
            visiblePlayer3 = match.getMap().allVisiblePlayers(targets.get(1));
            if (!visiblePlayer3.contains(targets.get(2))) {
                return false;
            }
        }


        return true;
    }

    /**
     * verify if someone can be shot by this type of strategy particular if there is someone visible by shooter
     *
     * @param shooter player who wants to shoot
     * @return true if there are someone, false otherwise
     */
    @Override
    public boolean canHitSomeone(Player shooter) {
        List<Player> visiblePlayer = match.getMap().allVisiblePlayers(shooter);
        for (Player p : match.getPlayers()) {
            if (visiblePlayer.contains(p) && p != shooter) {
                return true;
            }
        }
        return false;
    }

    /**
     * produce a list of player shootable by this type of strategy, particular player visible by shooter
     *
     * @param shooter player who wants to shoot
     * @return list of player hittable
     */
    @Override
    public List<Player> getHittableTargets(Player shooter) {
        List<Player> toReturn = new ArrayList<>();
        for (Player p : match.getMap().allVisiblePlayers(shooter)) {
            if (p != shooter) {
                toReturn.add(p);
            }
        }
        return toReturn;
    }
}
