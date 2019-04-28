package model;

import java.util.ArrayList;
import java.util.List;

/**
 * the current implementation requires
 */
public class FlameThrowerStrategy extends AbstractTargetStrategy {

    public boolean areTargetValid(Player shooter, List<Player> targets) {
        //checks if throws an exception
        super.areTargetValid(shooter, targets);

    }

    @Override
    public boolean canHitSomeone(Player shooter) {
        Tile shooterTile = shooter.getTile();
        Tile north = shooterTile.getNorthTile();

    }

    }

    @Override
    public List<Player> hittableTargets(Player shooter, List<Player> players) {
        return new ArrayList<>();
    }
