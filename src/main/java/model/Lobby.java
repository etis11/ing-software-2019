package model;

import exceptions.NotValidActionException;
import view.LobbyListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lobby  implements  LobbyObservable{

    private static final Logger lobbyLogger = Logger.getLogger(Lobby.class.getName());

    /**
     * MAX_PLAYER_IN_LOBBY are the maximum of player allowed in the lobby
     */
    public transient static final int MAX_PLAYER_IN_LOBBY = 3;

    private transient final List<LobbyListener> lobbyListeners;
    /**
     * users are the User contained in the Lobby
     */
    List<User> users;

    public Lobby() {
        lobbyListeners = new LinkedList<>();
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
        return new LinkedList<>(users);
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
        for (LobbyListener ls: lobbyListeners) {
            ls.onJoin(u);
        }
        lobbyLogger.log(Level.INFO, "The user "+ u.getUsername() +" joined the lobby");
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
            //notifyMessage to the observers
            for (LobbyListener ls: lobbyListeners) {
                ls.onLeave(u);
            }
            lobbyLogger.log(Level.INFO, "The user "+ u.getUsername() +" left the lobby");
            return users.remove(users.indexOf(u));
        } else {
            return null;
        }
    }

    public int getNumOfUsers() {
        return users.size();
    }

    public List<String> getNameToken(){
        List<String> toReturn = new ArrayList<>();
        for (User u : users){
            if(u.getPlayer()==null){
                u.setPlayerByName("");
            }
            toReturn.add(u.getPlayer().getName());
        }
        return toReturn;
    }

    /****************************+ Lobby Observable interface ********************************************/
    @Override
    public void attach(LobbyListener ls) {
        lobbyListeners.add(ls);
    }
}
