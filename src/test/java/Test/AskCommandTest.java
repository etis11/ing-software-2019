package Test;

import controller.commandpack.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.MessageListener;


import java.awt.*;
import java.util.*;
import java.util.List;

public class AskCommandTest  {

    private Match match;
    private Player player;
    LinkedList<Player> players;
    AbstractCommand command;
    MessageListener messageListener;
    MessageListener messageListener2;
    List<MessageListener> views;
    WeaponCard w;
    PowerUpCard p;


    @BeforeEach
    void init(){
        player = new Player("Pippo", State.fromJson("./src/main/resources/stateMachine/stateMachine.json"));
        match = new Match();
        players = new LinkedList<>();
        players.add(player);
        match.setPlayers(players);
        messageListener = (s)->{System.out.println(s);return s;};
        messageListener2 = (s)->{System.out.println(s);return s;};
        views = new ArrayList<>();
        views.add(messageListener);
        views.add(messageListener2);
        w = new WeaponCard();
        try {
            player.pickUpWeapon(w);
        } catch (Exception e) {
            e.getMessage();
        }
        p = new PowerUpCard(Color.RED, PowerUpType.TELEPORTER);
        player.pickUpPowerUp(p);


    }

    @Test
    void commandTest(){
        player.getState().nextState("NormalAction", player);
        command = new AskPickCommand(match, messageListener, views);
        command.execute();
        player.setRemainingMoves(1);
        command.execute();
        player.setRemainingMoves(0);

        player.getState().nextState("NormalAction", player);

        command = new AskWalkCommand(match, messageListener, views);
        command.execute();
        player.setRemainingMoves(1);
        command.execute();
        player.setRemainingMoves(0);

        player.getState().nextState("NormalAction", player);

        command = new AskShootCommand(match, messageListener, views);
        command.execute();
        player.setRemainingMoves(1);
        command.execute();
        player.setRemainingMoves(0);
        command = new AskUsePowerUpCommand(match, messageListener, views);
        command.execute();

        player.getState().nextState("NormalAction", player);

        command = new AskReloadCommand(match, messageListener, views);
        command.execute();
    }

}
