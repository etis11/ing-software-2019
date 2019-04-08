package Model;

public class BloodToken {
    private Player owner;

    public BloodToken(Player p){
        owner = p;
    }

    public Player getOwner(){
        return owner;
    }
}
