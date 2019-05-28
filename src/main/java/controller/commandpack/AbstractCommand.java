package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;

import java.util.List;

public abstract class AbstractCommand implements Command{

    protected MessageListener originView;
    protected List<MessageListener> allViews;
    protected GameManager gameManager;

    public AbstractCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews){
        this.gameManager = gameManager;
        this.originView = originView;
        this.allViews = allViews;
    }

    public AbstractCommand(){

    }

    public GameManager getGameManager(){
        return gameManager;
    }

    public MessageListener getOriginView() {
        return originView;
    }

    public List<MessageListener> getAllViews() {
        return allViews;
    }

    void endCommandToAction(){
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
