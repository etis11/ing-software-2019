package view;

import controller.CommandContainer;
import controller.commandpack.*;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedPlayer;
import network.RMI.ClientLauncherRMI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parserator implements Runnable {
    private CommandContainer commandLauncher;
    private String token;
    private List<String> weapons;
    private CommandLineInterface CLI;
    private boolean quit;
    private SemplifiedGame game;

    /*
    String needed to return all possible commands that you can call on CLI
     */
    private String commandi=AnsiColor.GREEN+"quit"+AnsiColor.RESET+" - Uscire dal gioco\n" +
            AnsiColor.GREEN+"muovi"+AnsiColor.RESET+" - Richiede se il giocatore può muoversi\n" +
            AnsiColor.GREEN+"spara"+AnsiColor.RESET+" - Richiede se il giocatore può sparare\n" +
            AnsiColor.GREEN+"raccogli"+AnsiColor.RESET+" - Richiede se il giocatore può raccogliere\n" +
            AnsiColor.GREEN+"ricarica"+AnsiColor.RESET+" - Comando per ricaricare un'arma\n" +
            AnsiColor.GREEN+"fineturno"+AnsiColor.RESET+" - Comando per terminare il turno\n" +
            AnsiColor.GREEN+"powerup"+AnsiColor.RESET+" - Commando che serve per chiedere conferma di poter usare un PowerUp\n" +
            AnsiColor.GREEN+"setusername *NOME*"+AnsiColor.RESET+" - Commando che serve per defnire il nome del tuo player. Es: setusername Paolo\n" +
            AnsiColor.GREEN+"setfraseeffetto *FRASE*"+AnsiColor.RESET+" - Commando che serve per dare una frase al tuo player.Es: setphraseeffect This is Sparta!\n" +
            AnsiColor.GREEN+"setuccisioni *NUMERO*"+AnsiColor.RESET+" - Commando che decide il numero di uccisioni per la partita. Es: setuccisioni 4\n" +
            AnsiColor.GREEN+"setgiocatori *NUMERO*"+AnsiColor.RESET+" - Comando che serve per impostare il numero di giocatori nella partita. Es: setgiocatori 5\n" +
            AnsiColor.GREEN+"punti"+AnsiColor.RESET+" - Commando che ritorna i punti del giocatore\n" +
            AnsiColor.GREEN+"setpersonaggio *nome*"+AnsiColor.RESET+" - Commando per scegliere il personaggio del gioco. Es: setpersonaggio violetta\n"+
            AnsiColor.GREEN+"setfrenesia *si/no*"+AnsiColor.RESET+" - Commando che imposta la frenesia finale per la partita. Es: setfrenesia no\n";

    public Parserator(CommandLineInterface cli, CommandContainer launcher, SemplifiedGame game) {
        CLI = cli;
        commandLauncher = launcher;
        quit = false;
        token = ClientSingleton.getInstance().getToken();
        this.game = game;
        createListWeapon();
    }

    @Override
    public void run() {
        while (!quit) {
            try {
                this.parseCommand(CLI.getUserInputString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException i) {
                CLI.displayText(AnsiColor.RED + "Nessun comando esistente con questo formato" + AnsiColor.RESET);
            }
        }
        CLILauncher.stopCLI();
    }

    public void parseCommand(String command) throws RemoteException {
        String param = "";
        String realCommand = "";
        if (command.contains(" ")) {
            String[] splittedCommand = command.split(" ");
            if (splittedCommand.length > 0) {
                realCommand = splittedCommand[0];
            }
            if (splittedCommand.length > 1) {
                param = splittedCommand[1];
            }
            if(command.contains("setfraseeffetto")){
                String toSet="";
                for(int i=1;i<splittedCommand.length;i++){
                    toSet=" "+splittedCommand[i];
                }
                param= toSet;
            }

        } else {
            realCommand = command;
        }
        try {
            switch (realCommand.toLowerCase()) {
                case "setmap":
                    commandLauncher.addCommand((new SetMapCommand(token,param)));
                    return;
                case "help":
                    CLI.displayText(commandi);
                    return;
                case "quit":
                    quit = true;
                    return;
                case "muovi":
                    commandLauncher.addCommand(new AskWalkCommand(token));
                    return;
                case "raccogli":
                    commandLauncher.addCommand(new AskPickCommand(token));
                    return;
                case "spara":
                    commandLauncher.addCommand(new AskShootCommand(token));
                    return;
                case "ricarica":
                    commandLauncher.addCommand(new AskReloadCommand(token));
                    return;
                case "fineturno":
                    commandLauncher.addCommand(new AskEndTurnCommand(token));
                    return;
                case "powerup":
                    commandLauncher.addCommand(new AskUsePowerUpCommand(token));
                    return;
                case "setusername":
                case "s":
                    commandLauncher.addCommand(new SetUsernameCommand(token, param));
                    return;
                case "setfraseeffetto":
                    commandLauncher.addCommand(new SetEffectPhraseCommand(token, param));
                    return;
                case "setuccisioni":
                    commandLauncher.addCommand(new SetNumberOfDeathCommand(token, Integer.valueOf(param)));
                    return;
                case "setgiocatori":
                    commandLauncher.addCommand(new SetPlayerNumberCommand(token, Integer.valueOf(param)));
                    return;
                case "punti":
                    commandLauncher.addCommand(new AskPointsCommand(token));
                    return;
                case "setpersonaggio":
                    commandLauncher.addCommand(new SetTokenCommand(token, param.toLowerCase()));
                    return;
                case "setfrenesia":
                    commandLauncher.addCommand(new SetFinalFrenzyCommand(token, parseFrenzy(param)));
                    return;
            }

            if (command.contains("target")) {
                List<String> toadd = new ArrayList<>();
                if(!command.contains("none")) {
                    toadd.addAll(Arrays.asList(command.split(" ")[1].split(",")));
                }
                commandLauncher.addCommand(new MoveTargetCommand(token, toadd));
                return;
            }
            if ((command.contains("up") || command.contains("right") || command.contains("left") || command.contains("down")|| command.contains("none")) && !command.contains("Cannone") && !command.contains("usapu")) {
                List<String> toadd = new ArrayList<>();
                if(!command.contains("none")) {
                    toadd.addAll(Arrays.asList(command.split(",")));
                }
                commandLauncher.addCommand(new MoveCommand(token, toadd));
                return;
            }
            if((command.toLowerCase().contains("sprog") || command.toLowerCase().contains("distruttore") || command.toLowerCase().contains("dozer")
                    || command.toLowerCase().contains("violetta")||command.toLowerCase().contains("banshee")) && !command.toLowerCase().contains("usa")){
                List<String> toadd = new ArrayList<>();
                if(command.contains("mirino")){
                    String[] splittedCommand = command.split(" ");
                    toadd.addAll(Arrays.asList(splittedCommand[0].split(",")));
                    //sprog,violetta mirino blu sprog rosso
                    if(splittedCommand.length == 5 && toadd.contains(splittedCommand[3]) && isValidColor(splittedCommand[2]) && splittedCommand[1].equals("mirino") &&
                        isNameValid(toadd) && isValidColor(splittedCommand[4])) {
                        commandLauncher.addCommand(new ChooseTargetCommand(token, toadd, splittedCommand[2], splittedCommand[3], splittedCommand[4]));
                        return;
                    }
                }
                else {
                    toadd.addAll(Arrays.asList(command.split(",")));
                    if (isNameValid(toadd)) {
                        commandLauncher.addCommand(new ChooseTargetCommand(token, toadd, null, null, null));
                        return;
                    }
                }
            }
            String power = command.toLowerCase();
            if ((power.contains("granata")||power.contains("teletrasporto")||power.contains("mirino")||power.contains("raggiocinetico"))&& power.contains("usapu")){
                String[] splittedPower = power.split(" ");
                if(isValidColor(splittedPower[2])) {
                    if(splittedPower.length == 5) {
                        commandLauncher.addCommand(new UsePowerUpCommand(token, splittedPower[1], splittedPower[2], splittedPower[3], splittedPower[4]));
                    }else{
                        commandLauncher.addCommand(new UsePowerUpCommand(token, splittedPower[1], splittedPower[2], splittedPower[3], ""));
                    }
                    return;
                }
            }
            if ((power.contains("granata")||power.contains("teletrasporto")||power.contains("mirino") ||power.contains("raggiocinetico")
                    && !(command.toLowerCase().contains("sprog") || command.toLowerCase().contains("distruttore")
                    || command.toLowerCase().contains("dozer")|| command.toLowerCase().contains("violetta")||command.toLowerCase().contains("banshee")))){
                String[] splittedPower = power.split(" ");
                if(isValidColor(splittedPower[1])) {
                    commandLauncher.addCommand(new SpawnCommand(token, splittedPower[0], splittedPower[1]));
                    return;
                }
            }
            if(command.toLowerCase().contains("prendi")){
                String[] splittedCommand = command.split(" ",2);
                if(isWeapon(splittedCommand[1])) {
                    commandLauncher.addCommand(new PickUpCommand(token, splittedCommand[1]));
                    return;
                }
            }
            if(command.toLowerCase().contains("usa")){
                String[] splittedCommand = command.split(" ", 2);
                if (splittedCommand[1] != null) {
                    if (isWeapon(splittedCommand[1])) {
                        commandLauncher.addCommand(new WeaponCommand(token, splittedCommand[1]));
                        return;
                    }
                }
            }
            if(command.toLowerCase().contains("scarta")){
                String[] splittedCommand = command.split(" ",2);
                if(isWeapon(splittedCommand[1])) {
                    commandLauncher.addCommand(new ThrowWeaponCommand(token, splittedCommand[1]));
                    return;
                }
            }
            if (isWeapon(command) && !command.toLowerCase().contains("usa") && !command.toLowerCase().contains("prendi")){
                commandLauncher.addCommand(new ReloadCommand(token, command));
                return;
            }
            if(command.contains("opt")){
                String[] splittedCommand = command.split(" ");
                if(splittedCommand[0].equals("opt")){
                    commandLauncher.addCommand(new ChooseOptEffectCommand(token, splittedCommand[1]));
                }
                else {
                    commandLauncher.addCommand(new ChooseOptEffectCommand(token, splittedCommand[0]));
                }
                return;
            }
            if(command.trim().equalsIgnoreCase("avanzato") || command.trim().equalsIgnoreCase("base")){
                commandLauncher.addCommand(new ChooseAdvanceCommand(token, command));
                return;
            }

            if ( command.contains("mostra")){
                String[] splittedCommand = command.split(" ");
                //should be at least two words
                if(splittedCommand.length >1){
                    String paramater = splittedCommand[1];
                    if(game.getMap()!= null &&paramater.equals("mappa")){
                        CLI.onMapChange(game.getMap());
                        return;
                    }

                }
                if(splittedCommand.length>2){
                    String board = splittedCommand[1];
                    String playerName = splittedCommand[2];
                    if ( game.getPlayers() != null && board.equals("plancia")){
                        SemplifiedPlayer player = null;
                        for(SemplifiedPlayer loopPlayer : game.getPlayers()){
                            if(loopPlayer.getName().equals(playerName))
                                player = loopPlayer;
                        }
                        if(player!= null){
                            CLI.onPlayerChange(player);
                            return;
                        }
                    }
                }
            }

            throw new IllegalArgumentException();
        } catch (RemoteException r) {
            CLI.displayText(AnsiColor.RED + "Server rmi non raggiungibile" + AnsiColor.RESET);
            CLI.displayText("Il tuo token è: " + CLI.getToken());
            System.exit(1);
        }
    }

    private boolean parseFrenzy(String param){
        return !param.equalsIgnoreCase("no");
    }

    private void createListWeapon(){
        weapons = new ArrayList<>();
        weapons.add("Distruttore");
        weapons.add("Mitragliatrice");
        weapons.add("Torpedine");
        weapons.add("Fucile al plasma");
        weapons.add("Fucile di precisione");
        weapons.add("Falce protonica");
        weapons.add("Raggio traente");
        weapons.add("Cannone vortex");
        weapons.add("Vulcanizzatore");
        weapons.add("Razzo termico");
        weapons.add("Raggio solare");
        weapons.add("Lanciafiamme");
        weapons.add("Lanciagranate");
        weapons.add("Lanciarazzi");
        weapons.add("Fucile laser");
        weapons.add("Spada fotonica");
        weapons.add("ZX-2");
        weapons.add("Fucile a pompa");
        weapons.add("Cyberguanto");
        weapons.add("Onda d'urto");
        weapons.add("Martello ionico");
    }

    private boolean isWeapon(String command){
        for(String w:weapons){
            if(w.equals(command)){
                return true;
            }
        }
        return false;
    }
    private boolean isValidColor(String color){
        return color.equals("blu") || color.equals("rosso")|| color.equals("giallo");
    }

    private boolean isNameValid(List<String> name){
        for (String str: name){
            if(!str.equals("sprog") && !str.equals("dozer") &&!str.equals("violetta") &&!str.equals("distruttore") && !str.equals("banshee")){
                return false;
            }
        }
        return true;
    }
}
