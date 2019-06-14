package model;

import exceptions.NotValidActionException;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    /**
     * MAX_PLAYER_IN_LOBBY are the maximum of player allowed in the lobby
     */
    public static final int MAX_PLAYER_IN_LOBBY = 5;

    /**
     * users are the User contained in the Lobby
     */
    List<User> users;

    public Lobby() {
        this.users = new ArrayList<>();
    }

    /**
     * tells maximum number of player for the lobby
     *
     * @return constant of max player
     */
    public static int getMaxPlayerInLobby() {
        return MAX_PLAYER_IN_LOBBY;
    }

    /**
     * give players in the lobby
     *
     * @return list of players in the lobby
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * allow a User to join the lobby if it is not full
     *
     * @param u user to be added
     * @throws Exception thrown if the lobby is already full
     */
    public void join(User u) throws NotValidActionException {
        if (!canJoin()) {
            throw new NotValidActionException("Lobby full");
        }
        users.add(u);
    }

    /**
     * tell if a lobby is full or not
     *
     * @return true if the lobby is not already full, false otherwise
     */
    public boolean canJoin() {
        return users.size() < MAX_PLAYER_IN_LOBBY;
    }

    /**
     * allow to remove a User from the Lobby by giving it
     *
     * @param u user to be removed
     * @return user to be removed
     */
    public User removeUser(User u) {
        if (u == null) throw new IllegalArgumentException("not inserted an user");
        if (users.contains(u)) {
            return users.remove(users.indexOf(u));
        } else {
            return null;
        }
    }

    public List<String> getNameToken(){
        List<String> toReturn = new ArrayList<>();
        for (User u : users){
            toReturn.add(u.getPlayer().getName());
        }
        return toReturn;
    }
}
