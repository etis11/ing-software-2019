package controller;

import model.Lobby;
import model.User;

import java.util.LinkedList;
import java.util.List;

public  abstract class AbstractCommandResponse {
    private String message;
    private boolean errorOccurred;
    private String error;
    private boolean playerChanged;
    private boolean mapChanged;
    private String mapName;
    private Lobby lobby;
    private List<User> joiningUsers;
    private List<User> leavingUsers;

    public AbstractCommandResponse(){
        joiningUsers = new LinkedList<>();
        leavingUsers = new LinkedList<>();
        mapChanged = false;
    }

    public List<User> getJoiningUsers() {
        return joiningUsers;
    }

    public List<User> getLeavingUsers() {
        return leavingUsers;
    }

    public void resetMessage(){
        message = null;
    }

    public void setMessage(String s){
        message = s;
    }

    public void resetErrorMessage(){
        errorOccurred = false;
        error = null;
    }

    public void setErrorMessage(String s){
        error = s;
        errorOccurred = true;
    }
    public void setMapChanged(boolean value){
        mapChanged = value;
    }

    public void setPlayerChanged(boolean value){
        playerChanged = value;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasErrorOccurred() {
        return errorOccurred;
    }

    public String getError() {
        return error;
    }

    public boolean arePlayersChanged() {
        return playerChanged;
    }

    public boolean isMapChanged() {
        return mapChanged;
    }


    public String getMapName() {
        return mapName;
    }

    public void setMap(String map) {
        this.mapName = map;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void addJoinedUser(User u){
        joiningUsers.remove(u);
        joiningUsers.add(u);
    }

    public void addLeavingUser(User u){
        leavingUsers.remove(u);
        leavingUsers.add(u);
    }

    public void resetJoinedUsers(){ joiningUsers.clear();}

    public void resetLeavingUsers(){ leavingUsers.clear();}
}
