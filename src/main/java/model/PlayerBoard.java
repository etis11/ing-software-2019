package model;

import view.PlayerObservable;
import view.PlayerObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PlayerBoard {
    /**
     * It'is a constant that defines the max damage that a player can have
     */
    private static final int MAX_DAMAGE_POINTS = 12;
    /**
     * The loader where the player's ammo are stored
     */
    private Loader loader;
    /**
     * This list contains all the bloodTokens that indicates damage
     */
    private List<BloodToken> damageTokens;
    /**
     * this list contains all the bloodTokens that indicates the marks on the current player
     */
    private List<BloodToken> marks;
    /**
     * A list that contains the possible points that other player could get after a kill
     */
    private List<Integer> killValue;

    /**
     * constructor method,
     * It has no parameters, creates a board with a default loader, a damageTokens list and marks list empity (because
     * no one still hit the player) and a default list for kill points
     */
    public PlayerBoard() {
        loader = new Loader();
        damageTokens = new LinkedList<>();
        marks = new LinkedList<>();
        killValue = (IntStream.of(8, 6, 4, 2, 1, 1, 1, 1, 1,1,1).boxed().collect(Collectors.toCollection(LinkedList::new)));
    }


    public List<BloodToken> getDamageTokens() {
        return new LinkedList<>(damageTokens);
    }

    public List<BloodToken> getMarks() {
        return new LinkedList<>(marks);
    }

    /**
     * Gets the list of current point list
     *
     * @return
     */
    public List<Integer> getKillValue() {
        return new LinkedList<>(killValue);
    }

    /**
     * This method return the number of damage points
     *
     * @return the size of damageTokens list
     */
    public int getNumDamagePoints() {
        return damageTokens.size();
    }

    /**
     * Returns the remaining hp
     *
     * @return
     */
    public int getRemainingHp() {
        return MAX_DAMAGE_POINTS - getNumDamagePoints();
    }

    /**
     * This method returns the number of marks that a player had put on the playerBoard
     *
     * @param p, the player that you are interested in
     * @return number of mark of that player
     */
    public int getNumMarksOfPlayer(Player p) {
        return (int) marks.stream().filter(mark -> mark.getOwner() == p).count();
    }

    /**
     * This method returns the number of marks that a player had put on the playerBoard
     *
     * @param p, the player that you are interested in
     * @return number of mark of that player
     */
    public int getNumDamageOfPlayer(Player p) {
        return (int) damageTokens.stream().filter(damage -> damage.getOwner() == p).count();
    }


    /**
     * Returns the number of marks of the player, without distinguishing the owner
     *
     * @return the number of marks
     */
    public int getNumMarks() {
        return marks.size();
    }


    /**
     * This method returns the number of damage that a player dealt to the owner of the playerBoard
     *
     * @param p the player that you want to know how much damage did
     * @return the damage did by that player
     */
    public int getNumDamagePoints(Player p) {
        return (int) damageTokens.stream().filter(damage -> damage.getOwner() == p).count();
    }

    /**
     * Getter method for the loader
     *
     * @return the loader of the playerBoard
     */
    public Loader getLoader() {
        return loader;
    }


    /**
     * This method applies all the changes to the player board given a damageTransporter object.
     * If the damage of the transporter is greater than 0, all the old marks are converted to damage. then all the damage
     * is added to the damageTokens list and then all the new marks are added to the marks list.
     *
     * @param d contains all the information that regards the damage. It's supposed to be legal
     */
    public void calculateDamage(DamageTransporter d) {
        //the conversion of the marks happens only if the damage is greater then 0
        if (d.getNumDamage() > 0) markToDamage(d);

        //add the damage in the list. a player can't have more then 12 damage points
        for (int i = 0; i < d.getNumDamage(); i++) {
            if (getNumDamagePoints() < MAX_DAMAGE_POINTS) {
                damageTokens.add(new BloodToken(d.getOwner()));
            }
        }

        //add the marks
        addMarks(d);
    }

    /**
     * All the marks of a given player become damage
     *
     * @param d contains all the information that regards the marks. It's supposed to be legal
     */
    private void markToDamage(DamageTransporter d) {
        for (BloodToken mark : marks) {
            if (mark.getOwner() == d.getOwner()) {
                marks.remove(mark);
                if (getNumDamagePoints() < MAX_DAMAGE_POINTS) {
                    damageTokens.add(mark);
                }

            }
        }
    }

    /**
     * add the marks contained in the DamageTransporter
     *
     * @param d contains all the information that regards the damage. It's supposed to be legal
     */
    private void addMarks(DamageTransporter d) {
        int numMarks = d.getNumMark();
        for (int i = 0; i < numMarks; i++) {
            if (getNumDamagePoints(d.getTarget()) < 3)
                marks.add(new BloodToken(d.getOwner()));
        }
    }

    public void resetPlayerboard(){
        for(BloodToken b : damageTokens){
            damageTokens.remove(b);
        }
        for(BloodToken b : marks){
            damageTokens.remove(b);
        }
    }
}
