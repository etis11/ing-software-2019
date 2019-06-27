package view;

import controller.CommandContainer;
import controller.commandpack.*;
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

    /*
    String needed to return all possible commands that you can call on CLI
     */
    String commandi=AnsiColor.GREEN+"quit"+AnsiColor.RESET+" - Uscire dal gioco\n" +
            AnsiColor.GREEN+"muovi *up/right/down/left*"+AnsiColor.RESET+" - Fa muovere il giocatore aggiungendo anche la/le direzione/i. Es: muovi up,left\n" +
            AnsiColor.GREEN+"spara"+AnsiColor.RESET+" - Commando che autorizza il giocatore a sparare\n" +
            AnsiColor.GREEN+"raccogli"+AnsiColor.RESET+" - Raccoglie nel tile in cui si trova le ammo o la WeaponCard\n" +
            AnsiColor.GREEN+"ricarica"+AnsiColor.RESET+" - Commando che carica l'arma\n" +
            AnsiColor.GREEN+"fineturno"+AnsiColor.RESET+" - Serve per terminare il tuo turno\n" +
            AnsiColor.GREEN+"powerup"+AnsiColor.RESET+" - Commando che server per chiedere conferma per poter usare la PowerUp\n" +
            AnsiColor.GREEN+"setusername *NOME*"+AnsiColor.RESET+" - Commando che server per defnire il nome del tuo player. Es: setusername Paolo\n" +
            AnsiColor.GREEN+"setfraseeffect *FRASE*"+AnsiColor.RESET+" - Commando che server per dare una frase al tuo player.Es: setphraseeffect This is Sparta!\n" +
            AnsiColor.GREEN+"setuccisioni *NUMERO*"+AnsiColor.RESET+" - Commando che decide i numeri di teschi a inizio partita. Es: setuccisioni 4\n" +
            AnsiColor.GREEN+"setgiocatori *NUMERO*"+AnsiColor.RESET+" - Comando che serve per decidere i numeri di giocatori nella partita. Es: setgiocatori 5\n" +
            AnsiColor.GREEN+"punti"+AnsiColor.RESET+" - Commando che ritorna la quantita dei punti del giocatore che fa la richiesta\n" +
            AnsiColor.GREEN+"setpersonaggio *NOME*"+AnsiColor.RESET+" - Commando che connette il giocatore con il carattere del gioco. Es: setpersonaggio VIOLETTA\n"+
            AnsiColor.GREEN+"setfrenesia *si/no*"+AnsiColor.RESET+" - Commando che imposta la frenesia finale per la partita. Es: setfrenesia no\n";

    public Parserator(CommandLineInterface cli, CommandContainer launcher) {
        CLI = cli;
        commandLauncher = launcher;
        quit = false;
        token = ClientSingleton.getInstance().getToken();
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
        } else {
            realCommand = command;
        }
        try {
            switch (realCommand.toLowerCase()) {
                case "help":
                    CLI.displayText(command);
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

            if (command.contains("up") || command.contains("right") || command.contains("left") || command.contains("down")|| command.contains("none")) {
                List<String> toadd = new ArrayList<>();
                if(!command.contains("none")) {
                    toadd.addAll(Arrays.asList(command.split(",")));
                }
                commandLauncher.addCommand(new MoveCommand(token, toadd));
                return;
            }

            String power = command.toLowerCase();
            if (power.contains("granata")||power.contains("teletrasporto")||power.contains("mirino")||power.contains("raggiocinetico")){
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



            throw new IllegalArgumentException();
        } catch (RemoteException r) {
            CLI.displayText(AnsiColor.RED + "Server rmi non raggiungibile" + AnsiColor.RESET);
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
}
