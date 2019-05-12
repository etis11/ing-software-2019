package controller.commandpack;

import view.AbstractView;
import model.Match;

import java.util.List;

public abstract class AbstractCommand implements Command {

    protected AbstractView originView;
    protected List<AbstractView> allViews;
    protected Match match;

    public AbstractCommand(Match match, AbstractView originView, List<AbstractView> allViews){
        this.match = match;
        this.originView = originView;
        this.allViews = allViews;
    }

    public AbstractCommand(){

    }

    @Override
    public void execute() {

    }
}
