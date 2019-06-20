package model;

public interface CreationGameObserver {

    void notifyStartedGame(Match m);

    void notifyCreatedLobby(Lobby l);
}