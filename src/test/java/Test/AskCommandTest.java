package Test;

import controller.commandpack.AskPickCommand;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.MessageListener;


import java.util.*;

public class AskCommandTest  {

    private Match match;
    private Player player;
    private State state, stateA;
    LinkedList<Player> players;
    AskPickCommand command;
    MessageListener messageListener;
    MessageListener messageListener2;
    List<MessageListener> views;
    Map<String, State> next;



    @BeforeEach
    void init(){
        state = new State("pickUp", 1, false, false, false, false, true, false, false, false, false, false, null);
        next = new HashMap<>();
        next.put("PickUp", state);
        stateA = new State("Action", 1, false, false, false, false, true, false, false, false, false, false, next);
        player = new Player("Pippo");
        player.setState(stateA);
        match = new Match();
        players = new LinkedList<>();
        players.add(player);
        match.setPlayers(players);
        messageListener = (s)->{System.out.println(s);return s;};
        messageListener2 = (s)->{System.out.println(s);return s;};
        views = new ArrayList<>();
        views.add(messageListener);
        views.add(messageListener2);
    }

    @Test
    void commandTest(){
        command = new AskPickCommand(match, messageListener, views);
        command.execute();
        player.setRemainingMoves(1);
        command.execute();
    }

}
