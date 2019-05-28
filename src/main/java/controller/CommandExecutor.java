package controller;

import controller.commandpack.AskEndTurnCommand;
import controller.commandpack.AskPickCommand;
import controller.commandpack.AskReloadCommand;
import model.Player;
import view.MessageListener;

public class CommandExecutor {

    public void execute(AskEndTurnCommand command){
        //auxiliary variable
        Player currentPlayer = command.getGameManager().getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().isNormalAction() || !currentPlayer.getState().isMoreAction() ||!currentPlayer.getState().isMostAction() ){
            command.getOriginView().notify("Non puoi terminare il tuo turno al momento");
        }
        else {
            currentPlayer.getState().nextState("EndTurn", currentPlayer);
            String message = "Il giocatore attuale ha terminato il suo turno";
            for (MessageListener view : command.getAllViews()){
                view.notify(message);
            }
        }
    }

    public void execute (AskPickCommand command){
        //auxiliary variable
        Player currentPlayer = command.getGameManager().getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canPickUp() || currentPlayer.getRemainingMoves()<1){
            command.getOriginView().notify("Non puoi raccogliere");
        }
        else {
            currentPlayer.setOldState(currentPlayer.getState());
            currentPlayer.getState().nextState("PickUp", currentPlayer);
            String message = "Il giocatore attuale sta raccogliendo";
            for (MessageListener view : command.getAllViews()){
                if (view!=command.getOriginView()) {
                    view.notify(message);
                }
            }
            command.getOriginView().notify("Se vuoi spostarti inserisci la direzione, altrimenti inserisci cosa vuoi raccogliere? (Munizioni o armi)");

        }
    }

    public void execute(AskReloadCommand command){
        //auxiliary variable
        Player currentPlayer = command.getGameManager().getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canReload()){
            command.getOriginView().notify("non puoi ricaricare");
        }
        else {
            currentPlayer.getState().nextState("Reload", currentPlayer);
            String message = "Il giocatore attuale sta ricaricando";
            for (MessageListener view : command.getAllViews()){
                if(view!=command.getOriginView()) {
                    view.notify(message);
                }
            }
            command.getOriginView().notify("Scegli quale arma ricaricare tra: "+currentPlayer.weaponsToString());
        }
    }
}
