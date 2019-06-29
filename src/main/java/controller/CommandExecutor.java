package controller;

import controller.commandpack.*;
import exceptions.*;
import javafx.scene.paint.Color;
import model.*;
import network.TokenRegistry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {
    private final TokenRegistry registry = TokenRegistry.getInstance();
    private final static Logger commandExecutorLogger = Logger.getLogger(CommandExecutor.class.getName());
    private final int seconds = 1;
    private ShootState shootState;
    private WeaponCard weaponToUse;
    private List<OptionalEffect> opt;
    private List<Player> targets;
    private Effect base;

    /**
     * gameManager is a reference to the model due to access to the match and lobby variables
     */
    private GameManager gameManager;

    private final  JsonCreator jsonCreator;

    private final Notifier notifier;

    public CommandExecutor(GameManager gameManager, JsonCreator jsonCreator, CommandLauncherInterface launcherInterface) {
        this.gameManager = gameManager;
        this.shootState = ShootState.BASE;
        this.weaponToUse = null;
        this.opt = new ArrayList<>();
        this.targets = new ArrayList<>();
        base = null;
        this.jsonCreator = jsonCreator;
        notifier = new JsonNotifier(jsonCreator, launcherInterface, gameManager);
    }



    public void execute(AskEndTurnCommand command) throws IOException {
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (hasMatchStarted(gameManager)) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error = "Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                //verify the player state
                if (!currentPlayer.getState().isNormalAction() && !currentPlayer.getState().isMoreAction() && !currentPlayer.getState().isMostAction()) {
                    String error ="Non puoi terminare il tuo turno al momento";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.getState().nextState("EndTurn", currentPlayer);
                    String message = currentPlayer.getName()+" ha terminato il suo turno";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            notifyToAllExceptCurrent(js, userJsonReceiver, message);
                        }
                    }
                    String messageToSend = "Hai terminato il tuo turno";
                    notifier.notifyMessageTargetPlayer(messageToSend, userJsonReceiver, currentPlayer);
                    commandExecutorLogger.log(Level.INFO, "End round for player "+currentPlayer.getName());
                    //TODO prova, non so se metterli qua o no
                    gameManager.getMatch().endRound();
                    gameManager.getMatch().newRound();
                    currentPlayer = gameManager.getMatch().getCurrentPlayer();
                    commandExecutorLogger.log(Level.INFO, "Start round for player "+currentPlayer.getName());
                    JsonReceiver userToBeNotifiedThrow = null;
                    for(JsonReceiver jr : command.getAllReceivers()){
                        User userToBeNotified= TokenRegistry.getInstance().getJsonUserOwner(jr);
                        if(userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
                            userToBeNotifiedThrow = jr;
                        }
                    }
                    notifier.notifyMessageTargetPlayer("", userToBeNotifiedThrow, currentPlayer);
                    if((currentPlayer.getState().getName().equals("EndTurn")&& currentPlayer.getTile() == null) || currentPlayer.getState().getName().equals("Dead") || currentPlayer.getState().getName().equals("Overkilled")) {
                        notifier.notifyMessageTargetPlayer("scegli quale powerup scartare per spawnare", userToBeNotifiedThrow, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Asked throwing for spawn to"+currentPlayer.getName());
                    }
                }
            }
        }
        else{
            String error = "La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();

    }

    public void execute(AskPickCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error = "Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                //verify the state
                if (!currentPlayer.getState().canPickUp() || currentPlayer.getRemainingMoves() < 1) {
                    String error = "Non puoi raccogliere";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("PickUp", currentPlayer);
                    String message = currentPlayer.getName()+" vuole raccogliere";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            notifier.notifyMessage(message, js);
                        }
                    }
                    notifier.notifyMessage(
                            "Se vuoi spostarti inserisci la direzione, altrimenti inserisci none, " +
                                    "raccoglierai automaticamente se ci sono munizioni", userJsonReceiver);
                    commandExecutorLogger.log(Level.INFO, "Asked move for pick up to "+currentPlayer.getName());
                }
            }
        }
        else{
            String error = "La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(AskReloadCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error = "Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                //verify the state
                if (!currentPlayer.getState().canReload()) {
                    String error = "Non puoi ricaricare";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.getState().nextState("Reload", currentPlayer);
                    boolean hasWeapon = currentPlayer.getWeapons().isEmpty();
                    //verify if the player has weapon
                    if (!hasWeapon) {
                        String message = "Il giocatore attuale sta ricaricando";
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                notifier.notifyMessage(message, js);
                            }
                        }
                        notifier.notifyMessage("Scegli quale arma ricaricare tra: " + currentPlayer.weaponsToString(),
                                userJsonReceiver);
                        commandExecutorLogger.log(Level.INFO, "Asked weapon to reload to "+currentPlayer.getName());
                    } else {
                        String error = "Non hai armi da ricaricare";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
            }
        }
        else{
            String error = "La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(AskPointsCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            int points = owner.getPoints();
            String message = owner.getName() + " hai: " + points + "punti";
            notifier.notifyMessage(message, userJsonReceiver);
            commandExecutorLogger.log(Level.INFO, "Notified points to "+owner.getName());
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(AskShootCommand command) throws IOException {
        //todo manca il movimento
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                boolean loaded = false;
                //verify if almost a weapon is loaded
                for (WeaponCard wpc : currentPlayer.getWeapons()) {
                    if (wpc.isLoaded()) {
                        loaded = true;
                    }
                }
                //verify the state
                if (!currentPlayer.getState().canShoot() || !shootState.equals(ShootState.BASE)|| currentPlayer.getRemainingMoves() < 1 || !loaded) {
                    String error ="Non puoi sparare";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("Shoot", currentPlayer);
                    shootState = ShootState.ASKEDSHOOT;
                    String message = currentPlayer.getName()+" sta per sparare";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        notifyToAllExceptCurrent(js, userJsonReceiver, message);
                    }
                    notifier.notifyMessageTargetPlayer("Muoviti o scegli con quale arma sparare tra: "
                            + currentPlayer.weaponsToString(), userJsonReceiver, currentPlayer);
                    commandExecutorLogger.log(Level.INFO, "Asked move to shoot to "+currentPlayer.getName());
                }
            }
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(AskUsePowerUpCommand command) throws IOException {
        //todo va rivisto
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner can use powerup
            if (!owner.getState().canUsePowerUp() || owner.getPowerUps().isEmpty()) {
                String error ="Non puoi usare powerup";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                String message = "Il giocatore attuale sta usando un power up";
                for (JsonReceiver js : command.getAllReceivers()) {
                    if (js != userJsonReceiver) {
                        notifier.notifyMessage(message, js);
                    }
                }
                notifier.notifyMessage("Scegli quale power up usare tra: " + currentPlayer.powerUpToString(),
                        userJsonReceiver);
            }
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(AskWalkCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                //verify the state
                if (!currentPlayer.getState().canRun() || currentPlayer.getRemainingMoves() < 1) {
                    String error ="Non puoi muoverti";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("Run", currentPlayer);
                    String message = currentPlayer.getName()+" si sta spostando";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            notifier.notifyMessage(message, js);
                        }
                    }
                    notifier.notifyMessage( "Inserisci le mosse che vuoi fare: (up, down, left, right)", userJsonReceiver);
                    commandExecutorLogger.log(Level.INFO, "Asked move to run to "+currentPlayer.getName());

                }
            }
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(MoveCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                String state = currentPlayer.getState().getName();
                if(state.equals("Run")||state.equals("PickUp")||state.equals("PickUpPlus")|| state.equals("Shoot")||state.equals("ShootPlus")) {
                    if (!currentPlayer.getState().canRun() && currentPlayer.getState().getRemainingSteps() < command.getMoves().size()) {
                        String error ="Non hai abbastanza mosse rimanenti";
                        notifier.notifyError(error, userJsonReceiver);
                    } else {
                        try {
                            currentPlayer.setOldTile(currentPlayer.getTile());
                            currentPlayer.getState().decrementRemainingSteps(command.getMoves().size());
                            currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
                            String message = currentPlayer.getName() + " si è spostato di nel tile: " + currentPlayer.getTile().getID();
                            if (currentPlayer.getState().getName().equals("Run")) {
                                for (JsonReceiver js : command.getAllReceivers()) {
                                    notifyToAllExceptCurrent(js, userJsonReceiver, message);
                                }
                                notifier.notifyMessageTargetPlayer("Ti sei spostato nel tile :" + currentPlayer.getTile().getID()
                                        , userJsonReceiver, currentPlayer);
                                commandExecutorLogger.log(Level.INFO, "Run of  "+currentPlayer.getName()+" correctly done");
                                command.endCommandToAction(gameManager);
                            }
                            if (currentPlayer.getState().canPickUp() && currentPlayer.getTile().canContainAmmo() && (currentPlayer.getState().getName().equals("PickUp")|| currentPlayer.getState().getName().equals("PickUpPlu"))) {
                                if (currentPlayer.getTile().isPresentAmmoCard()) {
                                    currentPlayer.getState().remainingStepsToZero();

                                    AmmoCard ammoCard = currentPlayer.getTile().pickUpAmmoCard();
                                    //draw
                                    currentPlayer.useAmmoCard(ammoCard, gameManager.getMatch().getPowerUpDeck());
                                    //put the card in the slush pile
                                    gameManager.getMatch().getAmmoSlushPile().addCard(ammoCard);
                                    //notifyMessage
                                    String message2 = currentPlayer.getName() + " si è spostato nel tile " + currentPlayer.getTile().getID() + " ha raccolto una carta munizioni";
                                    for (JsonReceiver js : command.getAllReceivers()) {
                                        notifyToAllExceptCurrent(js, userJsonReceiver, message2);
                                    }
                                    notifier.notifyMessageTargetPlayer("Ti sei spostato nel tile " + currentPlayer.getTile().getID()
                                            + " e hai raccolto una carta munizioni", userJsonReceiver, currentPlayer);
                                    commandExecutorLogger.log(Level.INFO, "Picked up an ammo from "+currentPlayer.getName());
                                    command.endCommandToAction(gameManager);
                                } else {
                                    //return to old state
                                    returnOldState(currentPlayer, userJsonReceiver, "Non c'è nulla da raccogliere in questo tile");
                                    return;
                                }
                            }
                            //pick up state into weapon tile
                            else if (currentPlayer.getState().canPickUp() && currentPlayer.getTile().canContainWeapons() && (currentPlayer.getState().getName().equals("PickUp")|| currentPlayer.getState().getName().equals("PickUpPlu"))) {
                                notifier.notifyMessageTargetPlayer("Seleziona quale arma raccogliere", userJsonReceiver, currentPlayer);
                            }
                            //choose weapon to shoot after movement
                            else if(currentPlayer.getState().canShoot() && shootState.equals(ShootState.ASKEDSHOOT)&&(currentPlayer.getState().getName().equals("Shoot") ||currentPlayer.getState().getName().equals("ShootPlus")) ){
                                notifier.notifyMessageTargetPlayer("Seleziona con quale arma sparare", userJsonReceiver, currentPlayer);
                                System.out.println(currentPlayer.getOldTile());
                            }
                            //shooter movement
                            else if (currentPlayer.getState().canShoot() && shootState.equals(ShootState.CHOOSEBASE)){
                                //verify if the shooter can move before choose target
                                if((base == null && !weaponToUse.getBaseEffect().get(0).canMoveShooter())||(base != null && !base.canMoveShooter())){
                                    undoMovement(currentPlayer, userJsonReceiver,"Puoi soltanto scegliere chi colpire");
                                }
                                else{
                                   verfyMoveShooter(currentPlayer,userJsonReceiver, command);
                                }
                            }
                            //movement caused by optional effect
                            else if (currentPlayer.getState().canShoot() && shootState.equals(ShootState.CHOSENEFFECT)){
                                if (!opt.isEmpty() && !canMoveOpt()){
                                    undoMovement(currentPlayer, userJsonReceiver,"Puoi soltanto scegliere chi colpire");
                                }
                                else if(!opt.isEmpty()){
                                    shootState = ShootState.MOVEEFFECTOPTIONAL;
                                }
                                verfyMoveShooter(currentPlayer, userJsonReceiver, command);
                            }
                        } catch (NotValidMovesException e) {
                            currentPlayer.getState().resetRemainingSteps();
                            String error ="Movimento non valido";
                            notifier.notifyError(error, userJsonReceiver);
                        }
                    }
                }
                else
                {
                    String error ="Azione non valida";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(PickUpCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                //verify the state
                if (currentPlayer.getState().canPickUp() && currentPlayer.getNumWeapons()<4 && (currentPlayer.getState().getName().equals("PickUp")||currentPlayer.getState().getName().equals("PickUpPlus"))) {
                    //verify if it is a weapon tile
                    if (currentPlayer.getTile().canContainWeapons()) {
                        //verify if there are weapon in the tile
                        if (!currentPlayer.getTile().getWeapons().isEmpty()) {
                            //set player remaining steps to zero
                            currentPlayer.getState().remainingStepsToZero();
                            System.out.println("Armi del giocatore: "+currentPlayer.weaponsToString());
                            WeaponCard weaponCard = null;
                            int count = 0;
                            if (command.getWeaponName() == null) {
                                //return to old state
                                returnOldState(currentPlayer, userJsonReceiver, "Non hai inserito quale arma raccogliere");
                                return;
                            } else {
                                try {
                                    for (WeaponCard wpc : currentPlayer.getTile().getWeapons()) {
                                        if (wpc.getName().equals(command.getWeaponName())) {
                                            weaponCard = currentPlayer.getTile().getWeapons().remove(count);
                                        }
                                        count++;
                                    }
                                } catch (PickableNotPresentException e) {
                                    //return to old state
                                    returnOldState(currentPlayer, userJsonReceiver, "Non sono presenti armi in questo tile");
                                    return;
                                }
                                //if the weapon selected was not present
                                if (weaponCard == null) {
                                    String error ="Non è presente l'arma da te inserita, inseriscine una valida";
                                    notifier.notifyError(error, userJsonReceiver);
                                }
                                else {
                                    try {
                                        currentPlayer.pickUpWeapon(weaponCard);
                                        String message = currentPlayer.getName()+" ha raccolto: " + weaponCard.getName();
                                        for (JsonReceiver js : command.getAllReceivers()) {
                                            notifyToAllExceptCurrent(js, userJsonReceiver, message);
                                        }
                                        notifier.notifyMessageTargetPlayer("Hai raccolto " + weaponCard.getName(), userJsonReceiver, currentPlayer);
                                        commandExecutorLogger.log(Level.INFO, "Picked up "+weaponCard.getName()+" from "+currentPlayer.getName());
                                        //decrement moves of player and return to action selector
                                        command.endCommandToAction(gameManager);
                                    } catch (IllegalHavingException e) {
                                        String error ="Hai più armi di quante consentite, scegline una da scartare tra: " + currentPlayer.weaponsToString();
                                        notifier.notifyError(error, userJsonReceiver);
                                        commandExecutorLogger.log(Level.INFO, "Asked thorwing a weapon to "+currentPlayer.getName());
                                    }
                                    catch (InsufficientAmmoException e){
                                        //return to old state
                                        returnOldState(currentPlayer, userJsonReceiver, "Non hai sufficienti munizioni per raccogliere l'arma");
                                        return;
                                    }
                                }

                            }
                        }
                        else {
                            //return to old state
                            returnOldState(currentPlayer, userJsonReceiver,"Nel tuo tile non c'è niente da raccogliere");
                        }
                    }
                }
                else {
                    String error ="Comando non valido";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        }
        else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(ThrowWeaponCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                if(currentPlayer.getWeapons().size()>3 && (currentPlayer.getState().getName().equals("PickUp")||currentPlayer.getState().getName().equals("PickUpPlus"))){
                    WeaponCard toThrow = currentPlayer.hasWeapon(command.getWeaponToThrow());
                    if(toThrow!=null){
                        currentPlayer.throwWeaponCard(toThrow);
                        String message = currentPlayer.getName()+" ha scartato: "+command.getWeaponToThrow();
                        for (JsonReceiver js : command.getAllReceivers()) {
                            notifyToAllExceptCurrent(js, userJsonReceiver, message);
                        }
                        notifier.notifyMessageTargetPlayer("Hai scartato: "+command.getWeaponToThrow(), userJsonReceiver, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Thrown "+toThrow.getName()+" from "+currentPlayer.getName());
                    }
                    else{
                        String error ="Non hai quest'arma";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
                else{
                    String error ="Non devi scartare nessun'arma";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        }
        else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();

    }

    public void execute(ReloadCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                if (command.getWeaponName() == null) {
                    for (WeaponCard wpc : currentPlayer.getWeapons()) {
                        if (wpc.getName().equals(command.getWeaponName())) {
                            if (!wpc.isLoaded()) {
                                try {
                                    wpc.reload(currentPlayer.getPlayerBoard().getLoader());
                                    String message = currentPlayer.getName()+" ha ricaricato: " + wpc.getName();
                                    for (JsonReceiver js : command.getAllReceivers()) {
                                        if (js != userJsonReceiver) {
                                            notifier.notifyMessage(message, js);
                                        }
                                    }
                                    notifier.notifyMessage("Arma ricaricata, vuoi ricaricare un'altra arma o finire il turno?", userJsonReceiver);
                                    commandExecutorLogger.log(Level.INFO, "Asekd for new relaod to "+currentPlayer.getName());
                                } catch (InsufficientAmmoException e) {
                                    String error ="Non hai abbastanza munizioni per ricricare l'arma selezionata";
                                    notifier.notifyError(error, userJsonReceiver);
                                }
                            } else {
                                String error ="L'arma selezionata è già carica, vuoi ricaricare un'altra arma oppure finire il turno?";
                                notifier.notifyError(error, userJsonReceiver);
                            }
                        }
                    }
                }
                else{
                    String error ="Non hai fornito il nome dell'arma";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        }
        else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SpawnCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else{
                String state = currentPlayer.getState().getName();
                //verify the player state
                if ((state.equals("EndTurn")&& currentPlayer.getTile() == null) || state.equals("Dead") ||state.equals("Overkilled")) {
                    //verify if the current player has a powerup
                    if (!currentPlayer.hasPowerUp(powerUpParser(command.getPowerUpType()), colorParser(command.getColor()))) {
                        String error ="Non hai questo PowerUp";
                        notifier.notifyError(error, userJsonReceiver);
                    } else {
                        String regenPointColor = command.getColor();
                        Tile tileToSpawn = gameManager.getMatch().getMap().getRegenPoint(translateColor(regenPointColor));
                        tileToSpawn.addPlayer(currentPlayer);
                        currentPlayer.getState().nextState("NormalAction", currentPlayer);
                        PowerUpCard toThrow= currentPlayer.getPowerUp(powerUpParser(command.getPowerUpType()), colorParser(command.getColor()));
                        try {
                            gameManager.getMatch().addPowerUpToSlush(currentPlayer.throwPowerUp(toThrow));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String message = currentPlayer.getName() + " si è rigenerato nel punto di rigenerazione" + regenPointColor;
                        for (JsonReceiver js : command.getAllReceivers()) {
                            notifyToAllExceptCurrent(js, userJsonReceiver, message);
                        };
                        notifier.notifyMessageTargetPlayer("Ti sei rigenerato nel punto di rigenerazione " + regenPointColor, userJsonReceiver, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Spawn of "+currentPlayer.getName());
                    }
                }
                else{
                    String error ="Azione non consentita";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        }else{
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(WeaponCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            }
            else{
                if(shootState.equals(ShootState.ASKEDSHOOT)){
                    //attributes the weapon to shoot
                    for(WeaponCard wc: currentPlayer.getWeapons()){
                        if(wc.getName().equals(command.getWeaponName())){
                            weaponToUse =wc;
                            System.out.println("Scelta arma: "+weaponToUse.getName());
                        }
                    }
                    //verify if the shooter owns the weapon and if it is loaded
                    if(weaponToUse != null && weaponToUse.isLoaded()){
                        shootState = ShootState.CHOSENWEAPON;
                        String message = "Hai scelto di sparare con: "+weaponToUse.getName();
                        notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Choose to shoot with "+weaponToUse.getName());
                    }
                    else{
                       if(weaponToUse == null){
                           String error ="Non hai quest'arma, scegline una che possiedi";
                           notifier.notifyError(error, userJsonReceiver);
                       }
                       else{
                           String error ="Quest'arma non è carica scegline una carica";
                           notifier.notifyError(error, userJsonReceiver);
                       }
                    }
                    //verify if the controller has to ask for base or advanced effect
                    if(weaponToUse.getAdvancedEffect()!= null && !weaponToUse.getAdvancedEffect().isEmpty()) {
                        String message = "Scegli se usare l'effetto base o quello avanzato";
                        notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Asked base or advanced effect to "+currentPlayer.getName());
                    }
                    else{
                        askOptional(currentPlayer, userJsonReceiver);
                        commandExecutorLogger.log(Level.INFO, "Asked optional effect to "+currentPlayer.getName());
                    }

                }
                else{
                    String error ="Non sei nella fase di scelta dell'arma";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }

        } else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(ChooseOptEffectCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            }
            else{
                if(shootState.equals(ShootState.CHOOSEBASE)&& weaponToUse.canOpt(currentPlayer)){
                    String message ="";

                    if(command.getOpt().equals("tutti")) {
                        if (currentPlayer.canPayAll(weaponToUse.getBaseEffect().get(0).getOptionalEffects())){
                            opt.addAll(weaponToUse.getBaseEffect().get(0).getOptionalEffects());
                            currentPlayer.payOpt(weaponToUse.getBaseEffect().get(0).getOptionalEffects());
                            message = "Tutti gli effetti opzionali sono stati impostati, se puoi muoverti inerisci il movimento se no inserisci i bersagli";
                            shootState = ShootState.CHOSENEFFECT;
                        }
                        else{
                            message = "Non puoi pagare tutti gli effetti opzionali, seleziona solo quelli ammessi";
                        }
                    }else if(!command.getOpt().equals("no")){
                        if (currentPlayer.canPay(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())).getCost())) {
                            opt.add(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())));
                            currentPlayer.payOpt(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())));
                            message = "L'effetto opzionale selezionato è stato impostato, se puoi muoverti inerisci il movimento se no inserisci i bersagli";
                            shootState = ShootState.CHOSENEFFECT;
                        }
                        else{
                            message = "Non puoi l'effetto opzionali, seleziona solo quelli ammessi";
                        }
                    }else if (command.getOpt().equals("no")){
                        shootState = ShootState.CHOSENEFFECT;
                        message = "Nessun effetto opzionale impostato, se puoi muoverti inerisci il movimento se no inserisci i bersagli";
                    }
                    notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                }
                else{
                    String error ="Non sei nella fase di scelta degli effetti opzionali";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }

        } else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();

    }

    public void execute(ChooseTargetCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            }
            else {
                if ((shootState.equals(ShootState.CHOSENWEAPON) && weaponToUse.getBaseEffect().get(0).getOptionalEffects().isEmpty())|| shootState.equals(ShootState.MOVEEFFECTBASE) || shootState.equals(ShootState.MOVEEFFECTOPTIONAL)) {
                    if(verifyTarget(command.getTarget(), gameManager.getMatch().getPlayers())) {
                        for (String str : command.getTarget()) {
                            targets.add(gameManager.getMatch().getPlayerFromName(str));
                        }
                        if(base == null){
                            if(!weaponToUse.getBaseEffect().get(0).canMoveTarget() && weaponToUse.getBaseEffect().get(0).getStrategy().areTargetValid(currentPlayer, targets)) {
                                applyDamage(currentPlayer);
                            } else if(weaponToUse.getBaseEffect().get(0).canMoveTarget()){
                                shootState = ShootState.TARGETASKED;
                            }
                            String message = "Target impostati corrrettamente";
                            notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                        }else if (base != null){
                            if(!base.canMoveTarget() && base.getStrategy().areTargetValid(currentPlayer, targets)) {
                                applyDamage(currentPlayer);
                            }
                            else if(base.canMoveTarget()){
                                shootState = ShootState.TARGETASKED;
                            }
                            String message = "Target impostati corrrettamente";
                            notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                        }
                        //todo optional
                        else{
                            resetShoot();
                            command.endCommandToAction(gameManager);
                            String error ="I target che hai inserito non puoi colpirli, hai perso la mossa";
                            notifier.notifyError(error, userJsonReceiver);
                        }

                    }else{
                        String error ="Target inseriti non validi";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
                else{
                    String error ="Non sei nella fase di scelta dei target";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        } else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(ChooseAdvanceCommand command) throws IOException{
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            }
            else {
                if(shootState.equals(ShootState.CHOSENWEAPON)){
                    if(command.getTypeBase().equals("avanzato")){
                        if(currentPlayer.canPay( weaponToUse.getAdvancedEffect().get(0).getCost())) {
                            base = weaponToUse.getAdvancedEffect().get(0);
                            currentPlayer.pay( weaponToUse.getAdvancedEffect().get(0));
                            commandExecutorLogger.log(Level.INFO, "Choosen advanced effetc for "+weaponToUse.getName()+ " from "+currentPlayer.getName());
                        }
                        else{
                            String error ="Non puoi pagare l'efetto avanzato, quindi userai quello base";
                            notifier.notifyError(error, userJsonReceiver);
                        }
                    }
                    else{
                        commandExecutorLogger.log(Level.INFO, "Choosen base effect for "+weaponToUse.getName()+ " from "+currentPlayer.getName());
                    }
                    askOptional(currentPlayer, userJsonReceiver);
                    commandExecutorLogger.log(Level.INFO, "Asked optional effect to "+currentPlayer.getName());
                }
                else{
                    String error ="Non sei nella fase di scelta dell'efetto avanzato";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        } else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(MoveTargetCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            }
            else {
                if(shootState.equals(ShootState.TARGETASKED)){
                    if((base == null && !weaponToUse.getBaseEffect().get(0).canMoveTarget())||(base != null && !base.canMoveTarget())||canOptionalTargetMove()){
                        //verify cannone
                        if(weaponToUse.getName().equals("Cannone vortex")){
                            //todo
                        }
                        else{
                            //verify leght of move
                            targets.get(0).setOldTile(targets.get(0).getTile());
                            int moves;
                            if(base == null){
                                moves = weaponToUse.getBaseEffect().get(0).getNumStepsTarget();
                            }else if(base != null){
                                moves = base.getNumStepsTarget();
                            }else{
                                //todo da optional
                                moves = 1;
                            }
                            //verify if the moves passed are correct
                            if(command.getMoves().size()<= moves){
                                try {
                                    currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
                                } catch (NotValidMovesException e) {
                                    String error ="Movimento non valido";
                                    notifier.notifyError(error, userJsonReceiver);
                                }
                            }
                            //verify if now target are valid
                            if((base == null && weaponToUse.getBaseEffect().get(0).getStrategy().areTargetValid(currentPlayer, targets))||(base != null && base.getStrategy().areTargetValid(currentPlayer, targets))){
                                //todo verify for optional
                                shootState = ShootState.APPLYEFFECTDAMAGE;
                                //todo apply damage
                            }
                            else{
                                if(!weaponToUse.getName().equals("Cannone vortex")){
                                    resetShoot();
                                    command.endCommandToAction(gameManager);
                                    undoMovement(targets.get(0), userJsonReceiver, "Non puoi colpire nessuno dopo lo spostamento del bersaglio, hai perso la mossa");
                                }
                                else{
                                    //todo per tutti
                                }
                            }

                        }
                    }
                }
                else{
                    String error ="Non sei nella fase di scelta movimento dell'avversario";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
        } else {
            String error ="La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetEffectPhraseCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                List<User> lobbyUsers = gameManager.getLobby().getUsers();
                User owner = registry.getJsonUserOwner(userJsonReceiver);
                //verify if the owner is in the lobby
                if (lobbyUsers.contains(owner)) {
                    owner.setEffectPhrase(command.getPhrase());
                    String message = "La tua frase ad effetto è stata modificata";
                    notifier.notifyMessage(message, userJsonReceiver);
                } else {
                    String error ="Non puoi modificare la tua frase ad effetto";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }else {
            String error ="Non puoi modificare la tua frase perchè la partita è iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetNumberOfDeathCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                int numOfDeathWanted = command.getDeath();
                User firstLobbyUser = gameManager.getLobby().getUsers().get(0);
                User owner = registry.getJsonUserOwner(userJsonReceiver);
                //verify target of death and if the owner is the first user of the lobby
                if (numOfDeathWanted < 9 && numOfDeathWanted > 4 && firstLobbyUser == owner) {
                    gameManager.setNumOfSkulls(numOfDeathWanted);
                    for (JsonReceiver js : command.getAllReceivers()) {
                        js.sendJson(jsonCreator.createJsonWithMessage("Il numero" +
                                "notifier.notifyMessage(message, js); di uccisioni per la partita è stato cambiato a: " + command.getDeath()));
                    }
                } else {
                    if (numOfDeathWanted > 8 || numOfDeathWanted < 5) {
                        String error ="Numero uccisioni non nel range ammissibile";
                        notifier.notifyError(error, userJsonReceiver);
                    } else {
                        String error ="Operazione non consentita";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }else {
            String error ="Non puoi modificare il numero di morti perchè è iniziata la partita";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetPlayerNumberCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                int numOfPlayersWanted = command.getPlayers();
                User firstLobbyUser = gameManager.getLobby().getUsers().get(0);
                User owner = registry.getJsonUserOwner(userJsonReceiver);
                //verify target of number of player and if the owner is the first user of the lobby
                if (numOfPlayersWanted < 6 && numOfPlayersWanted > 2 && firstLobbyUser == owner) {
                    gameManager.getLobby().setMaxPlayerInLobby(numOfPlayersWanted);
                    for (JsonReceiver jr : command.getAllReceivers()) {
                        jr.sendJson(jsonCreator.createJsonWithMessage("Il numero di uccisioni per la partita è stato cambiato a: " + command.getPlayers()));
                    }
                } else {
                    if (numOfPlayersWanted > 5 || numOfPlayersWanted < 3) {
                        String error ="Numero uccisioni non nel range ammissibile";
                        notifier.notifyError(error, userJsonReceiver);
                    } else {
                        String error ="Operazione non consentita";
                        notifier.notifyError(error, userJsonReceiver);

                    }
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }else {
            String error ="Non puoi modificare il numero di giocatori perchè la partita è già iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetUsernameCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        List<User> users = gameManager.getLobby().getUsers();
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        String clientToken = command.getToken();
        //verify if the game is started
        if (!gameHasStarted) {
            //first case, the user has still to be created. In this case, the name associated to the token in the registry should
            //be assigned

            //if the json receiver is not linked to any user
            if(registry.getJsonUserOwner(userJsonReceiver)==null){
                //returns the username registered with this token
                String username = registry.getUsernameFromToken(clientToken);
                User user = new User(username);
                commandExecutorLogger.log(Level.INFO, "User "+username +" created");
                registry.associateReceiverAndUser(userJsonReceiver, user);
                try {
                    gameManager.getLobby().join(user);
                    String jsonToSend = jsonCreator.createJsonWithMessage("Utente creato. Il tuo nome è " +user.getUsername());
                    userJsonReceiver.sendJson(jsonToSend);
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            notifier.notifyMessage("Lo user "
                                    + user.getUsername() +" si è unito alla lobby", js);
                        }
                    }
                } catch (NotValidActionException e) {
                    e.getMessage();
                    e.printStackTrace();
                }
            }
            //in case the json receiver is associated to the username, the new username should be checked
            else{
                String newUsername = command.getUsername();
                //if already present, send an error
                if (registry.usernameAlreadyPresent(newUsername)){
                    String error ="username già presente";
                    notifier.notifyError(error, userJsonReceiver);
                }
                //if not present, the user should change his name and the registry should be notified of the change
                else{
                    User currentUser = registry.getJsonUserOwner(userJsonReceiver);
                    String oldUsername = currentUser.getUsername();
                    currentUser.setUsername(newUsername);
                    registry.associateTokenAndUser(command.getToken(), newUsername);
                    String message = "Il tuo username è stato modificato in: " + newUsername;
                    notifier.notifyMessage(message, userJsonReceiver);
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            String message1 ="Lo user " + oldUsername +" ha cambiato il nome in " + newUsername;
                            notifier.notifyMessage(message1, js);
                        }
                    }
                }
            }
        }
        else {
            String error ="Non puoi modificare il tuo username perchè la partita è già iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();

        boolean lobbyFull = gameManager.getLobby().isFull();
        if(lobbyFull && !gameHasStarted && !gameManager.getLobby().isClosed()){
            TimerTask task = new TimerTask(){
                @Override
                public void run() {
                    commandExecutorLogger.log(Level.INFO, "timer scaduto");
                    try {
                        createMatchRoutine(command.getAllReceivers());
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            };
            gameManager.getLobby().closeLobby();
            Timer timer = new Timer();
            commandExecutorLogger.log(Level.INFO, "creazione e inizio del timer");
            timer.schedule(task, seconds * 1000);
        }
    }

    public void execute(SetTokenCommand command) throws IOException {
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                List<String> usersToken = gameManager.getLobby().getNameToken();
                String userClaimedName = command.getPlayerToken();
                if (userClaimedName.equals("violetta") || userClaimedName.equals("distruttore") || userClaimedName.equals("banshee") || userClaimedName.equals("dozer") || userClaimedName.equals("sprog")) {
                    //verify if the token is already used
                    if (!usersToken.contains(command.getPlayerToken())) {
                        Player playerAssociateToUser = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
                        playerAssociateToUser.setName(command.getPlayerToken());
                        String message = "Personaggio modificato in " + command.getPlayerToken();
                        notifier.notifyMessage(message, userJsonReceiver);
                    } else {
                        String error ="Personaggio già scelto";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
                else{
                    String error ="Personaggio non valido";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }
        else {
            String error ="Non puoi modificare il tuo personaggio perchè la partita è già iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetMapCommand command) throws IOException{
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                String mapWanted = command.getMap();
                User firstLobbyUser = gameManager.getLobby().getUsers().get(0);
                User owner = registry.getJsonUserOwner(userJsonReceiver);
                //verify if the owner is the first user of the lobby
                if (firstLobbyUser == owner) {
                    gameManager.setMapName(mapWanted);
                    for (JsonReceiver jr : command.getAllReceivers()) {
                        jr.sendJson(jsonCreator.createJsonWithMessage("La mappa per la partita è stata cambiata in " + gameManager.getMapName() ));
                    }
                }
                else {
                    String error ="Operazione non consentita";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }else {
            String error ="Non puoi modificare la mappa perchè la partita è già iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }

    public void execute(SetFinalFrenzyCommand command) throws IOException{
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                boolean frenzyWanted = command.getFrenzy();
                User firstLobbyUser = gameManager.getLobby().getUsers().get(0);
                User owner = registry.getJsonUserOwner(userJsonReceiver);
                //verify if the owner is the first user of the lobby
                if (firstLobbyUser == owner) {
                    if(gameManager.getFinalfrenzy()!=frenzyWanted) {
                        gameManager.setFinalFrenzy(frenzyWanted);
                        for (JsonReceiver jr : command.getAllReceivers()) {
                            jr.sendJson(jsonCreator.createJsonWithMessage("La partita terminerà con la frenesia finale: " + gameManager.getFinalfrenzy()));
                        }
                    }else{
                        String error ="La frenesia finale era già impostata al valore inserito";
                        notifier.notifyError(error, userJsonReceiver);
                    }
                }
                else {
                    String error ="Operazione non consentita";
                    notifier.notifyError(error, userJsonReceiver);
                }
            }
            else{
                String error ="Non hai ancora impostato uno username";
                notifier.notifyError(error, userJsonReceiver);
            }
        }else {
            String error ="Non puoi modificare la frenesia finale perchè la partita è già iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();
    }


    private boolean hasMatchStarted(GameManager gm){
        return gm.isMatchStarted();
    }

    /**
     * waits for some seconds and then creates a match and notifies to the receivers
     * @param receivers the receivers that need to be informed
     * @throws IOException
     */
    private void createMatchRoutine(List<JsonReceiver> receivers) throws IOException{
        gameManager.createMatch(jsonCreator);
        gameManager.startMatch();
        jsonCreator.notifyTileChange(null);
        JsonReceiver userToBeNotifiedThrow = null;
        //this line should not be necessary, but the observer-obserrvable pattern is activated after the mach is created,
        //so the json creator is not attached to the player . At this point should be attached
        jsonCreator.notifyPlayerChange(gameManager.getMatch().getCurrentPlayer());
        for(JsonReceiver jr: receivers ){
            User userToBeNotified= TokenRegistry.getInstance().getJsonUserOwner(jr);
            Player player = userToBeNotified.getPlayer();
            String json = jsonCreator.createTargetPlayerJson("La partita è iniziata", player);
            jr.sendJson(json);
            if(userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
                userToBeNotifiedThrow = jr;
            }
        }
        jsonCreator.reset();
        userToBeNotifiedThrow.sendJson(jsonCreator.createJsonWithMessage("scegli quale powerup scartare per spawnare"));
        jsonCreator.reset();
    }

    private Color colorParser(String color){
        if(color.equals("rosso")){
           return Color.RED;
        } else if (color.equals("blu")){
            return Color.BLUE;
        } else{
            return Color.YELLOW;
        }
    }

    private String translateColor(String color){
        switch (color){
            case "rosso":
                return "red";
            case "blu":
                return "blue";
            case "giallo":
                return "yellow";
        }
        return null;
    }
    private PowerUpType powerUpParser(String powerUp){
        if(powerUp.equalsIgnoreCase("granata")){
            return PowerUpType.TAGBACK_GRANADE;
        } else if (powerUp.equalsIgnoreCase("mirino")){
            return PowerUpType.TARGETING_SCOPE;
        }else if (powerUp.equalsIgnoreCase("raggiocinetico")){
            return PowerUpType.NEWTON;
        } else if (powerUp.equalsIgnoreCase("teletrasporto")){
            return PowerUpType.TELEPORTER;
        }
        return null;
    }


    private void notifyToAllExceptCurrent(JsonReceiver js, JsonReceiver userJsonReceiver, String message) throws IOException {
        if (js != userJsonReceiver) {
            User userToBenotified = registry.getJsonUserOwner(js);
            Player userPlayer = userToBenotified.getPlayer();
            notifier.notifyMessageTargetPlayer(message, js, userPlayer);
        }
    }

    private void returnOldState(Player currentPlayer, JsonReceiver userJsonReceiver, String message) throws IOException {
        if(currentPlayer.getTile() != currentPlayer.getOldTile()) {
            currentPlayer.getOldTile().addPlayer(currentPlayer);
        }
        String error =message;
        notifier.notifyError(error, userJsonReceiver);
        currentPlayer.setState(currentPlayer.getOldState());
        currentPlayer.setOldState(null);
        currentPlayer.setOldTile(null);
        jsonCreator.reset();
    }

    public void undoMovement(Player currentPlayer, JsonReceiver userJsonReceiver, String message) throws IOException {
        if(currentPlayer.getTile() != currentPlayer.getOldTile()) {
            currentPlayer.getOldTile().addPlayer(currentPlayer);
        }
        String error =message;
        notifier.notifyError(error, userJsonReceiver);
        currentPlayer.setOldTile(null);
    }


    private void notifyAllExceptOne(String message, JsonReceiver toExclude, List<JsonReceiver> allReceivers) throws IOException{
        for (JsonReceiver js : allReceivers) {
            if (js != toExclude) {
                notifier.notifyMessage(message, js);
            }
        }
    }

    private boolean canMoveOpt(){
        for(OptionalEffect opts : opt){
            if(opts.canShooterMove()){
                return true;
            }
        }
        return false;
    }

    private void resetShoot(){
        shootState = ShootState.BASE;
        base = null;
        weaponToUse = null;
        targets.clear();
        opt.clear();
    }

    private void askOptional(Player currentPlayer, JsonReceiver userJsonReceiver) throws IOException {
        shootState = ShootState.CHOOSEBASE;
        //verify if the current player can use an optional effect or if is present
        if(weaponToUse.canOpt(currentPlayer)){
            String message = "Scegli se vuoi usare gli effetti opzionali e quali: no opt, tutti opt o opt + il numero da 0 a "+(weaponToUse.getBaseEffect().get(0).getOptionalEffects().size()-1);
            notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
        }
        else{
            if(weaponToUse.getBaseEffect().get(0).canMoveShooter() && weaponToUse.getAdvancedEffect()!= null && weaponToUse.getAdvancedEffect().size()>0 && weaponToUse.getAdvancedEffect().get(0).canMoveShooter()){
                String message = "Scegli se vuoi muoverti con l'effetto base";
                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
            }
            else{
                String message = "Scegli chi vuoi colpire";
                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
            }
        }
    }

    private void verfyMoveShooter(Player currentPlayer, JsonReceiver userJsonReceiver, MoveCommand command) throws IOException {
        if(base == null && !weaponToUse.getBaseEffect().get(0).getStrategy().canHitSomeone(currentPlayer)){
            resetShoot();
            command.endCommandToAction(gameManager);
            String error ="Non puoi colpire nessuno da questa posizione quindi hai perso la mossa";
            notifier.notifyError(error, userJsonReceiver);
        }
        else if (base != null && !base.getStrategy().canHitSomeone(currentPlayer)){
            resetShoot();
            command.endCommandToAction(gameManager);
            String error ="Non puoi colpire nessuno da questa posizione quindi hai perso la mossa";
            notifier.notifyError(error, userJsonReceiver);
        }
        else{
            shootState =ShootState.MOVEEFFECTBASE;
        }
    }

    private boolean verifyTarget(List<String> name, List<Player> players){
        List<String> playerName = new ArrayList<>();
        for(Player pl: players){
            playerName.add(pl.getName());
        }
        for(String str: name){
            if(!playerName.contains(str)){
                return false;
            }
        }
        return true;
    }

    private boolean canOptionalTargetMove(){
        for(OptionalEffect opts: opt){
//            if(opts)
        }
        return true;
    }

    private void applyDamage(Player currentPlayer){
        shootState = ShootState.APPLYEFFECTDAMAGE;
        //todo apply damage, possono essere più di uno?
        weaponToUse.getBaseEffect().get(0).applyOptionalEffect(opt);
        DamageTransporter dt = new DamageTransporter(targets.get(0), currentPlayer, weaponToUse.getBaseEffect().get(0).getDamage().get("red"),weaponToUse.getBaseEffect().get(0).getMarks().get("red"));
        targets.get(0).getPlayerBoard().calculateDamage(dt);
    }
}
