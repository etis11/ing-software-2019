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

        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            if (!currentPlayer.getState().isNormalAction() && !currentPlayer.getState().isMoreAction() && !currentPlayer.getState().isMostAction()) {
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi terminare il tuo turno al momento"));
            } else {
                currentPlayer.getState().nextState("EndTurn", currentPlayer);
                String message = "Il giocatore attuale ha terminato il suo turno";
                for (JsonReceiver js : command.getAllReceivers()){
                    if (js!=command.getJsonReceiver()) {
                        js.sendJson(jsonCreator.createJsonWithMessage(message));
                    }
                }
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Hai terminato il tuo turno"));
            }
        }
        jsonCreator.reset();
    }

    public void execute(AskPickCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            if (!currentPlayer.getState().canPickUp() || currentPlayer.getRemainingMoves() < 1) {
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi raccogliere"));
            } else {
                currentPlayer.setOldState(currentPlayer.getState());
                currentPlayer.getState().nextState("PickUp", currentPlayer);
                String message = "Il giocatore attuale sta raccogliendo";
            for (JsonReceiver js : command.getAllReceivers()){
                if (js!=command.getJsonReceiver()) {
                    js.sendJson(jsonCreator.createJsonWithMessage(message));
                }
            }
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Se vuoi spostarti inserisci la direzione, altrimenti inserisci cosa vuoi raccogliere. (Munizioni o armi)"));
            }
        }
        jsonCreator.reset();
    }

    public void execute(AskReloadCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        }
        else {
            if (!currentPlayer.getState().canReload()) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi ricaricare"));
            }
            else {
                currentPlayer.getState().nextState("Reload", currentPlayer);
                if (!currentPlayer.getWeapons().isEmpty()) {
                    String message = "Il giocatore attuale sta ricaricando";
                    for (JsonReceiver js : command.getAllReceivers()){
                        if (js!=command.getJsonReceiver()) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Scegli quale arma ricaricare tra: " + currentPlayer.weaponsToString()));
                } else {
                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non hai armi da ricaricare"));
                }
            }
        }
        jsonCreator.reset();
    }

    public void execute(AskPointsCommand command) throws IOException {
        int points = registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer().getPoints();
        command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage(registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer().getName()+ " hai: "+points+ "punti"));
    }

    public void execute(AskShootCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            boolean loaded = false;
            //verify if almost a weapon is loaded
            for (WeaponCard wpc : currentPlayer.getWeapons()) {
                if (wpc.isLoaded()) {
                    loaded = true;
                }
            }
            if (!currentPlayer.getState().canShoot() || currentPlayer.getRemainingMoves() < 1 || !loaded) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi sparare"));
            } else {
                currentPlayer.getState().nextState("Shoot", currentPlayer);
                String message = "Il giocatore attuale sta per sparare";
                for (JsonReceiver js : command.getAllReceivers()){
                    if (js!=command.getJsonReceiver()) {
                        js.sendJson(jsonCreator.createJsonWithMessage(message));
                    }
                }
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Scegli con quale arma sparare tra: " + currentPlayer.weaponsToString()));
            }
        }
        jsonCreator.reset();
    }

    public void execute(AskUsePowerUpCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (!currentPlayer.getState().canUsePowerUp() || currentPlayer.getPowerUps().isEmpty()) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi usare powerup"));
        } else {
            String message = "Il giocatore attuale sta usando un power up";
            for (JsonReceiver js : command.getAllReceivers()){
                if (js!=command.getJsonReceiver()) {
                    js.sendJson(jsonCreator.createJsonWithMessage(message));
                }
            }
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Scegli quale power up usare tra: "+currentPlayer.powerUpToString()));
        }
        jsonCreator.reset();
    }

    public void execute(AskWalkCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            if (!currentPlayer.getState().canRun() || currentPlayer.getRemainingMoves() < 1) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi muoverti"));
            } else {
                currentPlayer.getState().nextState("Run", currentPlayer);
                String message = "Il giocatore attuale si sta spostando";
                for (JsonReceiver js : command.getAllReceivers()){
                    if (js!=command.getJsonReceiver()) {
                        js.sendJson(jsonCreator.createJsonWithMessage(message));
                    }
                }
            }
        }
        jsonCreator.reset();
    }

    public void execute(MoveCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            if (currentPlayer.getState().getRemainingSteps() < command.getMoves().size()) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non hai abbastanza mosse rimanenti"));
            } else {
                try {
                    currentPlayer.getState().decrementRemainingSteps(command.getMoves().size());
                    currentPlayer.move(new Movement(new ArrayList<>(command.getMoves())));
                    String message = "Il giocatore attuale si è spostato di: " + command.getMoves().size() + " mosse";
                    for (JsonReceiver js : command.getAllReceivers()) {
                        if (js != command.getJsonReceiver()) {
                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                        }
                    }
                }
                catch (NotValidMovesException e){
                    currentPlayer.getState().resetRemainingSteps();
                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Movimento non valido"));
                }
            }

            if (currentPlayer.getState().getName().equals("Run")) {
                command.endCommandToAction(gameManager);
            }
        }
        jsonCreator.reset();
    }

    public void execute(PickUpCommand command) throws IOException {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() != currentPlayer) {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi eseguire questa azione se non è il tuo turno"));
        } else {
            if (currentPlayer.getState().getName().equals("PickUp")) {
                Tile oldTile = currentPlayer.getTile();
                //player movement
                try {
                    currentPlayer.move(new Movement(command.getMoves()));
                } catch (NotValidMovesException e) {
                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Movimento non valido"));
                }

                //verify if it is an ammo tile
                if (currentPlayer.getTile().canContainAmmo()) {
                    if(currentPlayer.getTile().isPresentAmmoCard()) {
                        currentPlayer.getState().remainingStepsToZero();

                        AmmoCard ammoCard = currentPlayer.getTile().pickUpAmmoCard();
                        //draw
                        currentPlayer.useAmmoCard(ammoCard, gameManager.getMatch().getPowerUpDeck());
                        //put the card in the slush pile
                        gameManager.getMatch().getAmmoSlushPile().addCard(ammoCard);

                        //notify
                        String message = "Il giocatore attuale ha raccolto una carta munizioni";
                        for (JsonReceiver js : command.getAllReceivers()) {
                            if (js != command.getJsonReceiver()) {
                                js.sendJson(jsonCreator.createJsonWithMessage(message));
                            }
                        }
                        command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Hai raccolto una carta munizioni"));
                        //decrement moves of player and return to action selector
                        command.endCommandToAction(gameManager);
                    }
                    else{
                        //return to old state
                        oldTile.addPlayer(currentPlayer);
                        command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Nel tuo tile non c'è niente da raccogliere"));
                    }
                }
                else{
                    //TODO weapon
                    if(!currentPlayer.getTile().getWeapons().isEmpty()){
                        //set player remaining steps to zero
                        currentPlayer.getState().remainingStepsToZero();

                        WeaponCard weaponCard = null;
                        int count = 0;
                        if (command.getWeaponName() == null) {
                            //return to old state
                            oldTile.addPlayer(currentPlayer);
                            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non è presente l'arma da te inserita"));
                        }
                        else{
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
                                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non sono presenti armi in questo tile"));
                            }
                            if (weaponCard == null) {
                                //return to old state
                                oldTile.addPlayer(currentPlayer);
                                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non è presente l'arma da te inserita"));
                            }
                            else {
                                try {
                                    currentPlayer.pickUpWeapon(weaponCard);
                                } catch (Exception e) {
                                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("hai più armi di quante consentite, scegline una da scartare tra: "+currentPlayer.weaponsToString()));
                                } finally {
                                    String message = "Il giocatore attuale ha raccolto: " + weaponCard.getName();
                                    for (JsonReceiver js : command.getAllReceivers()) {
                                        if (js != command.getJsonReceiver()) {
                                            js.sendJson(jsonCreator.createJsonWithMessage(message));
                                        }
                                    }
                                    command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Hai raccolto "+weaponCard.getName()));
                                }

                            }
                            //decrement moves of player and return to action selector
                            command.endCommandToAction(gameManager);
                        }
                    }
                    else{
                        //return to old state
                        oldTile.addPlayer(currentPlayer);
                        command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Nel tuo tile non c'è niente da raccogliere"));
                    }
                }
            } else {
//            command.getOriginView().notify("Comando non valido")
                    //TODO o lancio exception??
            }
        }
        jsonCreator.reset();
    }

    public void execute(ReloadCommand command) {
        //auxiliary variable
        Player currentPlayer = gameManager.getMatch().getCurrentPlayer();
        if (!(registry.getJsonUserOwner(command.getJsonReceiver()).getPlayer() == currentPlayer)) {
            //ERRORE, comunica al receiver  command.getJsonReceiver().sendJson()
        } else {
            if (command.getWeaponName() == null) throw new IllegalArgumentException("no weapon selected");
            for (WeaponCard wpc : currentPlayer.getWeapons()) {
                if (wpc.getName().equals(command.getWeaponName())) {
                    if (!wpc.isLoaded()) {
                        try {
                            wpc.reload(currentPlayer.getPlayerBoard().getLoader());
                            String message = "Il giocatore attuale ha ricaricato: " + wpc.getName();
//                        for (MessageListener view : command.getAllViews()) {
//                            view.notify(message)
//                        }
//                        command.getOriginView().notify("vuoi ricaricare un'altra arma oppure finire il turno?")
                        } catch (InsufficientAmmoException e) {
//                        command.getOriginView().notify("Non hai le munizioni necessarie per ricaricare ques'arma, vuoi ricaricare un'altra arma oppure finire il turno?")
                        }
                    } else {
//                    command.getOriginView().notify("L'arma selezionata è già carica, vuoi ricaricare un'altra arma oppure finire il turno?")
                    }
                }
            }
        }
    }

    public void execute(SetEffectPhraseCommand command) throws IOException {
        if (!gameManager.getMatch().isStarted()) {
            if (gameManager.getLobby().getUsers().contains(registry.getJsonUserOwner(command.getJsonReceiver()))) {
                registry.getJsonUserOwner(command.getJsonReceiver()).setEffectPhrase(command.getPhrase());
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("La tua frase ad effetto è stata modificata"));
            } else {
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi modificare la tua frase ad effetto"));
            }
        } else {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi modificare la tua frase perchè la partita è iniziata"));
        }
        jsonCreator.reset();
    }

    public void execute(SetNumberOfDeathCommand command) throws IOException {
        boolean gameHasStarted = gameManager.getMatch().isStarted();
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
                    gameManager.getMatch().setSkulls(numOfDeathWanted);
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
        boolean gameHasStarted = gameManager.getMatch().isStarted();
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
        boolean gameHasStarted = gameManager.getMatch().isStarted();
        List<User> users = gameManager.getLobby().getUsers();
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if the game is started
        if (!gameHasStarted) {
            //verify if the username has already used
            if (!registry.usernameAlreadyPresent(command.getUsername())) {
                //verify if the user has already been created
                if (registry.getJsonUserOwner(userJsonReceiver)== null){
                    User user = new User(command.getUsername());
                    registry.associateReceiverAndUser(userJsonReceiver, user);
                    try {
                        gameManager.getLobby().join(user);
                    } catch (NotValidActionException e) {
                        e.printStackTrace();
                    }
                }
                else if (users.contains(registry.getJsonUserOwner(command.getJsonReceiver()))) {
                    registry.getJsonUserOwner(command.getJsonReceiver()).setUsername(command.getUsername());
                        command.getJsonReceiver().sendJson(jsonCreator.createJsonWithMessage("Il tuo username è stato modificato in: " + command.getUsername()));
                }
            }
            else{
                command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("username già presente"));
            }
        }
        else {
            command.getJsonReceiver().sendJson(jsonCreator.createJsonWithError("Non puoi modificare il tuo username perchè la partita è già iniziata"));
        }
        jsonCreator.reset();
    }

    public void execute(SetTokenCommand command) throws IOException {
        boolean gameHasStarted = gameManager.getMatch().isStarted();
        JsonReceiver userJsonReceiver = command.getJsonReceiver();
        //verify if game started
        if (!gameHasStarted) {
            //verify if the user has already been created
            if (registry.getJsonUserOwner(userJsonReceiver) != null) {
                List<String> usersToken = gameManager.getLobby().getNameToken();
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
                userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non hai ancora impostato uno username"));
            }
        }
        else {
            userJsonReceiver.sendJson(jsonCreator.createJsonWithError("Non puoi modificare il tuo personaggio perchè la partita è già iniziata"));
        }
        jsonCreator.reset();
    }
}
