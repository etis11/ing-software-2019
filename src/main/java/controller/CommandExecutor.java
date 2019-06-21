package controller;

import controller.commandpack.*;
import exceptions.InsufficientAmmoException;
import exceptions.NotValidActionException;
import exceptions.NotValidMovesException;
import exceptions.PickableNotPresentException;
import model.*;
import network.TokenRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private final TokenRegistry registry = TokenRegistry.getInstance();

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
                    String message = "Il giocatore attuale ha terminato il suo turno";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    userJsonReceiver.sendJson(jsonCreator.createJsonWithMessage("Hai terminato il tuo turno"));
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
                    currentPlayer.getState().nextState("Run", currentPlayer);
                    String message = "Il giocatore attuale si sta spostando";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != userJsonReceiver) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
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
                        String message = "Il giocatore attuale si è spostato di: " + command.getMoves().size() + " mosse";
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != userJsonReceiver) {
                                js.sendJson(jsonCreator.createJsonWithMessage(message));
                            }
                        }
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
                    gameManager.getMatch().setPlayerNumber(numOfPlayersWanted);
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
        //verify if the game is started
        if (!gameHasStarted) {
            //verify if the username is already used
            if (!registry.usernameAlreadyPresent(command.getUsername())) {
                //verify if the user has already been created
                if (registry.getJsonUserOwner(userJsonReceiver)== null){
                    User user = new User(command.getUsername());
                    registry.associateReceiverAndUser(userJsonReceiver, user);
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
        /*
        * TODO adesso bisogna controllare che la lobby non sia piena. Se è piena, viene creata la partita e vengono fatte
        * tutte le notifiche ai client
        * */
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
                    gameManager.setMap(mapWanted);
                    for (JsonReceiver jr : command.getAllReceivers()) {
                        jr.sendJson(jsonCreator.createJsonWithMessage("La mappa per la partita è stata cambiata"));
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

    private boolean hasMatchStarted(GameManager gm){
        return gm.isMatchStarted();
    }
}
