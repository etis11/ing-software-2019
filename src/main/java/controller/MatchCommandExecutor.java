package controller;

import exceptions.NotValidActionException;
import model.Match;
import model.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MatchCommandExecutor extends AbstractCommandExecutor{

    public MatchCommandExecutor(Match match) {
        super(match);
    }
}
