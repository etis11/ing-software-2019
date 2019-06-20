package controller;

import model.Lobby;
import model.User;

import java.util.List;

public  abstract class AbstractCommandResponse {
    private String message;
    private boolean errorOccurred;
    private String error;
    private boolean playerChanged;
    private boolean mapChanged;
    private String mapName;
    private Lobby lobby;
    private List<User> join;
    private List<User> leave;

    public AbstractCommandResponse(){
        mapChanged = false;
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
        join.remove(u);
        join.add(u);
    }

    public void addLeavingUser(User u){
        leave.remove(u);
        leave.add(u);
    }

    public void resetJoinedUsers(){ join.clear();}

    public void resetLeavingUsers(){ leave.clear();}
}
