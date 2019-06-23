package view;

import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedPlayer;

import java.util.List;

public interface MatchObserver {
    void onCurrentPlayerChange(SemplifiedPlayer p);

    void onSkullChange(List<SemplifiedBloodToken> skull);
}
