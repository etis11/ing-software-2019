package model;

import view.LobbyListener;

public interface LobbyObservable {

    void attach(LobbyListener ls);
}
