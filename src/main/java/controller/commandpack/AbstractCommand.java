package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCommand implements Command, Serializable {

    protected MessageListener originView;
    protected List<MessageListener> allViews;
    protected long token;

    public AbstractCommand(MessageListener originView, List<MessageListener> allViews){
        this.originView = originView;
        this.allViews = allViews;
    }

    public AbstractCommand(long token){
        this.token = token;
    }

    public AbstractCommand(){

    }

    public MessageListener getOriginView() {
        return originView;
    }

    public List<MessageListener> getAllViews() {
        return allViews;
    }

    public long getToken(){return token;}

    public void endCommandToAction(GameManager gameManager){
        gameManager.getMatch().getCurrentPlayer().decrementMoves();
        gameManager.getMatch().getCurrentPlayer().getState().resetRemainingSteps();
        gameManager.getMatch().getCurrentPlayer().getState().nextState(gameManager.getMatch().getCurrentPlayer().getOldState().getName(), gameManager.getMatch().getCurrentPlayer());
        gameManager.getMatch().getCurrentPlayer().setOldState(null);
    }

    @Override
    public void execute(CommandExecutor exe) {
        System.out.println("ERRORE");
    }


}
