package controller;

import model.Match;

public class LobbyCommandExecutor extends AbstractCommandExecutor{

    public LobbyCommandExecutor(Match match/*, List<Command> commandList*/) {
        //TODO i parametri sono unici?
    }

    @Override
    public void executeCommand() {
        //TODO
        super.executeCommand();
    }

    @Override
    public void addCommand() {
        //TODO
        super.addCommand();
    }

    @Override
    public void executeSecondaryCommand() {
        //TODO
        super.executeSecondaryCommand();
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
