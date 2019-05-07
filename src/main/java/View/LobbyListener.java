package View;

import model.User;

public interface LobbyListener {

    public void onJoin(User joinedUser);
    public void onLeave(User leavingUser);
}
