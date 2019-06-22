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
     * maxPlayerInLobby are the maximum of player allowed in the lobby
     */
    private transient int maxPlayerInLobby = 2;

    private transient final List<LobbyListener> lobbyListeners;
    /**
     * users are the User contained in the Lobby
     */
    private final List<User> users;

    public Lobby() {
        lobbyListeners = new LinkedList<>();
        this.users = new ArrayList<>();
    }

    /**
     * tells maximum number of player for the lobby
     *
     * @return constant of max player
     */
    public int getMaxPlayerInLobby() {
        return maxPlayerInLobby;
    }

    public void setMaxPlayerInLobby(int maxPlayerInLobby){
        this.maxPlayerInLobby = maxPlayerInLobby;
    }

    /**
     * give players in the lobby
     *
     * @return list of players in the lobby
     */
    public List<User> getUsers() {
        synchronized (users){
            return new LinkedList<>(users);
        }
    }

    /**
     * allow a User to join the lobby if it is not full
     *
     * @param u user to be added
     * @throws Exception thrown if the lobby is already full
     */
    public void join(User u) throws NotValidActionException {
        synchronized (users){
            if (!canJoin()) {
                throw new NotValidActionException("Lobby full");
            }
            users.add(u);
            for (LobbyListener ls: lobbyListeners) {
                ls.onJoin(u);
            }
            lobbyLogger.log(Level.INFO, "The user "+ u.getUsername() +" joined the lobby");
        }
    }

    /**
     * tell if a lobby is full or not
     *
     * @return true if the lobby is not already full, false otherwise
     */
    public boolean canJoin() {
        synchronized (users){
            return users.size() < maxPlayerInLobby;
        }
    }

    /**
     * allow to remove a User from the Lobby by giving it
     *
     * @param u user to be removed
     * @return user to be removed
     */
    public User removeUser(User u) {
        synchronized (users){
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
    }

    public int getNumOfUsers() {
        synchronized (users){
            return users.size();
        }
    }

    public List<String> getNameToken(){
        synchronized (users){
            List<String> toReturn = new ArrayList<>();
            for (User u : users){
                if(u.getPlayer()==null){
                    u.setPlayerByName("");
                }
                toReturn.add(u.getPlayer().getName());
            }
            return toReturn;
        }
    }

    public boolean isFull(){
        return getNumOfUsers() == getMaxPlayerInLobby();
    }

    /****************************+ Lobby Observable interface ********************************************/
    @Override
    public void attach(LobbyListener ls) {
        synchronized (lobbyListeners){
            lobbyListeners.add(ls);
        }
    }
}
