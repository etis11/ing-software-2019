package controller;

import controller.commandpack.AskEndTurnCommand;
import view.MessageListener;

public class CommandExecutor {

    public void execute(AskEndTurnCommand command){
        if (!command.getGameManager().getMatch().getCurrentPlayer().getState().isNormalAction() || !gameManager.getMatch().getCurrentPlayer().getState().isMoreAction() ||!gameManager.getMatch().getCurrentPlayer().getState().isMostAction() ){
            originView.notify("Non puoi terminare il tuo turno al momento");
        }
        else {
            command.getGameManager().getMatch().getCurrentPlayer().getState().nextState("EndTurn", gameManager.getMatch().getCurrentPlayer());
            String message = "Il giocatore attuale ha terminato il suo turno";
            for (MessageListener view : allViews){
                view.notify(message);
            }
        }
    }
}
