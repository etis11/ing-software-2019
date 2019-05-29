package model;

import java.util.List;

public class MarksEffect extends Effect {
    int marks;
    public  MarksEffect(int marks, boolean isGlobal, boolean isOptional){
        super(null, null);//TODO aggiunto
        this.marks=marks;
        //TODO commenatato super.setGlobal(isGlobal);
        //TODO commenatato super.setOptional(isOptional);
    }
    //TODO commentato
    /*@Override
    public void useEffect(List<Player> p) {

    }*/
}
