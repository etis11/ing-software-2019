package controller;

import model.Player;

public interface Notifier {

    void notifyError(String message, JsonReceiver jsonReceiver);

    void notifyMessage(String message, JsonReceiver jsonReceiver);

     void notifyMessageTargetPlayer(String message, JsonReceiver jsonReceiver, Player player);

}
