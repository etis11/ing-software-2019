package Model;

/**
 * this class represent movement of a player, it is used in DamageTransporter
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Movement {

    /**
     * this is the numeber of moves
     */
    private int moves;

    /**
     * this is the constructor method
     * @param moves number of moves to do
     */
    public Movement(int moves) {
        this.moves = moves;
    }

    /**
     * this method return number of moves
     * @return number of moves
     */
    public int getMoves() {
        return moves;
    }
}
