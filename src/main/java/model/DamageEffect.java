package model;

import java.util.List;

public class DamageEffect extends Effect {
    private int damage;


    public DamageEffect(int damage,boolean isGlobal,boolean isOptional){
        this.damage=damage;
        super.setGlobal(isGlobal);
        super.setOptional(isOptional);
    }

    @Override
    public void useEffect(List<Player> p) {

    }
}
