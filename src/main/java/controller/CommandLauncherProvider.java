package controller;

import model.GameManager;
import model.JsonCreator;
import model.Lobby;
import model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandLauncherProvider {

    private final List<CommandLauncher> commandLaunchers;
    private final List<GameManager> gameManagers;
    private int currentLauncher;

    public CommandLauncherProvider() {
        commandLaunchers = new LinkedList<>();
        gameManagers = new LinkedList<>();
        // so the first game has index 0
        currentLauncher = -1;
    }

    public CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException {
        //first time launched, creates a new game
        if (commandLaunchers.size() == 0) createNewGame();
        GameManager currentGameManager = gameManagers.get(currentLauncher);
        //if the current game is started, create a new game
        if (currentGameManager.isMatchStarted()) createNewGame();
        //creates a new game if the lobby is full
        if (currentGameManager.getLobby().getNumOfUsers() >= 5) createNewGame();
        return commandLaunchers.get(currentLauncher);
    }

    private void createNewGame() throws RemoteException {
        //creates a game with a lobby but without a match
        GameManager gameManager = new GameManager();
        //creates a json creator
        JsonCreator jsonCreator = new JsonCreator();
        //the json creator need the information about the lobby, so it need to be attached to the game manager.
        //The json creator is also interested on the start match event
        gameManager.attach(jsonCreator);
        gameManager.attachObserverToLobby(jsonCreator);
        CommandLauncher launcher = new CommandLauncher(gameManager, jsonCreator);
        commandLaunchers.add(launcher);
        gameManagers.add(gameManager);
        currentLauncher += 1;
        UnicastRemoteObject.exportObject(launcher, 0);
        new Thread(() -> launcher.executeCommand()).start();

    }

    /**
     * removes the fiven user from the lobby of his game. Used only when the game has not started
     * @param user user to be kicked from the lobby
     */
    public void removeUserFromGame(User user) {
        synchronized (gameManagers) {
            for (GameManager gm : gameManagers) {
                Lobby lobby = gm.getLobby();
                if (lobby.contains(user)) {
                    lobby.removeUser(user);
                }
            }
        }
    }

    /**
     * tells if the game the user is in is started or not
     * @param user
     * @return true if the match is started
     */
    public boolean hasGameStarted(User user) {
        synchronized (gameManagers) {
            for (GameManager gm : gameManagers) {
                Lobby lobby = gm.getLobby();
                if (lobby.contains(user)) {
                    return gm.isMatchStarted();
                }
            }
            throw new RuntimeException("This user is not in any game");
        }

    }

    /**
     * makes the user with this name in the right lobby active again
     * @param name
     */
    public User ReconnectUserToOldGame(String name){
        synchronized (gameManagers){
            for(GameManager gm : gameManagers){
                List<User> users = gm.getLobby().getUsers();
                for(User user: users){
                    if (user.getUsername().equals(name)){
                        user.setDisconnected(false);
                        return user;
                    }

                }
            }
        }
        return null;
    }

    public CommandLauncherInterface getConnectedBeforeLauncher(String name){
        int index = 0;
        synchronized (gameManagers){
            for(GameManager gm : gameManagers){
                List<User> users = gm.getLobby().getUsers();
                for(User user: users){
                    if (user.getUsername().equals(name))
                        index = gameManagers.indexOf(gm);
                }
            }
        }
        return commandLaunchers.get(index);
    }

}
