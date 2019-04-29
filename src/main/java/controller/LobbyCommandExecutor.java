package controller;

import model.Match;

import java.util.ArrayList;

public class LobbyCommandExecutor extends AbstractCommandExecutor{

    public LobbyCommandExecutor(Match match) {
        this.match = match;
        this.commandList = new ArrayList<>();
    }
    

    /**
     * routine to set user Username
     */
    public void setUsername(){
        //TODO
    }

    /**
     * routine to set the player token
     */
    public void setPlayerToken(){
        //TODO
    }

    /**
     * routine to set the user effect phrase
     */
    public void setEffectPhrase(){
        //TODO
    }

    /**
     * return a list of command to set the match
     */
    public void commandHelp(){
        //TODO
    }

    /**
     * routine to set number of kills for the match
     */
    public void setNumberOfKills(){
        //TODO
    }

    /**
     * routine to set the game mode
     */
    public void setMode(){
        //TODO
    }

    /**
     * routine to set the game map for the match
     */
    public void setMap(){
        //TODO
    }

    /**
     * routine to set the max number of player for the match
     */
    public void setNumberOfPlayers(){
        //TODO
    }
}
