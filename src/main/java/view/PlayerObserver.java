package view;

import model.Player;

public interface PlayerObserver {
    void onHpChange(Player damagePlayer);

    void onMarksChange(Player markedPlayer);

    void onAmmoChange(Player p);

    void onPowerUpChange(Player p);

    void onWeaponChange(Player p);

    void onPlayerChange(Player p);
}
