package controller;

import java.util.Map;

public enum ShootState {

    BASE(null),
    ASKEDSHOOT(null),
    CHOSENWEAPON(null),
    CHOSENEFFECT(null),
    MOVEEFFECTBASE(null),
    MOVEEFFECTOPTIONAL(null),
    TARGETASKED(null),
    APPLYEFFECTDAMAGE(null),
    MOVESECONDLY(null),
    ENDSHOOT(null);

    private Map<String, ShootState> nextPossibleState;

    private ShootState(Map<String, ShootState> nextPossibleState){
        this.nextPossibleState = nextPossibleState;
    }

    public Map<String, ShootState> getNextPossibleState(){
        return nextPossibleState;
    }
}
