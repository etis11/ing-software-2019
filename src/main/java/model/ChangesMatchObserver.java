package model;

import java.util.List;

public interface ChangesMatchObserver {
    void notifyCurrentPlayerChange(Player player);

    void notifySkullChange(List<List<BloodToken>> deathTrack);
}

