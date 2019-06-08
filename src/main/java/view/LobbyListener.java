package view;

import model.User;

public interface LobbyListener {

     void onJoin(User joinedUser);
     void onLeave(User leavingUser);
}
