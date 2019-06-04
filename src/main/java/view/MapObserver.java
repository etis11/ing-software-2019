package view;


import model.Match;

public interface MapObserver {
    public void onMapChange(Match m);
}
