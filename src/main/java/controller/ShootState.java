package controller;

import java.util.HashMap;
import java.util.Map;

public enum ShootState {

    BASE(),
    ASKEDSHOOT(),
    CHOSENWEAPON(),
    CHOSENEFFECT(),
    MOVEEFFECTBASE(),
    MOVEEFFECTOPTIONAL(),
    TARGETASKED(),
    APPLYEFFECTDAMAGE(),
    MOVESECONDLY(),
    ENDSHOOT();

}
