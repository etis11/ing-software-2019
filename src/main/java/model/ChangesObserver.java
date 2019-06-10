package model;

public interface ChangesObserver {

    void notifyTileChange(Tile t);

    void notifyPlayerChange(Player p);
}
