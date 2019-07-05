package model;

/**
 * Implemented by all the objects that are interested in the start of the game, in the creation of the lobby or in the
 * change of the map
 */
public interface CreationGameObserver {

    void notifyStartedGame(Match m);

    void notifyCreatedLobby(Lobby l);

    void notifyChangedMap(String mapName);
}
