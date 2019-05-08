package controller.commandpack;

import View.AbstractView;
import model.Match;

import java.util.List;

public class MoveCommand extends AbstractCommand {


    public MoveCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        super(match, originView, allViews);
        //TODO mancano parametri per creare movimento e controlli booleani
    }

    @Override
    public void execute() {
        //TODO
    }
}
