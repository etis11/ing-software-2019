package model;

public class User {

    /**
     * instance variable for the user token
     */
    private static long idToken = 0L;

    /**
     * username is the name chosen by the user
     */
    private String username;

    /**
     * token is the user identifier
     */
    private final long token;

    /**
     * effectPhrase is the phrase chosen by the user to play
     */
    private String effectPhrase;

    /**
     * player is the player associate with the user
     */
    private Player player;
    //TODO LobbyListener lobbyListener


    public User() {
        this.username = "user";
        this.token = User.idToken;
        User.idToken++;//increment id for token
        this.effectPhrase = "I will survive";
        this.player = null;
        //listener
    }

    public User(String username, long token) {
        this.username = username;
        this.token = token;
        this.effectPhrase = "I will survive";
        this.player = null;
        //listener
    }

    /**
     * return the username chosen by the user
     * @return user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set the user username by one given
     * @param username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * return the token which identify each user
     * @return user identifier
     */
    public long getToken() {
        return token;
    }

    /**
     * return the effect phrase chosen by the user or the one of default
     * @return return a string with the user effect phrase
     */
    public String getEffectPhrase() {
        return effectPhrase;
    }

    /**
     * allow to set the user effect phrase by the one given
     * @param effectPhrase to be set
     */
    public void setEffectPhrase(String effectPhrase) {
        this.effectPhrase = effectPhrase;
    }

    /**
     * return the player associated with the user
     * @return a player associated with the user
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * allow to associate a player to the user
     * @param player the player to associate
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
