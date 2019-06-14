package model;

import java.io.File;

import static model.State.fromJson;

public class User {

    /**
     * username is the name chosen by the user
     */
    private String username;


    /**
     * effectPhrase is the phrase chosen by the user to play
     */
    private String effectPhrase;

    /**
     * player is the player associate with the user
     */
    private transient Player player;
    //TODO LobbyListener lobbyListener


    public User() {
        this.username = "user";
        this.effectPhrase = "I will survive";
        this.player = new Player("", fromJson("." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "stateMachine" + File.separatorChar + "stateMachine.json"));
        //listener
    }

    public User(String username) {
        this.username = username;
        this.effectPhrase = "I will survive";
        this.player = new Player("", fromJson("." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "stateMachine" + File.separatorChar + "stateMachine.json"));
        //listener
    }

    /**
     * return the username chosen by the user
     *
     * @return user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set the user username by one given
     *
     * @param username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * return the effect phrase chosen by the user or the one of default
     *
     * @return return a string with the user effect phrase
     */
    public String getEffectPhrase() {
        return effectPhrase;
    }

    /**
     * allow to set the user effect phrase by the one given
     *
     * @param effectPhrase to be set
     */
    public void setEffectPhrase(String effectPhrase) {
        this.effectPhrase = effectPhrase;
    }

    /**
     * return the player associated with the user
     *
     * @return a player associated with the user
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * allow to associate a player to the user
     *
     * @param player the player to associate
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
