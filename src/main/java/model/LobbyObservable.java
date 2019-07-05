package model;

import view.LobbyListener;

/**
 * Interface needed to attach an Observer to the Lobby
 */
public interface LobbyObservable {

    void attach(LobbyListener ls);
}
