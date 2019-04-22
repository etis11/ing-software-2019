package Model;


/**
 * Class used for the blood tokens
 * */
public class BloodToken {

    /**
     * owner is the Player
     */
    /
    private Player owner;

    /**
     * The constructor of BloodTOken
     * @param p is used to choose the actual plauyer during the turn.
     * This way the owner of the blood tokens becomes the player whose turn is
     * */
    public BloodToken(Player p){
        owner = p;
    }

    /**
     * Returns the owner based on player's turn
     * @return owner returns the Player whose turn is, needed later on to check his blood tokens
     */
     */
    /
    public Player getOwner(){
        return owner;
    }
}
