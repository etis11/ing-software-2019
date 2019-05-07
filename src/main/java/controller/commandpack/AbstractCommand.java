package controller.commandpack;

import model.Player;

public abstract class AbstractCommand implements Command {

    public Player owner;

    @Override
    public void execute() {

    }
}
