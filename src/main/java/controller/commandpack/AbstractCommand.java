package controller.commandpack;

import view.MessageListener;
import model.Match;

import java.util.List;

public abstract class AbstractCommand implements Command {

    protected MessageListener originView;
    protected List<MessageListener> allViews;
    protected Match match;

    public AbstractCommand(Match match, MessageListener originView, List<MessageListener> allViews){
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
