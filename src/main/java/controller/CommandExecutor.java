package controller;

import controller.commandpack.*;
import exceptions.InsufficientAmmoException;
import exceptions.NotValidActionException;
import exceptions.PickableNotPresentException;
import model.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CommandExecutor {

    ConcurrentMap<Long, JsonReceiver> listeners;
    /**
     * gameManager is a reference to the model due to access to the match and lobby variables
     */
    GameManager gameManager;

    public CommandExecutor(GameManager gameManager){
        this.gameManager=gameManager;
        this.listeners = new ConcurrentHashMap<>();
    }

    public void execute(AskEndTurnCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().isNormalAction() && !currentPlayer.getState().isMoreAction() &&!currentPlayer.getState().isMostAction() ){
            //command.getOriginView().notify("Non puoi terminare il tuo turno al momento");
        }
        else {
            currentPlayer.getState().nextState("EndTurn", currentPlayer);
            String message = "Il giocatore attuale ha terminato il suo turno";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
        }
    }

    public void execute (AskPickCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canPickUp() || currentPlayer.getRemainingMoves()<1){
//            command.getOriginView().notify("Non puoi raccogliere");
        }
        else {
            currentPlayer.setOldState(currentPlayer.getState());
            currentPlayer.getState().nextState("PickUp", currentPlayer);
            String message = "Il giocatore attuale sta raccogliendo";
//            for (MessageListener view : command.getAllViews()){
//                if (view!=command.getOriginView()) {
//                    view.notify(message);
//                }
//            }
//            command.getOriginView().notify("Se vuoi spostarti inserisci la direzione, altrimenti inserisci cosa vuoi raccogliere. (Munizioni o armi)");

        }
    }

    public void execute(AskReloadCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canReload()){
//            command.getOriginView().notify("non puoi ricaricare");
        }
        else {
            currentPlayer.getState().nextState("Reload", currentPlayer);
            String message = "Il giocatore attuale sta ricaricando";
//            for (MessageListener view : command.getAllViews()){
//                if(view!=command.getOriginView()) {
//                    view.notify(message);
//                }
//            }
//            if (!currentPlayer.getWeapons().isEmpty()) {
//                command.getOriginView().notify("Scegli quale arma ricaricare tra: " + currentPlayer.weaponsToString());
//            }
//            else{
//                command.getOriginView().notify("Non hai armi");
//            }
        }
    }

    public void execute (AskPointsCommand command){
//        if (command.getPlayer() == null) throw new IllegalArgumentException("Player can't be null");
//        int points = command.getPlayer().getPoints();
//        command.getOriginView().notify(gameManager.getMatch().getCurrentPlayer().getName()+ " hai: "+points+ "punti");
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
//            command.getOriginView().notify("Non puoi sparare");
        }
        else {
            currentPlayer.getState().nextState("Shoot", currentPlayer);
            String message = "Il giocatore attuale sta per sparare";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
            //TODO notifico già le armi?
        }
    }

    public void execute(AskUsePowerUpCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canUsePowerUp() || currentPlayer.getPowerUps().isEmpty()){
//            command.getOriginView().notify("Non puoi usare powerup");
        }
        else {
            String message = "Il giocatore attuale sta usando un power up";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
//            command.getOriginView().notify("Scegli quale power up usare tra: "+currentPlayer.powerUpToString());
        }
    }

    public void execute(AskWalkCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (!currentPlayer.getState().canRun() || currentPlayer.getRemainingMoves()<1){
//            command.getOriginView().notify("non puoi spostarti");
        }
        else {
            currentPlayer.getState().nextState("Run", currentPlayer);
            String message = "Il giocatore attuale si sta spostando";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
        }
    }

    public void execute(MoveCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (currentPlayer.getState().getRemainingSteps()<command.getMoves().size()){
//            command.getOriginView().notify("Non hai abbastanze mosse rimanenti");
        }
        else {
            currentPlayer.getState().decrementRemainingSteps(command.getMoves().size());
            currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
            String message = "Il giocatore attuale si è spostato di: "+command.getMoves().size()+" mosse";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
        }

        if(currentPlayer.getState().getName().equals("Run")){
            command.endCommandToAction(gameManager);
        }
    }

    public void execute(PickUpAmmoCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (currentPlayer.getState().getName().equals("PickUp")){
            //TODO verifica tile giusto
            //set remaining steps to zero
            currentPlayer.getState().remainingStepsToZero();

            AmmoCard ammoCard = currentPlayer.getTile().pickUpAmmoCard();
            //draw
            currentPlayer.useAmmoCard(ammoCard, gameManager.getMatch().getPowerUpDeck());
            //put the card in the slush pile
            gameManager.getMatch().getAmmoSlushPile().addCard(ammoCard);

            //notify
            String message = "Il giocatore attuale ha raccolto una carta munizioni";
//            for (MessageListener view : command.getAllViews()){
//                view.notify(message);
//            }
            //decrement moves of player and return to action selector
            command.endCommandToAction(gameManager);
        }
        else{
//            command.getOriginView().notify("Comando non valido");
            //TODO o lancio exception??
        }
    }

    public void execute(PickUpWeaponCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (currentPlayer.getState().getName().equals("PickUp")) {
            //set player remaining steps to zero
            currentPlayer.getState().remainingStepsToZero();

            WeaponCard weaponCard = null;
            int count = 0;
            if (command.getWeaponName() == null) throw new IllegalArgumentException("can't insert null weapon");
            try {
                for (WeaponCard wpc : currentPlayer.getTile().getWeapons()) {
                    if (wpc.getName().equals(command.getWeaponName())) {
                        weaponCard = currentPlayer.getTile().getWeapons().remove(count);
                    }
                    count++;
                }
            } catch (PickableNotPresentException e) {
//                command.getOriginView().notify("non sei in un riquadro contenente armi");
            }
            //TODO come gestisco se voglio raccogleire arma ma non sono nel tile giusto?
            if (weaponCard == null) {
//                command.getOriginView().notify(command.getWeaponName() + " non è tra le armi presenti nel tuo riquadro");
            } else {
                try {
                    currentPlayer.pickUpWeapon(weaponCard);
                } catch (Exception e) {
//                    command.getOriginView().notify("hai più armi di quante consentite, scegline una da scartare tra: "+currentPlayer.weaponsToString());
                } finally {
                    String message = "Il giocatore attuale ha raccolto: " + weaponCard.getName();
//                    for (MessageListener view : command.getAllViews()) {
//                        view.notify(message);
//                    }
                }

            }

            //decrement moves of player and return to action selector
            command.endCommandToAction(gameManager);
        }
        else{
//            command.getOriginView().notify("Comando non valido");
            //TODO o lancio exception??
        }
    }

    public void execute(ReloadCommand command){
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();

        if (command.getWeaponName() == null) throw new IllegalArgumentException("no weapon selected");
        for (WeaponCard wpc : currentPlayer.getWeapons()){
            if (wpc.getName().equals(command.getWeaponName())){
                if(!wpc.isLoaded()) {
                    try {
                        wpc.reload(currentPlayer.getPlayerBoard().getLoader());
                        String message = "Il giocatore attuale ha ricaricato: " + wpc.getName();
//                        for (MessageListener view : command.getAllViews()) {
//                            view.notify(message);
//                        }
//                        command.getOriginView().notify("vuoi ricaricare un'altra arma oppure finire il turno?");
                    } catch (InsufficientAmmoException e) {
//                        command.getOriginView().notify("Non hai le munizioni necessarie per ricaricare ques'arma, vuoi ricaricare un'altra arma oppure finire il turno?");
                    }
                }
                else{
//                    command.getOriginView().notify("L'arma selezionata è già carica, vuoi ricaricare un'altra arma oppure finire il turno?");
                }
            }
        }
    }

    public void execute(SetEffectPhraseCommand command){
        if (!gameManager.getMatch().isStarted()) {
//            if (gameManager.getLobby().getUsers().contains(command.getUser())) {
//                command.getUser().setEffectPhrase(command.getPhrase());
//                command.getOriginView().notify("La tua frase ad effetto è stata modificata");
//            } else {
//                command.getOriginView().notify("Non puoi modificare la tua frase ad effetto");
//            }
        }
        else{
//            command.getOriginView().notify("Non puoi modificare la tua frase perchè la partita è iniziata");
        }
    }

    public void execute(SetNumberOfDeathCommand command){
        if (!gameManager.getMatch().isStarted()) {
//            if (command.getDeath() < 9 && command.getDeath() > 4 && gameManager.getLobby().getUsers().get(0) == command.getOwner()) {
//                gameManager.getMatch().setSkulls(command.getDeath());
//                for (MessageListener ml : command.getAllViews()) {
//                    ml.notify("Il numero di uccisioni per la partita è stato cambiato a: " + command.getDeath());
//                }
//            } else {
//                if (command.getDeath() > 8 || command.getDeath() < 5) {
//                    command.getOriginView().notify("Numero uccisioni non nel target ammissibile");
//                } else {
//                    command.getOriginView().notify("Operazione non consentita");
//                }
//            }
        }
        else{
//            command.getOriginView().notify("Non puoi modificare il numero di morti perchè è iniziata la partita");
        }
    }

    public void execute(SetPlayerNumberCommand command){
        if (!gameManager.getMatch().isStarted()) {
//            if (command.getPlayers() < 6 && command.getPlayers() > 2 && gameManager.getLobby().getUsers().get(0) == command.getOwner()) {
//                gameManager.getMatch().setPlayerNumber(command.getPlayers());
//                for (MessageListener ml : command.getAllViews()) {
//                    ml.notify("Il numero di uccisioni per la partita è stato cambiato a: " + command.getPlayers());
//                }
//            } else {
//                if (command.getPlayers() > 5 || command.getPlayers() < 3) {
//                    command.getOriginView().notify("Numero uccisioni non nel target ammissibile");
//                } else {
//                    command.getOriginView().notify("Operazione non consentita");
//                }
//            }
        }
        else{
//            command.getOriginView().notify("Non puoi modificare il numero di giocatori perchè la partita è già iniziata");
        }
    }

    public void execute(SetUsernameCommand command){
        if (!gameManager.getMatch().isStarted()) {
//            if (gameManager.getLobby().getUserToken().contains(command.getToken())) {
//                gameManager.getLobby().getUserFromToken(command.getToken()).setUsername(command.getUsername());
//                command.getOriginView().notify("Il tuo username è stato modificato in: " + command.getUsername());
            }
//        else {
//                command.getOriginView().notify("Non puoi modificare il tuo username");
//            }
//        }
//        else{
//            command.getOriginView().notify("Non puoi modificare il tuo username perchè la partita è già iniziata");
//        }
    }


    public void execute(CreateUserCommand command){
//        if (!gameManager.getMatch().isStarted()) {
//            //User user = new User(command.getUsername(), command.getToken());

//            try {
//                gameManager.getLobby().join(user);
//            } catch (NotValidActionException e) {
//                e.printStackTrace();
//            }
//        }
//        else{
////            command.getOriginView().notify("Non puoi unirti alla partita perchè è già iniziata");
//        }

    }

    public void execute(SetTokenCommand command){

    }
}
