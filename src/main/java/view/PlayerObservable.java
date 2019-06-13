package view;

import model.Player;

public interface PlayerObservable {

    void attachPlayerObserver(PlayerObserver playerObserver);
}
