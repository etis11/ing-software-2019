package model;

/**
 *Implemented by all the entities that can have the logic that understand if a match is starting
 */
public interface CreationGameObservable {

    void attach(CreationGameObserver ob);
}
