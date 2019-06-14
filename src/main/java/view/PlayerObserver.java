package view;

import model.clientModel.SemplifiedPlayer;

public interface PlayerObserver {
    void onHpChange(SemplifiedPlayer damagePlayer);

    void onMarksChange(SemplifiedPlayer markedPlayer);

    void onAmmoChange(SemplifiedPlayer p);

    void onPowerUpChange(SemplifiedPlayer p);

    void onWeaponChange(SemplifiedPlayer p);

    void onPlayerChange(SemplifiedPlayer p);
}
