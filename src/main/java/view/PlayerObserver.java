package view;

import model.Player;

public interface PlayerObserver {
    public void onHpChange(Player damagePlayer);
    public void onMarksChange(Player markedPlayer);
    public void onAmmoChange(Player p);
    public void onPowerUpChange(Player p);
}
