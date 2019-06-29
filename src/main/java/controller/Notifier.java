package controller;

import model.Player;
import model.User;

public interface Notifier {

    void notifyError(String message, JsonReceiver jsonReceiver);

    void notifyMessage(String message, JsonReceiver jsonReceiver);

     void notifyMessageTargetPlayer(String message, JsonReceiver jsonReceiver, Player player);

     void disconnectReceiver(JsonReceiver jsonReceiver);

     void notifyDisconnection(User user, Player player, JsonReceiver jsonReceiver);
}
