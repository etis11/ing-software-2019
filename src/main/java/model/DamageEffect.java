package model;

import java.util.List;

public class DamageEffect extends Effect {
    private int damage;


    public DamageEffect(int damage,boolean isGlobal,boolean isOptional){
        super(null, null); //TODO aggiunto
        this.damage=damage;
        //TODO commenatato super.setGlobal(isGlobal);
        //TODO commenatato super.setOptional(isOptional);
    }

    //TODO commenatato
    /*@Override
    public void useEffect(List<Player> p) {

    }*/
}
