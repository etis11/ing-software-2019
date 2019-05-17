package model;

import controller.MatchCommandExecutor;
import controller.commandpack.AskPickCommand;
import controller.commandpack.Command;
import view.MessageListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class gameCreator {

    public static void main(String[] args) {
        Match match = new Match();
        MessageListener m = (s) -> {System.out.println(s);};
        List<MessageListener> views =  new ArrayList<MessageListener>();
        views.add(m);
        Player p = new Player("Passoni", State.fromJson("./src/main/resources/stateMachine/stateMachine.json"));
        p.getState().nextState("NormalAction", p);
        p.setRemainingMoves(1);
        Command c = new AskPickCommand(match, m, views);

        List<Player> l = new LinkedList<>();
        l.add(p);
        match.setPlayers(l);

        MatchCommandExecutor exe = new MatchCommandExecutor(match);
        Thread t =  new Thread(() -> exe.executeCommand());
        t.setDaemon(false);
        t.start();

        exe.addCommand(c);

    }
}
