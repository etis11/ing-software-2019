package model.clientModel;

/**
 * Client rapresentation of the damage, contains the information of the player that generated it
 */
public class SemplifiedBloodToken {

    SemplifiedPlayer owner;

    public SemplifiedBloodToken() {
    }

    public SemplifiedBloodToken(SemplifiedPlayer owner) {
        this.owner = owner;
    }

    public SemplifiedPlayer getOwner() {
        return owner;
    }

}
