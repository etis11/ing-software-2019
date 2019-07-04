package Test;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import model.Effect;

import static org.junit.jupiter.api.Assertions.*;

public class EffectTest {

private Effect effect;
private Match match;
    private Map<String, Integer> damage;
    private Map<String, Integer> marks;

@BeforeEach
    void testEffect() {
    effect = new Effect();
    damage = new HashMap<>();
    marks = new HashMap<>();
    }

    @Test
    public void Effectus(){

        damage.put("red",1);
        damage.put("blue", 1);
        damage.put("green", 0);
        marks.put("red", 2);
        marks.put("blue", 0);
        marks.put("green", 1);

        effect.setMarks(marks);

        assertSame(effect.getMarks().size(),3);
        assertNotNull(effect.getMarks());

    }
}
