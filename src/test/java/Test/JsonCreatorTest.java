package Test;

import model.JsonCreator;
import model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonCreatorTest {

    private JsonCreator jsonCreator;

    @BeforeEach
    void initJsonCreator(){
        jsonCreator = new JsonCreator();
    }

    @Test
    void correctPlayerFormat(){
        Player gigino = new Player("Gigino");
        Player pinotto = new Player("Pinotto");
        jsonCreator.notifyPlayerChange(gigino);
        jsonCreator.notifyPlayerChange(pinotto);

        System.out.println(jsonCreator.createJsonWithMessage("gigino e pinotto"));
    }
}
