package model;

import java.util.List;

/**
 * This interfaces is implemented by all the objects that are interested in the change of the current player
 * and the change of the death track
 */
public interface ChangesMatchObserver {

        /**
         * called when a player turn is ended and a new turn has started
         * @param player player who the current turn is
         */
    void notifyCurrentPlayerChange(Player player);

    /**
     * Called when a kill happens
     * @param deathTrack the track where all the kills and thg if information about them are stored
     */
    void notifySkullChange(List<List<BloodToken>> deathTrack);
}