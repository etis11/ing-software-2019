package Test;

import controller.MatchCommandExecutor;
import controller.commandpack.Command;
import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class CommandExecutorTest {

    private MatchCommandExecutor commandExecutor;

    @BeforeEach
    void initCommandExecutor(){
        Match m = new Match();
        commandExecutor = new MatchCommandExecutor(m);
    }

    @Test
    void testMatchExecutor() {
        Command c = () -> {};
        Thread t = new Thread(() -> commandExecutor.executeCommand());
        t.setDaemon(false);
        t.start();

        for (int i = 0; i < 10; i++){
            Thread t2 = new Thread(()->{commandExecutor.addCommand(c);} );
            t2.setName("Thread "+ i);
            t2.start();
         }


    }
}
