package model.clientModel;

public class SemplifiedBloodToken {

    SemplifiedPlayer owner;

    public SemplifiedBloodToken(SemplifiedPlayer owner) {
        this.owner = owner;
    }

    public SemplifiedPlayer getOwner() {
        return owner;
    }

}
