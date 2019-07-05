package model;

/**
 * The object of the classes implementing this interface are interested in the change of a tile or a player
 */
public interface ChangesObserver {

    /**
     * The object is notified if something happens on the tile ( the players in it have changed, there are no ammos, and
     * so on)
     * @param t tile changed
     */
    void notifyTileChange(Tile t);

    /**
     * Notifies if something happen to the player (his marks changed, his damage changed etc)
     * @param p
     */
    void notifyPlayerChange(Player p);
}
