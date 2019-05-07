package model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent movement of a player
 *
 * @author Alessandro Passoni
 * @version 1.0
 */
public class Movement {

    /**
     * this is the number of moves
     */
    private List<String> steps;

    /**
     * this is the constructor method
     * @param steps number of moves to do
     */
    public Movement(List<String> steps) {
        this.steps = steps;
    }

    /**
     * return the list of steps
     * @return list of steps
     */
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }
}
