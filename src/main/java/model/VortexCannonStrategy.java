package model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The following class is one of the strategies used to determine the way targets are chosen by the shooter.
 * */
public class VortexCannonStrategy extends AbstractTargetStrategy {

    private GameMap gameMap;

    private List<Player> attackedPlayers;

     public VortexCannonStrategy (GameMap gameMap){
         this.gameMap = gameMap ;
     }

    @Override
    public boolean areTargetValid(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return hittableTargets(shooter).containsAll(targets);
    }

    @Override
    public boolean canHitSomeone(Player shooter) {
        return !hittableTargets(shooter).isEmpty();
    }

//tra tutti i player,filtro quelli che hanno distanza di <=1 da visibletiles e l'altra condizione e' che non sia mestesso
    @Override
    public List<Player> hittableTargets(Player shooter) {
         return Match.getPlayers().stream().filter( player -> gameMap.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(player)<=1)) && !player.equals(shooter) ).collect(Collectors.toList());
    }

    public void moveTarget(Player shooter,  Player target, Tile destination) {
        Tile startPoint = target.getTile();
        startPoint.getPlayers().remove(target);
        target.setTile(destination);
        destination.getPlayers().add(target);
        this.attackedPlayers.add(target);
    }

    public boolean areTargetValidBlackHole(Player shooter, List<Player> targets) {
        super.areTargetValid(shooter, targets);
        return hittableTargetsBlackHole(shooter).containsAll(targets);
    }

    public boolean canHitSomeoneBlackHole(Player shooter) {
        return !hittableTargetsBlackHole(shooter).isEmpty();
    }

    public List<Player> hittableTargetsBlackHole(Player shooter) {
        return Match.getPlayers().stream().filter( player -> gameMap.allVisibleTiles(shooter).stream().filter(tile -> !tile.equals(shooter.getTile())).anyMatch(visible -> (visible.distance(player)<=1)) && !player.equals(shooter) && !this.attackedPlayers.contains(player) ).collect(Collectors.toList());
    }

    public void blackHole (Player shooter,  List<Player> targets, Tile destination){
        hittableTargetsBlackHole(shooter).forEach(player -> moveTarget(shooter,player,destination));
    }

}
