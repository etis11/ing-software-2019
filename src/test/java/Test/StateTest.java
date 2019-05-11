package Test;

import model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static model.State.fromJson;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    Player player;

    @BeforeAll
    public void init(){
        String path = "." + File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "stateMachine"+File.separatorChar+"stateMachine.json";
        player = new Player();
        player.setState(fromJson(path));
    }

    @Test
    public void initTest(){
        assertTrue(player.getState().getName().equals("EndTurn"));
    }


}
