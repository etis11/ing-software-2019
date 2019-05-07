package controller.commandpack;

import View.AbstractView;

import java.util.List;

public abstract class AbstractCommand implements Command {

    private AbstractView originView;
    private List<AbstractView> allViews;

    public AbstractCommand(AbstractView originView, List<AbstractView> allViews){
        this.originView = originView;
        this.allViews = allViews;
    }

    public AbstractCommand(){

    }

    @Override
    public void execute() {

    }
}
