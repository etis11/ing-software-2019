package controller;

import controller.commandpack.*;
import model.GameManager;
import model.Player;
import model.WeaponCard;
import view.MessageListener;

public class CommandExecutor {

    GameManager gameManager;

    public void CommandExecutor(GameManager gameManager){
        this.gameManager=gameManager;
    }

    public void execute(AskEndTurnCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

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
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

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
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

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

    public void execute (AskPointsCommand command){
        if (command.getPlayer() == null) throw new IllegalArgumentException("Player can't be null");
        int points = command.getPlayer().getPoints();
        command.getOriginView().notify(gameManager.getMatch().getCurrentPlayer().getName()+ " hai: "+points+ "punti");
    }

    public void execute (AskShootCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        boolean loaded = false;
        //verify if almost a weapon is loaded
        for (WeaponCard wpc : currentPlayer.getWeapons()){
            if(wpc.isLoaded()){
                loaded = true;
            }
        }
        if (!currentPlayer.getState().canShoot() || currentPlayer.getRemainingMoves()<1 || !loaded){
            command.getOriginView().notify("Non puoi sparare");
        }
        else {
            currentPlayer.getState().nextState("Shoot", currentPlayer);
            String message = "Il giocatore attuale sta per sparare";
            for (MessageListener view : command.getAllViews()){
                view.notify(message);
            }
            //TODO notifico già le armi?
        }
    }

    public void execute(AskUsePowerUpCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canUsePowerUp() || currentPlayer.getPowerUps().isEmpty()){
            command.getOriginView().notify("Non puoi usare powerup");
        }
        else {
            String message = "Il giocatore attuale sta usando un power up";
            for (MessageListener view : command.getAllViews()){
                view.notify(message);
            }
            command.getOriginView().notify("Scegli quale power up usare tra: "+currentPlayer.powerUpToString());
        }
    }

    public void execute(AskWalkCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canRun() || currentPlayer.getRemainingMoves()<1){
            command.getOriginView().notify("non puoi spostarti");
        }
        else {
            currentPlayer.getState().nextState("Run", currentPlayer);
            String message = "Il giocatore attuale si sta spostando";
            for (MessageListener view : command.getAllViews()){
                view.notify(message);
            }
        }
    }
}
