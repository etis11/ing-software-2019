package model;

/**
 * This interface is implemented by all the components that contains the logic of the match
 */
public interface ChangesMatchObservable {

    void attach(ChangesMatchObserver observer);
}
