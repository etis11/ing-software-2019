package controller;

import controller.commandpack.*;
import exceptions.*;
import javafx.scene.paint.Color;
import model.*;
import network.TokenRegistry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {
    private final TokenRegistry registry = TokenRegistry.getInstance();
    private final static Logger commandExecutorLogger = Logger.getLogger(CommandExecutor.class.getName());
    public static int startMatchTimerDelay= 1;
    /**
     * duration of a turn expressed in seconds
     */
    public static int turnLength = 500;
    private ShootState shootState;
    private WeaponCard weaponToUse;
    private List<OptionalEffect> opt;
    private List<Player> targets;
    private Effect advanced;
    private boolean usePU;
    /**
     * constant for multiplying values from seconds to millis
     */
    private final long thousand = 1000;

    /**
     * Timer used to check and disconnect the current player
     */
    private Timer turnTimer;
    /**
     *
     */
    private Timer startGameTimer;
    private boolean startGameTimerStarted;

    /**
     * gameManager is a reference to the model due to access to the match and lobby variables
     */
    private GameManager gameManager;

    /**
     * used to create json
     */
    private final  JsonCreator jsonCreator;

    /**
     * notifies the receiver of the changes
     */
    private final Notifier notifier;

    /**
     * is used by the server to send his command
     */
    private final CommandLauncherInterface launcher;

    public CommandExecutor(GameManager gameManager, JsonCreator jsonCreator, CommandLauncherInterface launcherInterface) {
        this.gameManager = gameManager;
        this.shootState = ShootState.BASE;
        this.weaponToUse = null;
        this.opt = new ArrayList<>();
        this.targets = new ArrayList<>();
        advanced = null;
        this.jsonCreator = jsonCreator;
        notifier = new JsonNotifier(jsonCreator, launcherInterface, gameManager);
        turnTimer = null;
        launcher = launcherInterface;
        usePU = false;
    }

    public void setTurnTimer(Timer turnTimer) {
        this.turnTimer = turnTimer;
    }

    /**
     * returns the timer length
     * @return expressed in seconds
     */
    public int getTurnLength() {
        return turnLength;
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
                if (!currentPlayer.getState().isNormalAction() && !currentPlayer.getState().isMoreAction() && !currentPlayer.getState().isMostAction() && !currentPlayer.getState().getName().equals("Reload")) {
                    String error ="Non puoi terminare il tuo turno al momento";
                    notifier.notifyError(error, userJsonReceiver);
                } else {
                    currentPlayer.getState().nextState("EndTurn", currentPlayer);
                    String message = currentPlayer.getName()+" ha terminato il suo turno";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            notifyExceptCurrent(js, userJsonReceiver, message);
                        }
                    }
                    endTurnNotification(userJsonReceiver);
                    endTurnAndResetTimer();
                    checkEndMatch(command.getAllReceivers());
                    gameManager.getMatch().newRound();
                    newTimerCreation(command.getAllReceivers());

                    currentPlayer = gameManager.getMatch().getCurrentPlayer();
                    commandExecutorLogger.log(Level.INFO, "Inizio turno giocatore "+currentPlayer.getName());
                    //cycle that finds the json receiver of the person that should throw
                    notifyNewTurnAndSpawning(command.getAllReceivers());
                    commandExecutorLogger.log(Level.INFO, "Start round for player "+currentPlayer.getName());
                }
            }
        }
        else{
            String error = "La partita non è ancora iniziata";
            notifier.notifyError(error, userJsonReceiver);
        }
        jsonCreator.reset();

    }

    public void execute(ServerEndTurnCommand command) throws IOException{
            //notify to all the user alive that the old user has been kicked
            List<JsonReceiver> allJsonReceivers = command.getAllReceivers();
            // user that has to be disconnected
            User forcedDisconnectedUser = command.getDisconnectUser();
            //current player before changing the turn
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            for(JsonReceiver jsonReceiver: allJsonReceivers) {
                notifier.notifyDisconnection(forcedDisconnectedUser, currentPlayer, jsonReceiver);
            }
            jsonCreator.reset();
            //put the current player in end turn
            currentPlayer.goToEndState();
            //disconnected, i dont have to notify him
            //end turn
            endTurnAndResetTimer();
            checkEndMatch(allJsonReceivers);
            //new round
            gameManager.getMatch().newRound();
            //new turn
            newTimerCreation(allJsonReceivers);
            notifyNewTurnAndSpawning(allJsonReceivers);
    }

    /**
     * Stops the timer if there are not enough player in the lobby.
     * @param command
     */
    public void execute(StopTimerLobby command){
        List<JsonReceiver> allReceivers = command.getAllReceivers();
        System.out.println(gameManager.getLobby().getUsers());
        System.out.println("minCap " + gameManager.getLobby().hasReachedMinCapacity() + " " + gameManager.getLobby().getUsers().size());
        System.out.println("start timer " + startGameTimerStarted);
        System.out.println("matchs started" + gameManager.isMatchStarted());
        if (!gameManager.getLobby().hasReachedMinCapacity() && startGameTimerStarted && !gameManager.isMatchStarted()){
            startGameTimer.cancel();
            startGameTimer.purge();
            commandExecutorLogger.log(Level.INFO, "Timer killed");
            startGameTimerStarted = false;
        }
    }


    /**
     * Notifies to the current player that the turn has ended. The board is filled and the timer is resetted
     * @param userJsonReceiver
     */
    private void endTurnNotification(JsonReceiver userJsonReceiver){
        String messageToSend = "Hai terminato il tuo turno";
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        notifier.notifyMessageTargetPlayer(messageToSend, userJsonReceiver, currentPlayer);
        commandExecutorLogger.log(Level.INFO, "Termine turno giocatore "+currentPlayer.getName());
    }

    /**
     * calls the end round routine and resets the timer
     */
    private void endTurnAndResetTimer(){
        gameManager.getMatch().endRound();
        //chack in case it's the first time and the timer was not instatiated
        if (turnTimer != null){
            turnTimer.cancel();
            turnTimer.purge();
        }
    }

    /**
     * notify the new turn and checks for the spawning
     * @param allJsonReceiver
     */
    private void notifyNewTurnAndSpawning(List<JsonReceiver> allJsonReceiver){
        JsonReceiver userToBeNotifiedThrow = null;
        for(JsonReceiver jr : allJsonReceiver){
            User userToBeNotified= TokenRegistry.getInstance().getJsonUserOwner(jr);
            if (userToBeNotified != null){
                if(userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
                    userToBeNotifiedThrow = jr;
                }
            }
        }
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        for (JsonReceiver jsonReceiver: allJsonReceiver){
            if(jsonReceiver != userToBeNotifiedThrow){
                notifier.notifyMessageTargetPlayer("", jsonReceiver, registry.getJsonUserOwner(jsonReceiver).getPlayer());
            }
        }
        if((currentPlayer.getState().getName().equals("EndTurn")&& currentPlayer.getTile() == null) || currentPlayer.getState().getName().equals("Dead") || currentPlayer.getState().getName().equals("Overkilled")) {
            notifier.notifyMessageTargetPlayer("scegli quale powerup scartare per spawnare", userToBeNotifiedThrow, currentPlayer);
            commandExecutorLogger.log(Level.INFO, "Richiesta scarto power up per spawn a "+currentPlayer.getName());
        }
        else {
            notifier.notifyMessageTargetPlayer("E' iniziato il tuo turno", userToBeNotifiedThrow, currentPlayer);
        }
    }

    /**
     * looks for the json receiver of the current player and sets the timer
     * @param allJsonReceivers
     */
    private void newTimerCreation(List<JsonReceiver> allJsonReceivers){
        //looking for the json receiver that has the current player
        User timerUser = null;
        JsonReceiver jsonReceiverCurrentTurn = null;
        for (JsonReceiver jsonReceiver : allJsonReceivers){
            User possibleCurrentUser = possibleCurrentUser = registry.getJsonUserOwner(jsonReceiver);
            //this user is null in case the json receiver has been disconnected and so his user is disconnected.
            if (possibleCurrentUser != null){
                Player userPlayer = possibleCurrentUser.getPlayer();
                //if the current player is the same of this user player, then the new json receiver is this one
                if (userPlayer.getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
                    jsonReceiverCurrentTurn = jsonReceiver;
                    timerUser = possibleCurrentUser;

                }
            }
        }
        turnTimer = new Timer();
        turnTimer.schedule(new TurnTimerTask(launcher, jsonReceiverCurrentTurn, notifier, timerUser), turnLength*thousand);
    }

    /**
     * Checks if the match has to stop. A match ends when there are less then 3 players or
     * all the player have played during "frenesia"
     * TODO per adesso implementa solo la storia della disconnessione
     */
    private void checkEndMatch(List<JsonReceiver> allReceivers){
        Match match = gameManager.getMatch();
        if (match.getNumActivePlayers() < Match.getMinActivePlayers()){
            for(JsonReceiver jsonReceiver: allReceivers){
                notifier.notifyMessage("La partita è terminata perchè non ci sono abbastanza utenti attivi", jsonReceiver);
            }
            //now i must disconnect all the json receivers
            disconnectReceivers(allReceivers);
            try{
                launcher.stopExecuting();
            }
            catch (RemoteException re){
                commandExecutorLogger.log(Level.WARNING, "This exception should never occur");
            }
        }
    }

    private void disconnectReceivers(List<JsonReceiver> allReceivers){
        for(JsonReceiver jsonReceiver: allReceivers){
            notifier.disconnectReceiver(jsonReceiver);
        }
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
                    boolean hasWeapon = currentPlayer.getWeapons().isEmpty();
                    boolean canPay = false;
                    for(WeaponCard w:currentPlayer.getWeapons()){
                        if(!w.isLoaded() && currentPlayer.canPay(w.getReloadCost().subList(1,w.getReloadCost().size()))){
                            canPay = true;
                        }
                    }
                    //verify if the player has weapon
                    if (!hasWeapon && canPay) {
                        currentPlayer.getState().nextState("Reload", currentPlayer);
                        System.out.println(currentPlayer.getWeapons());
                        String message = currentPlayer.getName()+" sta ricaricando";
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                notifier.notifyMessage(message, js);
                            }
                        }
                        notifier.notifyMessageTargetPlayer("Scegli quale arma ricaricare tra: " + currentPlayer.weaponsToString(),
                                userJsonReceiver, currentPlayer);
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
                    System.out.println("canshoot: "+!currentPlayer.getState().canShoot());
                    System.out.println("shootstate ok: "+!shootState.equals(ShootState.BASE));
                    System.out.println("no rem moves: "+(currentPlayer.getRemainingMoves() < 1));
                    System.out.println("loaded: "+!loaded);
                    String error ="Non puoi sparare";
                    notifier.notifyErrorTargetPlayer(error, userJsonReceiver, currentPlayer);
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("Shoot", currentPlayer);
                    shootState = ShootState.ASKEDSHOOT;
                    String message = currentPlayer.getName()+" sta per sparare";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        notifyExceptCurrent(js, userJsonReceiver, message);
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
                String message = owner.getName()+" sta usando un power up";
                usePU =true;
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

    public void execute(UsePowerUpCommand command){
        boolean gameHasStarted = hasMatchStarted(gameManager);
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (gameHasStarted) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            //todo forse va tolta o rivista questa condizione
            if (owner != currentPlayer) {
                String error ="Non puoi eseguire questa azione se non è il tuo turno";
                notifier.notifyError(error, userJsonReceiver);
            } else {
                if(usePU){
                    //todo
                    //verifico se ce l'ha
                    //rimuovo
                    //applico
                    usePU=false;
                    //in caso di pu non valido riporto usePu a false
                }
                else{
                    String error ="Non puoi usare power up";
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
                    try{
                        currentPlayer.getState().nextState("Run", currentPlayer);
                    }
                    catch (IllegalStateException ise){
                        notifier.notifyMessageTargetPlayer("Non puoi fare questa azione", userJsonReceiver, currentPlayer);
                        return;
                    }
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
                //if run, pick or shoot state
                if(state.equals("Run")||state.equals("PickUp")||state.equals("PickUpPlus")|| state.equals("Shoot")||state.equals("ShootPlus")) {
                    //if player can't run
                    if (!currentPlayer.getState().canRun() && currentPlayer.getState().getRemainingSteps() < command.getMoves().size()) {
                        String error ="Non hai abbastanza mosse rimanenti";
                        notifier.notifyError(error, userJsonReceiver);
                    } else {
                        //try the movement
                        try {
                            currentPlayer.setOldTile(currentPlayer.getTile());
                            currentPlayer.getState().decrementRemainingSteps(command.getMoves().size());
                            currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
                            String message = currentPlayer.getName() + " si è spostato di nel tile: " + currentPlayer.getTile().getID();
                            //case run state
                            if (currentPlayer.getState().getName().equals("Run")) {
                                for (JsonReceiver js : command.getAllReceivers()) {
                                    notifyExceptCurrent(js, userJsonReceiver, message);
                                }
                                notifier.notifyMessageTargetPlayer("Ti sei spostato nel tile :" + currentPlayer.getTile().getID()
                                        , userJsonReceiver, currentPlayer);
                                commandExecutorLogger.log(Level.INFO, "Run of  "+currentPlayer.getName()+" correctly done");
                                command.endCommandToAction(gameManager);
                            }
                            //case pick state
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
                                        notifyExceptCurrent(js, userJsonReceiver, message2);
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
                                if((advanced == null && !weaponToUse.getBaseEffect().get(0).canMoveShooter() && command.getMoves().size()>weaponToUse.getBaseEffect().get(0).getNumStepsShooter())||(advanced != null && !advanced.canMoveShooter() && command.getMoves().size()> advanced.getNumStepsShooter())){
                                    undoMovement(currentPlayer, userJsonReceiver,"Puoi soltanto scegliere chi colpire(messaggio da differrenziare)");
                                }
                                else{
                                    if(advanced == null){
                                        weaponToUse.getBaseEffect().get(0).setAlreadyMovedShooter(true);
                                    }
                                    else {
                                        advanced.setAlreadyMovedShooter(true);
                                    }
                                    shootState = ShootState.MOVEEFFECTBASE;
                                    verifyHitMovedShooter(currentPlayer,userJsonReceiver, command);
                                }
                            }
                            //movement caused by optional effect
                            else if (currentPlayer.getState().canShoot() && shootState.equals(ShootState.CHOSENEFFECT)){
                                int moves = 0;
                                for (OptionalEffect opts : opt){
                                    if(opts.canTargetMove()){
                                        moves = opts.getNumStepsTarget();
                                    }
                                }
                                if (!opt.isEmpty() && !canMoveShooterOpt() && command.getMoves().size() >moves){
                                    undoMovement(currentPlayer, userJsonReceiver,"Puoi soltanto scegliere chi colpire(messaggio da differenziare)");
                                }
                                else if(!opt.isEmpty()){
                                    for(OptionalEffect opts :opt){
                                        opts.setShooterAlreadyMoved(true);
                                    }
                                    shootState = ShootState.MOVEEFFECTOPTIONAL;
                                }
                                System.out.println("qui c'e qualcosa da verificare");
                                //todo da rifare verifyHitMovedShooter(currentPlayer, userJsonReceiver, command)
                            }
                            else if(currentPlayer.getState().canShoot() && shootState.equals(ShootState.APPLYEFFECTDAMAGE)){
                                int moves = 0;
                                for (OptionalEffect opts : opt){
                                    if(opts.canTargetMove()){
                                        moves = opts.getNumStepsTarget();
                                    }
                                }
                                if((advanced == null && weaponToUse.getBaseEffect().get(0).canMoveShooter() && command.getMoves().size()>weaponToUse.getBaseEffect().get(0).getNumStepsShooter())
                                        ||(advanced != null && advanced.canMoveShooter() && command.getMoves().size()> advanced.getNumStepsShooter()) || (canMoveShooterOpt()&&command.getMoves().size()>moves)) {
                                    //notify Spostamento
                                    shootEnded(userJsonReceiver);
                                }
                                else{
                                    undoWithoutError(currentPlayer);
                                }

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
                                            notifyExceptCurrent(js, userJsonReceiver, message);
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
                            notifyExceptCurrent(js, userJsonReceiver, message);
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
                if (command.getWeaponName() != null) {
                    WeaponCard wpc = currentPlayer.hasWeapon(command.getWeaponName());
                    if (wpc != null) {
                        if (!wpc.isLoaded()) {
                            try {
                                wpc.reload(currentPlayer);
                                System.out.println(currentPlayer.getWeapons());
                                String message = currentPlayer.getName()+" ha ricaricato: " + wpc.getName();
                                for (JsonReceiver js : command.getAllReceivers()) {
                                    if (js != userJsonReceiver) {
                                        notifier.notifyMessage(message, js);
                                    }
                                }
                                notifier.notifyMessageTargetPlayer("Arma ricaricata, vuoi ricaricare un'altra arma o finire il turno?", userJsonReceiver, currentPlayer);
                                commandExecutorLogger.log(Level.INFO, "Asked for new relaod to "+currentPlayer.getName());
                            } catch (InsufficientAmmoException e) {
                                String error ="Non hai abbastanza munizioni per ricaricare l'arma selezionata";
                                notifier.notifyError(error, userJsonReceiver);
                            }
                        } else {
                            String error ="L'arma selezionata è già carica, vuoi ricaricare un'altra arma oppure finire il turno?";
                            notifier.notifyError(error, userJsonReceiver);
                        }
                    }
                    else {
                        String error = "Non hai l'arma selezionata, vuoi ricaricare un'altra arma oppure finire il turno?";
                        notifier.notifyError(error, userJsonReceiver);
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
                           // e.printStackTrace();
                            LOGGER.LOGGER.log(Level.WARNING,Arrays.toString(e.getStackTrace()));
                        }
                        String message = currentPlayer.getName() + " si è rigenerato nel punto di rigenerazione " + regenPointColor;
                        for (JsonReceiver js : command.getAllReceivers()) {
                            notifyExceptCurrent(js, userJsonReceiver, message);
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

    public void execute(WeaponCommand command) {
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
                    //verify if the controller has to ask for advanced or advanced effect
                    if(weaponToUse!=null&&weaponToUse.getAdvancedEffect()!= null && !weaponToUse.getAdvancedEffect().isEmpty()) {
                        String message = "Scegli se usare l'effetto advanced o quello avanzato";
                        notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                        commandExecutorLogger.log(Level.INFO, "Asked advanced or advanced effect to "+currentPlayer.getName());
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
                //verify if the shoot state is correct
                if(shootState.equals(ShootState.CHOOSEBASE)&& weaponToUse.canOpt(currentPlayer)){
                    String message ="";
                    //parse command param
                    if(command.getOpt().equals("tutti")) {
                        //verify if player can pay all effects
                        if (currentPlayer.canPayAll(weaponToUse.getBaseEffect().get(0).getOptionalEffects())){
                            opt.addAll(weaponToUse.getBaseEffect().get(0).getOptionalEffects());
                            currentPlayer.payOpt(weaponToUse.getBaseEffect().get(0).getOptionalEffects());
                            message = "Tutti gli effetti opzionali sono stati impostati, se puoi muoverti inerisci il movimento se no inserisci i bersagli";
                            shootState = ShootState.CHOSENEFFECT;
                            System.out.println("scelti : "+opt.size()+" opt effect (tutti)");
                        }
                        else{
                            message = "Non puoi pagare tutti gli effetti opzionali, seleziona solo quelli ammessi";
                        }
                    }else if(!command.getOpt().equals("no")){
                        //verify if the choosen opt effect can be paid
                        if (currentPlayer.canPay(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())).getCost())) {
                            opt.add(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())));
                            currentPlayer.payOpt(weaponToUse.getBaseEffect().get(0).getOptionalEffects().get(Integer.parseInt(command.getOpt())));
                            message = "L'effetto opzionale selezionato è stato impostato, se puoi muoverti inserisci il movimento se no inserisci i bersagli";
                            shootState = ShootState.CHOSENEFFECT;
                            System.out.println("scelto : "+opt.size()+" opt effect(uno solo il num: "+command.getOpt()+")");
                        }
                        else{
                            message = "Non puoi l'effetto opzionali, seleziona solo quelli ammessi";
                        }
                    }else if (command.getOpt().equals("no")){
                        shootState = ShootState.CHOSENEFFECT;
                        message = "Nessun effetto opzionale impostato, se puoi muoverti o muovere il target inserisci il movimento se no inserisci i bersagli";
                        System.out.println("scelto nessun opt");
                    }
                    notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                    commandExecutorLogger.log(Level.INFO, "Set optional effect for "+weaponToUse.getName());
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
//                boolean noMove = shootState.equals(ShootState.CHOSENEFFECT) && ((advanced == null && !weaponToUse.getBaseEffect().get(0).canMoveShooter() && !weaponToUse.getBaseEffect().get(0).canMoveTarget())|| (advanced != null && !advanced.canMoveTarget() && !advanced.canMoveShooter()));
                boolean noMove = shootState.equals(ShootState.CHOSENEFFECT) && ((advanced == null && !weaponToUse.getBaseEffect().get(0).canMoveShooter())|| (advanced != null && !advanced.canMoveTarget() && !advanced.canMoveShooter()));
                //verify if the state is correct to accept targets
                if (noMove || (shootState.equals(ShootState.CHOOSEBASE) && weaponToUse.getBaseEffect().get(0).getOptionalEffects().isEmpty())|| shootState.equals(ShootState.MOVEEFFECTBASE) || shootState.equals(ShootState.MOVEEFFECTOPTIONAL)) {
                    if(verifyTarget(command.getTarget(), gameManager.getMatch().getPlayers())) {
                        for (String str : command.getTarget()) {
                            targets.add(gameManager.getMatch().getPlayerFromName(str));
                        }
                        String message ="";
                        //if using advanced effect
                        if(advanced == null){
                            //if can't move target and target are valid apply damage
                            if(!weaponToUse.getBaseEffect().get(0).canMoveTarget() && weaponToUse.getBaseEffect().get(0).getStrategy().areTargetValid(currentPlayer, targets) && !canOptionalTargetMove()) {
                                applyDamage(currentPlayer, userJsonReceiver);
                                notifyTargetHit(command.getAllReceivers(), userJsonReceiver);
                            }
                            //if can move the target
                            else if(weaponToUse.getBaseEffect().get(0).canMoveTarget() || canOptionalTargetMove()){
                                shootState = ShootState.TARGETASKED;
                                message = "Target impostati corrrettamente, se vuoi puoi spostarli";
                                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                                commandExecutorLogger.log(Level.INFO, "target selected correctly and asked to move them to "+currentPlayer.getName());
                            }
                            //if target are not valid
                            else {
                                resetShoot();
                                command.endCommandToAction(gameManager);
                                String error ="I target che hai inserito non puoi colpirli, hai perso la mossa";
                                notifier.notifyError(error, userJsonReceiver);
                            }


                        }else if (advanced != null){
                            //if can't move target and target are valid apply damage
                            if(!advanced.canMoveTarget() && advanced.getStrategy().areTargetValid(currentPlayer, targets) && !canOptionalTargetMove()) {
                                applyDamage(currentPlayer, userJsonReceiver);
                                notifyTargetHit(command.getAllReceivers(), userJsonReceiver);
                            }
                            //if can move the target
                            else if(advanced.canMoveTarget() || canOptionalTargetMove()){
                                shootState = ShootState.TARGETASKED;
                                message = "Target impostati corrrettamente";
                                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
                                commandExecutorLogger.log(Level.INFO, "target selected correctly "+currentPlayer.getName());
                            }
                            //if target are not valid
                            else {
                                resetShoot();
                                command.endCommandToAction(gameManager);
                                String error ="I target che hai inserito non puoi colpirli, hai perso la mossa";
                                notifier.notifyError(error, userJsonReceiver);
                            }
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
                            advanced = weaponToUse.getAdvancedEffect().get(0);
                            currentPlayer.pay( weaponToUse.getAdvancedEffect().get(0));
                            commandExecutorLogger.log(Level.INFO, "Choosen advanced effetc for "+weaponToUse.getName()+ " from "+currentPlayer.getName());
                        }
                        else{
                            String error ="Non puoi pagare l'efetto avanzato, quindi userai quello advanced";
                            notifier.notifyError(error, userJsonReceiver);
                        }
                    }
                    else{
                        commandExecutorLogger.log(Level.INFO, "Choosen advanced effect for "+weaponToUse.getName()+ " from "+currentPlayer.getName());
                    }
                    askOptional(currentPlayer, userJsonReceiver);
                    commandExecutorLogger.log(Level.INFO, "Asked optional effect to "+currentPlayer.getName());
                }
                else{
                    String error ="Non sei nella fase di scelta dell'effetto avanzato";
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
                    if((advanced == null && weaponToUse.getBaseEffect().get(0).canMoveTarget())||(advanced != null && advanced.canMoveTarget())||canOptionalTargetMove()){
                        //verify cannone
                        if(weaponToUse.getName().equals("Cannone vortex")){
                            //todo
                        }
                        else{
                            //verify leght of move
                            targets.get(0).setOldTile(targets.get(0).getTile());
                            int moves = 0;
                            if(advanced == null && weaponToUse.getBaseEffect().get(0).canMoveTarget()){
                                moves = weaponToUse.getBaseEffect().get(0).getNumStepsTarget();
                            }else if(advanced != null && advanced.canMoveTarget()){
                                moves = advanced.getNumStepsTarget();
                            }else{
                                for (OptionalEffect opts : opt){
                                    if(opts.canTargetMove()){
                                        moves = opts.getNumStepsTarget();
                                    }
                                }
                            }
                            //verify if the moves passed are correct
                            if(command.getMoves().size()<= moves){
                                try {
                                    //todo Sposta altri
                                    targets.get(0).move(new Movement(new ArrayList<>(command.getMoves())));
                                } catch (NotValidMovesException e) {
                                    String error ="Movimento non valido";
                                    notifier.notifyError(error, userJsonReceiver);
                                }
                            }
                            //verify if now target are valid
                            if((advanced == null && weaponToUse.getBaseEffect().get(0).getStrategy().areTargetValid(currentPlayer, targets))||(advanced != null && advanced.getStrategy().areTargetValid(currentPlayer, targets))){
                                shootState = ShootState.APPLYEFFECTDAMAGE;
                                for (OptionalEffect opts: opt){
                                    opts.setTargetAlreadyMoved(true);
                                }
                                applyDamage(currentPlayer, userJsonReceiver);
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
                else if(shootState.equals(ShootState.APPLYEFFECTDAMAGE)){
                    if((advanced == null && weaponToUse.getBaseEffect().get(0).canMoveTarget())||(advanced != null && advanced.canMoveTarget())|| canOptionalTargetMove()) {
                        //verify cannone
                        if (weaponToUse.getName().equals("Cannone vortex")) {
                            //todo
                        } else {
                            //verify leght of move
                            targets.get(0).setOldTile(targets.get(0).getTile());
                            int moves = 0;
                            if (advanced == null && weaponToUse.getBaseEffect().get(0).canMoveTarget()) {
                                moves = weaponToUse.getBaseEffect().get(0).getNumStepsTarget();
                            } else if (advanced != null && advanced.canMoveTarget()) {
                                moves = advanced.getNumStepsTarget();
                            } else {
                                for (OptionalEffect opts : opt) {
                                    if (opts.canTargetMove()) {
                                        moves = opts.getNumStepsTarget();
                                    }
                                }
                            }
                            //verify if the moves passed are correct
                            if (command.getMoves().size() <= moves) {
                                try {
                                    //todo Sposta altri
                                    targets.get(0).move(new Movement(new ArrayList<>(command.getMoves())));
                                } catch (NotValidMovesException e) {
                                    String error = "Movimento non valido";
                                    notifier.notifyError(error, userJsonReceiver);
                                }
                            }
                        }
                    }
                    shootEnded(userJsonReceiver);
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
                    LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    LOGGER.LOGGER.log(Level.WARNING,Arrays.toString(e.getStackTrace()));

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


        //starts a timer for the start of the game when the lobby reaches the min size

        Lobby lobby = gameManager.getLobby();
        //if the players are at least three and the timer did't start
        System.out.println("min capacity " + lobby.hasReachedMinCapacity());
        System.out.println("not game started " + !gameHasStarted);
        System.out.println("not timer started " + !startGameTimerStarted);
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                commandExecutorLogger.log(Level.INFO, "timer scaduto");
                try {
                    List<JsonReceiver> allReceivers = command.getAllReceivers();
                    //try to notify all the receivers. If rmi has disconnected, now it's disconnected for the server too
                    for (JsonReceiver jsonReceiver: allReceivers){
                        notifier.notifyMessage("La partita sta per iniziare", jsonReceiver);
                    }

                    //checking the receivers that are not associated to an user (because they are disconnected
                    List<JsonReceiver> disconnected = new LinkedList<>();
                    for(JsonReceiver jsonReceiver : allReceivers){
                        //if the user is null, a disconnection may have occurred
                        if (registry.getJsonUserOwner(jsonReceiver) == null){
                            disconnected.add(jsonReceiver);
                        }
                    }

                    //removing the mulfuntioning receivers
                    for(JsonReceiver jsonReceiver : disconnected){
                        allReceivers.remove(jsonReceiver);
                    }

                    if (allReceivers.size() >= gameManager.getLobby().getMinPlayerInLobby()){
                        createMatchRoutine(allReceivers);
                    }
                    else {
                        //notify that the match is not starting
                        for(JsonReceiver jsonReceiver: allReceivers){
                            notifier.notifyMessage("A causa di una disconnessione la partita non comincerà", jsonReceiver);
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        };
        if(lobby.hasReachedMinCapacity() && !gameHasStarted && !startGameTimerStarted){
            startGameTimer = new Timer();
            commandExecutorLogger.log(Level.INFO, "creazione e inizio del timer");
            startGameTimer.schedule(task, startMatchTimerDelay * thousand);
            startGameTimerStarted = true;
        }
        //if lobby reach 5, stop old timer and start a new one
        if (lobby.isFull() && !gameHasStarted && !lobby.isClosed()){
            startGameTimer.cancel();
            startGameTimer.purge();
            startGameTimer = new Timer();
            startGameTimer.schedule(task, 10*1000);
            lobby.closeLobby();
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
        User timerUser = null;
        for(JsonReceiver jr: receivers ){
            User userToBeNotified= TokenRegistry.getInstance().getJsonUserOwner(jr);
            Player player = userToBeNotified.getPlayer();
            String json = jsonCreator.createTargetPlayerJson("La partita è iniziata", player);
            jr.sendJson(json);
            if(userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
                userToBeNotifiedThrow = jr;
                timerUser = userToBeNotified;
            }
        }
        jsonCreator.reset();
        if(userToBeNotifiedThrow!=null)
        userToBeNotifiedThrow.sendJson(jsonCreator.createJsonWithMessage("scegli quale powerup scartare per spawnare"));
        jsonCreator.reset();
        //creates a timer for the first turn
        turnTimer = new Timer();
        turnTimer.schedule(new TurnTimerTask(launcher, userToBeNotifiedThrow, notifier, timerUser), turnLength*thousand);
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


    private void notifyExceptCurrent(JsonReceiver js, JsonReceiver userJsonReceiver, String message) throws IOException {
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

    private void undoMovement(Player currentPlayer, JsonReceiver userJsonReceiver, String message) {
        if(currentPlayer.getTile() != currentPlayer.getOldTile()) {
            currentPlayer.getOldTile().addPlayer(currentPlayer);
        }
        String error =message;
        notifier.notifyError(error, userJsonReceiver);
        currentPlayer.setOldTile(null);
    }

    private void undoWithoutError(Player currentPlayer){
        if(currentPlayer.getTile() != currentPlayer.getOldTile()) {
            currentPlayer.getOldTile().addPlayer(currentPlayer);
        }
        currentPlayer.setOldTile(null);
    }


    private boolean canMoveShooterOpt(){
        if(opt != null && !opt.isEmpty()) {
            for (OptionalEffect opts : opt) {
                if (opts.canShooterMove()) {
                    return true;
                }
            }
        }
        return false;
    }


    private void resetShoot(){
        shootState = ShootState.BASE;
        advanced = null;
        weaponToUse = null;
        targets.clear();
        opt.clear();
        System.out.println("dentro reset shoot: "+shootState.equals(ShootState.BASE));
        System.out.println("resettato lo shoot");
    }

    private void askOptional(Player currentPlayer, JsonReceiver userJsonReceiver) {
        shootState = ShootState.CHOOSEBASE;
        //verify if the current player can use an optional effect or if is present
        if(weaponToUse.canOpt(currentPlayer)){
            String message = "Scegli se vuoi usare gli effetti opzionali e quali: no opt, tutti opt o opt + il numero da 0 a "+(weaponToUse.getBaseEffect().get(0).getOptionalEffects().size()-1);
            notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
        }
        else{
            if(weaponToUse.getBaseEffect().get(0).canMoveShooter() && weaponToUse.getAdvancedEffect()!= null && weaponToUse.getAdvancedEffect().size()>0 && weaponToUse.getAdvancedEffect().get(0).canMoveShooter()){
                String message = "Scegli se vuoi muoverti con l'effetto advanced";
                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
            }
            else{
                String message = "Scegli chi vuoi colpire";
                notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
            }
        }
    }

    private void verifyHitMovedShooter(Player currentPlayer, JsonReceiver userJsonReceiver, MoveCommand command) throws IOException {
        if((advanced == null && !weaponToUse.getBaseEffect().get(0).getStrategy().canHitSomeone(currentPlayer))||(advanced != null && !advanced.getStrategy().canHitSomeone(currentPlayer))){
            resetShoot();
            command.endCommandToAction(gameManager);
            String error ="Non puoi colpire nessuno da questa posizione quindi hai perso la mossa";
            notifier.notifyError(error, userJsonReceiver);
        }
    }

    private boolean verifyTarget(List<String> name, List<Player> players){
        List<String> playerName = new ArrayList<>();
        for(Player pl: players){
            playerName.add(pl.getName());
        }
        for(String str: name){
            if(!playerName.contains(str) || str.equals(gameManager.getMatch().getCurrentPlayer().getName())){
                return false;
            }
        }
        return true;
    }

    private boolean canOptionalTargetMove(){
        if(opt != null && !opt.isEmpty()) {
            for (OptionalEffect opts : opt) {
                if (opts.canTargetMove()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void applyDamage(Player currentPlayer, JsonReceiver userJsonReceiver){
        shootState = ShootState.APPLYEFFECTDAMAGE;
        DamageTransporter dt = null;
        //target red
        if(advanced == null){
            weaponToUse.getBaseEffect().get(0).applyOptionalEffect(opt);

            dt =weaponToUse.getBaseEffect().get(0).useEffect(currentPlayer, targets.get(0), "red");
        }
        else{
            advanced.applyOptionalEffect(opt);
            dt = advanced.useEffect(currentPlayer, targets.get(0), "red");
        }
        targets.get(0).calculateDamage(dt);
        commandExecutorLogger.log(Level.INFO, "calculated damage for red "+dt.getNumDamage()+" damage and "+dt.getNumMark()+" marks to "+targets.get(0).getName());
        dt = null;
        //target blue
        if(advanced == null && targets.size()>1){
            if(weaponToUse.getBaseEffect().get(0).getDamage().get("blue") != 0 || weaponToUse.getBaseEffect().get(0).getMarks().get("blue") != 0){
                commandExecutorLogger.log(Level.INFO, "calculated  base damage transporter for blue target");
                dt =weaponToUse.getBaseEffect().get(0).useEffect(currentPlayer, targets.get(1), "blue");
            }
        }
        else if(targets.size()>1){
            if(advanced.getDamage().get("blue") != 0 || advanced.getMarks().get("blue") != 0) {
                commandExecutorLogger.log(Level.INFO, "calculated adv damage transporter for blue target");
                dt = advanced.useEffect(currentPlayer, targets.get(1), "blue");
            }
        }
        if(dt != null){
            targets.get(1).calculateDamage(dt);
            commandExecutorLogger.log(Level.INFO, "calculated damage for blue "+dt.getNumDamage()+" damage and "+dt.getNumMark()+" marks to "+targets.get(1).getName());
        }

        dt = null;
        //target green
        if(advanced == null && targets.size()>2){
            if(weaponToUse.getBaseEffect().get(0).getDamage().get("green") != 0 ||weaponToUse.getBaseEffect().get(0).getMarks().get("green") != 0){
                commandExecutorLogger.log(Level.INFO, "calculated base damage transporter for green target");
                dt =weaponToUse.getBaseEffect().get(0).useEffect(currentPlayer, targets.get(2), "green");
            }
        }
        else if(targets.size()>2){
            if(advanced.getDamage().get("green") != 0 || advanced.getMarks().get("green") != 0) {
                commandExecutorLogger.log(Level.INFO, "calculated adv damage transporter for green target");
                dt = advanced.useEffect(currentPlayer, targets.get(2), "green");
            }
        }
        if(dt != null){
            targets.get(2).calculateDamage(dt);
            commandExecutorLogger.log(Level.INFO, "calculated damage for red "+dt.getNumDamage()+" damage and "+dt.getNumMark()+" marks to "+targets.get(2).getName());
        }
        System.out.println("Arma prima reset opt: "+weaponToUse);
        //reset effect
        if(advanced == null){
            weaponToUse.getBaseEffect().get(0).resetDmgAndMarks();
        }
        else{
            advanced.resetDmgAndMarks();
        }
        commandExecutorLogger.log(Level.INFO, "effect resetted");

        System.out.println("Arma dopo reset opt: "+weaponToUse);

        //notify
        String message = "";
        if(advanced == null){
            if((!weaponToUse.getBaseEffect().get(0).isAlreadyMovedShooter() && weaponToUse.getBaseEffect().get(0).canMoveShooter())
                    ||(canMoveShooterOpt() && !weaponToUse.getBaseEffect().get(0).areOptionalAlreadyMoved(opt))) {
                message = "Puoi ancora muoverti se vuoi";
            }
            else if ((!weaponToUse.getBaseEffect().get(0).isAlreadyMovedTarget() && weaponToUse.getBaseEffect().get(0).canMoveTarget())
                    ||(canOptionalTargetMove() && !weaponToUse.getBaseEffect().get(0).areOptionalAlreadyMoved(opt))){
                message = "Puoi ancora muovere i target se vuoi";
            }
        }
        else{
            if((!advanced.isAlreadyMovedShooter() && advanced.canMoveShooter())
                    ||(canMoveShooterOpt() && !advanced.areOptionalAlreadyMoved(opt))) {
                message = "Puoi ancora muoverti se vuoi";
            }
            else if ((!advanced.isAlreadyMovedTarget() && advanced.canMoveTarget())
                    ||(canOptionalTargetMove() && !advanced.areOptionalAlreadyMoved(opt))){
                message = "Puoi ancora muovere i target se vuoi";
            }
        }
        notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
        weaponToUse.setLoaded(false);

        //verify if already moved or it can't, so if true end the routine
        boolean advancedShooterMoved = false;
        boolean advancedTargetMoved = false;
        boolean baseShooterMoved = false;
        boolean baseTargetMoved = false;
        boolean optMoved = false;
        if(advanced != null){
            System.out.println("nel ramo adv");
            advancedShooterMoved = advanced.isAlreadyMovedShooter() || !advanced.canMoveShooter();
            advancedTargetMoved = advanced.isAlreadyMovedTarget() || !advanced.canMoveTarget();
            if(advancedShooterMoved){
                shootEnded(userJsonReceiver);
            }
            else if(advancedTargetMoved){
                shootEnded(userJsonReceiver);
            }
            else{
                shootState = ShootState.APPLYEFFECTDAMAGE;
            }
        }
        else{
            System.out.println("nel ramo base");
            baseShooterMoved = !weaponToUse.getBaseEffect().get(0).canMoveShooter() || weaponToUse.getBaseEffect().get(0).isAlreadyMovedShooter();
            baseTargetMoved = !weaponToUse.getBaseEffect().get(0).canMoveTarget() || weaponToUse.getBaseEffect().get(0).isAlreadyMovedTarget();
            optMoved = opt!= null && !opt.isEmpty() && (!canMoveShooterOpt() || !canOptionalTargetMove() || weaponToUse.getBaseEffect().get(0).areOptionalAlreadyMoved(opt));

            if(baseShooterMoved || baseTargetMoved || optMoved){
                System.out.println("nel ramo base shoot end");
                shootEnded(userJsonReceiver);
            }
            else{
                shootState = ShootState.APPLYEFFECTDAMAGE;
            }

        }
        System.out.println("shootstate al termine dell'applicazione danno: " +shootState);
    }

    public String printTargetsName() {
        StringBuilder toReturn = new StringBuilder();
        for(Player p: targets) {
            toReturn.append(p.getName()+" ");
        }
        return toReturn.toString();
    }

    private void notifyTargetHit(List<JsonReceiver> receivers, JsonReceiver userJsonReceiver) throws IOException {
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        String message = "Target impostati corrrettamente e colpiti";
        notifier.notifyMessageTargetPlayer(message, userJsonReceiver, currentPlayer);
        message = "Sono stati colpiti: "+printTargetsName();
        for (JsonReceiver js : receivers) {
            notifyExceptCurrent(js, userJsonReceiver, message);
        }
        commandExecutorLogger.log(Level.INFO, "target selected correctly "+currentPlayer.getName());
    }

    private void shootEnded(JsonReceiver userJsonReceiver){
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        notifier.notifyMessageTargetPlayer("Hai sparato con successo a: "+printTargetsName(), userJsonReceiver, currentPlayer);
        resetShoot();
        currentPlayer.decrementMoves();
        currentPlayer.getState().nextState(currentPlayer.getOldState().getName(), currentPlayer);
        currentPlayer.setOldState(null);
        if(currentPlayer.getOldTile() !=null){
            currentPlayer.setOldTile(null);
        }

    }

}
