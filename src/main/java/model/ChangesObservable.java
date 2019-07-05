package model;

/**
 * Implemented by the objects that can understand if a tile or player have changed
 */
public interface ChangesObservable {

    void attach(ChangesObserver observer);
}
