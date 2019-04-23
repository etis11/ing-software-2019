package model;

public class DamageTransporter {

    /**
     * this is the player targeted to be damaged
     */
    private Player target;

    /**
     * this is the player that created this damageTransporter object
     */
    private Player owner;

    /**
     * this is the number of damage assigned to target
     */
    private int numDamage;

    /**
     * this is the number of marks assigned to target
     */
    private int numMark;


    /**
     * this is the cunstroctor method
     * @param target is the player to damage
     * @param owner is the player that created this DamageTransporter object
     * @param numDamage is the damage to assign to target
     * @param numMark is the number of marks to assign to target
     */
    public DamageTransporter(Player target, Player owner, int numDamage, int numMark) {
        this.target = target;
        this.owner = owner;
        this.numDamage = numDamage;
        this.numMark = numMark;
    }

    public DamageTransporter(Player p, Object o, int i, int i1, boolean b, boolean b1) {
    }

    /**
     * this method return the player targeted
     * @return player targeted
     */
    public Player getTarget() {
        return target;
    }

    /**
     * this method return the player that wants to damage the target
     * @return owner of this object
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * this method return the number of damage to assign to target
     * @return number of damage
     */
    public int getNumDamage() {
        return numDamage;
    }


    /**
     * this method return the number of marks to assign to target
     * @return number of marks
     */
    public int getNumMark() {
        return numMark;
    }

}
