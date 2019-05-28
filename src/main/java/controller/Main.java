package controller;

import controller.commandpack.AskEndTurnCommand;
import controller.commandpack.AskPickCommand;
import controller.commandpack.AskReloadCommand;
import model.GameManager;
import model.Match;
import model.Player;
import model.State;
import view.MessageListener;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MessageListener messageView = (s) -> System.out.println(s);
        List<MessageListener> listViews = new LinkedList<>();
        listViews.add(messageView);
        GameManager gm = new GameManager();
        Match m = gm.getMatch();
        String path = "." + File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "stateMachine"+File.separatorChar+"stateMachine.json";
        State s = State.fromJson(path);
        Player p = new Player("Oscar", s);
        p.setRemainingMoves(2);
        p.getState().nextState("NormalAction", p);
        List<Player> players = new LinkedList<>();
        players.add(p);
        m.setPlayers(players);
        CommandLauncher launcher = new CommandLauncher(m);
        AskEndTurnCommand endCommand = new AskEndTurnCommand(messageView, listViews);
        AskReloadCommand reloadCommand = new AskReloadCommand(messageView, listViews);
        AskPickCommand pickCommand = new AskPickCommand(messageView, listViews);

        launcher.addCommand(pickCommand);
        launcher.addCommand(reloadCommand);
        launcher.addCommand(endCommand);

        launcher.executeCommand();
    }
}
