package view;


import model.Match;
import model.clientModel.SemplifiedMap;

public interface MapObserver {
    void onMapChange(SemplifiedMap map);

    void onTypeMapChange(String mapName);
}
