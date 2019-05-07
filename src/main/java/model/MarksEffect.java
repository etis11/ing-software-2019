package model;

import java.util.List;

public class MarksEffect extends Effect {
    int marks;
    public  MarksEffect(int marks, boolean isGlobal, boolean isOptional){
        this.marks=marks;
        super.setGlobal(isGlobal);
        super.setOptional(isOptional);
    }
    @Override
    public void useEffect(List<Player> p) {

    }
}
