package controller;

import controller.commandpack.*;
import exceptions.InsufficientAmmoException;
import exceptions.NotValidActionException;
import exceptions.NotValidMovesException;
import exceptions.PickableNotPresentException;
import javafx.scene.paint.Color;
import model.*;
import network.TokenRegistry;

import java.io.IOException;
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

    /**
     * gameManager is a reference to the model due to access to the match and lobby variables
     */
    private GameManager gameManager;

    private JsonCreator jsonCreator;

    public CommandExecutor(GameManager gameManager, JsonCreator jsonCreator) {
        this.gameManager = gameManager;
        this.jsonCreator = jsonCreator;
    }


    public void execute(AskEndTurnCommand command) throws IOException {
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (hasMatchStarted(gameManager)) {
            Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
            Player owner = registry.getJsonUserOwner(userJsonReceiver).getPlayer();
            //verify if the owner is the current player
            if (owner != currentPlayer) {
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                //verify the player state
                if (!currentPlayer.getState().isNormalAction() && !currentPlayer.getState().isMoreAction() && !currentPlayer.getState().isMostAction()) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi terminare il tuo turno al momento"));
                } else {
                    currentPlayer.getState().nextState("EndTurn", currentPlayer);
                    //TODO prova, non so se metterli qua o no
                    gameManager.getMatch().endRound();
                    gameManager.getMatch().newRound();
                    String message = currentPlayer.getName()+" ha terminato il suo turno";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Hai terminato il tuo turno"));

//                    JsonReceiver userToBeNotifiedThrow = null;
//                    for(JsonReceiver jr : command.getAllReceivers()){
//                        User userToBeNotified= TokenRegistry.getInstance().getJsonUserOwner(jr);
//                        if(userToBeNotified.getPlayer().getName().equals(gameManager.getMatch().getCurrentPlayer().getName())){
//                            userToBeNotifiedThrow = jr;
//                        }
//                    }
//                    userToBeNotifiedThrow.sendJson(jsonCreator.createJsonWithMessage("E' iniziato il tuo turno"));
//                    jsonCreator.reset();
//                    currentPlayer = gameManager.getMatch().getCurrentPlayer();
//                    if((currentPlayer.getState().getName().equals("EndTurn")&& currentPlayer.getTile() == null) || currentPlayer.getState().getName().equals("Dead") || currentPlayer.getState().getName().equals("Overkilled")) {
//                        userToBeNotifiedThrow.sendJson(jsonCreator.createJsonWithMessage("scegli quale powerup scartare per spawnare"));
//                        jsonCreator.reset();
//                    }
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                //verify the state
                if (!currentPlayer.getState().canPickUp() || currentPlayer.getRemainingMoves() < 1) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi raccogliere"));
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("PickUp", currentPlayer);
                    String message = "Il giocatore attuale sta raccogliendo";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Se vuoi spostarti inserisci la direzione, altrimenti inserisci cosa vuoi raccogliere. (Munizioni o armi)"));
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                //verify the state
                if (!currentPlayer.getState().canReload()) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi ricaricare"));
                } else {
                    currentPlayer.getState().nextState("Reload", currentPlayer);
                    boolean hasWeapon = currentPlayer.getWeapons().isEmpty();
                    //verify if the player has weapon
                    if (!hasWeapon) {
                        String message = "Il giocatore attuale sta ricaricando";
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                js.sendJson(jsonCreator.createJsonWithMessage(message));
                            }
                        }
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Scegli quale arma ricaricare tra: " + currentPlayer.weaponsToString()));
                    } else {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai armi da ricaricare"));
                    }
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
            userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage(owner.getName() + " hai: " + points + "punti"));
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                boolean loaded = false;
                //verify if almost a weapon is loaded
                for (WeaponCard wpc : currentPlayer.getWeapons()) {
                    if (wpc.isLoaded()) {
                        loaded = true;
                    }
                }
                //verify the state
                if (!currentPlayer.getState().canShoot() || currentPlayer.getRemainingMoves() < 1 || !loaded) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi sparare"));
                } else {
                    currentPlayer.getState().nextState("Shoot", currentPlayer);
                    String message = "Il giocatore attuale sta per sparare";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Scegli con quale arma sparare tra: " + currentPlayer.weaponsToString()));
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi usare powerup"));
            } else {
                String message = "Il giocatore attuale sta usando un power up";
                for (JsonReceiver js : command.getAllReceivers()) {
                    if (js != userJsonReceiver) {
                        js.sendJson(jsonCreator.createJsonWithMessage(message));
                    }
                }
                userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Scegli quale power up usare tra: " + currentPlayer.powerUpToString()));
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                //verify the state
                if (!currentPlayer.getState().canRun() || currentPlayer.getRemainingMoves() < 1) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi muoverti"));
                } else {
                    currentPlayer.setOldState(currentPlayer.getState());
                    currentPlayer.getState().nextState("Run", currentPlayer);
                    String message = currentPlayer.getName()+" si sta spostando";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Inserisci le mosse che vuoi fare: (up, down, left, right)"));
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                if (currentPlayer.getState().getRemainingSteps() < command.getMoves().size()) {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai abbastanza mosse rimanenti"));
                } else {
                    try {
                        currentPlayer.getState().decrementRemainingSteps(command.getMoves().size());
                        currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
                        String message = currentPlayer.getName()+" si è spostato di nel tile: " +currentPlayer.getTile().getID();
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                User userToBenotified = registry.getJsonUserOwner(js);
                                Player userPlayer = userToBenotified.getPlayer();
                                js.sendJson(jsonCreator.createTargetPlayerJson(message, userPlayer));
                            }
                        }
                        userJsonReceiver.sendJson(jsonCreator.createTargetPlayerJson("Ti sei spostato nel tile :" + currentPlayer.getTile().getID(), currentPlayer));
                    } catch (NotValidMovesException e) {
                        currentPlayer.getState().resetRemainingSteps();
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Movimento non valido"));
                    }
                }
                if (currentPlayer.getState().getName().equals("Run")) {
                    command.endCommandToAction(gameManager);
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                //verify the state
                if (currentPlayer.getState().getName().equals("PickUp")) {
                    Tile oldTile = currentPlayer.getTile();
                    //player movement
                    try {
                        currentPlayer.move(new Movement(command.getMoves()));
                    } catch (NotValidMovesException e) {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Movimento non valido"));
                    }

                    //verify if it is an ammo tile
                    if (currentPlayer.getTile().canContainAmmo()) {
                        //verify if an ammo card is present
                        if (currentPlayer.getTile().isPresentAmmoCard()) {
                            currentPlayer.getState().remainingStepsToZero();

                            AmmoCard ammoCard = currentPlayer.getTile().pickUpAmmoCard();
                            //draw
                            currentPlayer.useAmmoCard(ammoCard, gameManager.getMatch().getPowerUpDeck());
                            //put the card in the slush pile
                            gameManager.getMatch().getAmmoSlushPile().addCard(ammoCard);

                            //notifyMessage
                            String message = "Il giocatore attuale ha raccolto una carta munizioni";
                            for (JsonReceiver js : command.getAllReceivers()) {
                                if (js != userJsonReceiver) {
                                    js.sendJson(jsonCreator.createJsonWithMessage(message));
                                }
                            }
                            userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Hai raccolto una carta munizioni"));
                            //decrement moves of player and return to action selector
                            command.endCommandToAction(gameManager);
                        }
                        else {
                            //return to old state
                            oldTile.addPlayer(currentPlayer);
                            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Nel tuo tile non c'è niente da raccogliere"));
                        }
                    } else {
                        //verify if there are weapon in the tile
                        if (!currentPlayer.getTile().getWeapons().isEmpty()) {
                            //set player remaining steps to zero
                            currentPlayer.getState().remainingStepsToZero();

                            WeaponCard weaponCard = null;
                            int count = 0;
                            if (command.getWeaponName() == null) {
                                //return to old state
                                oldTile.addPlayer(currentPlayer);
                                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non è presente l'arma da te inserita"));
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
                                    oldTile.addPlayer(currentPlayer);
                                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non sono presenti armi in questo tile"));
                                }
                                //if the weapon selected was not present
                                if (weaponCard == null) {
                                    //return to old state
                                    oldTile.addPlayer(currentPlayer);
                                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non è presente l'arma da te inserita"));
                                }
                                else {
                                    try {
                                        currentPlayer.pickUpWeapon(weaponCard);
                                    } catch (Exception e) {
                                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("hai più armi di quante consentite, scegline una da scartare tra: " + currentPlayer.weaponsToString()));
                                    } finally {
                                        String message = "Il giocatore attuale ha raccolto: " + weaponCard.getName();
                                        for (JsonReceiver js : command.getAllReceivers()) {
                                            if (js != userJsonReceiver) {
                                                js.sendJson(jsonCreator.createJsonWithMessage(message));
                                            }
                                        }
                                       userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Hai raccolto " + weaponCard.getName()));
                                    }

                                }
                                //decrement moves of player and return to action selector
                                command.endCommandToAction(gameManager);
                            }
                        }
                        else {
                            //return to old state
                            oldTile.addPlayer(currentPlayer);
                            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Nel tuo tile non c'è niente da raccogliere"));
                        }
                    }
                }
                else {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Comando non valido"));
                }
            }
        }
        else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else {
                if (command.getWeaponName() == null) {
                    for (WeaponCard wpc : currentPlayer.getWeapons()) {
                        if (wpc.getName().equals(command.getWeaponName())) {
                            if (!wpc.isLoaded()) {
                                try {
                                    wpc.reload(currentPlayer.getPlayerBoard().getLoader());
                                    String message = "Il giocatore attuale ha ricaricato: " + wpc.getName();
                                    for (JsonReceiver js : command.getAllReceivers()) {
                                        if (js != userJsonReceiver) {
                                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                                        }
                                    }
                                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Arma ricaricata, vuoi ricaricare un'altra arma o finire il turno?"));
                                } catch (InsufficientAmmoException e) {
                                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai abbastanza munizioni per ricricare l'arma selezionata"));
                                }
                            } else {
                                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("L'arma selezionata è già carica, vuoi ricaricare un'altra arma oppure finire il turno?"));
                            }
                        }
                    }
                }
                else{
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai fornito il nome dell'arma"));
                }
            }
        }
        else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
            } else{
                String state = currentPlayer.getState().getName();
                //verify the player state
                if ((state.equals("EndTurn")&& currentPlayer.getTile() == null) || state.equals("Dead") ||state.equals("Overkilled")) {
                    //verify if the current player has a powerup
                    if (!currentPlayer.hasPowerUp(powerUpParser(command.getPowerUpType()), colorParser(command.getColor()))) {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai questo PowerUp"));
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
                            if (js != userJsonReceiver) {
                                User userToBenotified = registry.getJsonUserOwner(js);
                                Player userPlayer = userToBenotified.getPlayer();
                                js.sendJson(jsonCreator.createTargetPlayerJson(message, userPlayer));
                            }
                        }
                        userJsonReceiver.sendJson(jsonCreator.createTargetPlayerJson("Ti sei rigenerato nel punto di rigenerazione " + regenPointColor, currentPlayer));
                    }
                }
                else{
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Azione non consentita"));
                }
            }
        }else{
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La partita non è ancora iniziata"));
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
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("La tua frase ad effetto è stata modificata"));
                } else {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare la tua frase ad effetto"));
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare la tua frase perchè la partita è iniziata"));
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
                        js.sendJson(jsonCreator.createJsonWithMessage("Il numero di uccisioni per la partita è stato cambiato a: " + command.getDeath()));
                    }
                } else {
                    if (numOfDeathWanted > 8 || numOfDeathWanted < 5) {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Numero uccisioni non nel range ammissibile"));
                    } else {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Operazione non consentita"));
                    }
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare il numero di morti perchè è iniziata la partita"));
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
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Numero uccisioni non nel range ammissibile"));
                    } else {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Operazione non consentita"));

                    }
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare il numero di giocatori perchè la partita è già iniziata"));
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
            //verify if the username is already used
            if (!registry.usernameAlreadyPresent(command.getUsername())) {
                //verify if the user has already been created
                if (registry.getJsonUserOwner(userJsonReceiver)== null){
                    User user = new User(command.getUsername());
                    registry.associateReceiverAndUser(userJsonReceiver, user);
                    registry.associateTokenAndUser(clientToken, user);
                    try {
                        gameManager.getLobby().join(user);
                        String jsonTosend = jsonCreator.createJsonWithMessage("Utente creato. Il tuo nome è " +user.getUsername());
                        userJsonReceiver.sendJson(jsonTosend);
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                js.sendJson(jsonCreator.createJsonWithMessage("Si è aggiunto un nuovo user alla lobby"));
                            }
                        }
                    } catch (NotValidActionException e) {
                        e.printStackTrace();
                    }
                }
                else if (users.contains(registry.getJsonUserOwner(command.getJsonReceiver()))) {
                    registry.getJsonUserOwner(command.getJsonReceiver()).setUsername(command.getUsername());
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Il tuo username è stato modificato in: " + command.getUsername()));
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("username già presente"));
            }
        }
        else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare il tuo username perchè la partita è già iniziata"));
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
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Personaggio modificato in " + command.getPlayerToken()));
                    } else {
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Personaggio già scelto"));
                    }
                }
                else{
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Personaggio non valido"));
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }
        else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare il tuo personaggio perchè la partita è già iniziata"));
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
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Operazione non consentita"));
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare la mappa perchè la partita è già iniziata"));
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
                        userJsonReceiver.sendJson(jsonCreator.createJsonWithError("La frenesia finale era già impostata al valore inserito"));
                    }
                }
                else {
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Operazione non consentita"));
                }
            }
            else{
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare la frenesia finale perchè la partita è già iniziata"));
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


    private void notifyToAll(){

    }
}
