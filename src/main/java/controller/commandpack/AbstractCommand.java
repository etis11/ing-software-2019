package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;

import java.io.Serializable;

public abstract class AbstractCommand implements Command, Serializable {

    protected String token;


    public AbstractCommand(String token){
        this.token = token;
    }

    public AbstractCommand(){

    }


    public String getToken(){return token;}

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
