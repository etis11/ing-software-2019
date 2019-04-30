package controller;

import model.Player;

public abstract class AbstractCommand implements  Command{

    Player owner;

    @Override
    public void execute() {

    }
}
