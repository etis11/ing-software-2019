package controller;

import model.GameManager;
import model.JsonCreator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandLauncherProvider {

    private final List<CommandLauncher> commandLaunchers;
    private final List<GameManager> gameManagers;
    private int currentLauncher;

    public CommandLauncherProvider(){
        commandLaunchers = new LinkedList<>();
        gameManagers = new LinkedList<>();
        // so the first game has index 0
        currentLauncher = -1;
    }

    public CommandLauncherInterface getCurrentCommandLauncher() throws RemoteException{
        //first time launched, creates a new game
        if (commandLaunchers.size() == 0) createNewGame();
        GameManager currentGameManager = gameManagers.get(currentLauncher);
        //if the current game is started, create a new game
        if (currentGameManager.isMatchStarted()) createNewGame();
        //creates a new game if the lobby is full
        if(currentGameManager.getLobby().getNumOfUsers() >= 5) createNewGame();
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
        new Thread(()-> launcher.executeCommand()).start();

    }

}