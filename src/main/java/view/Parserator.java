package view;

import controller.CommandContainer;
import controller.CommandLauncherInterface;
import controller.commandpack.*;
import model.Player;
import model.User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parserator implements Runnable {
    private CommandContainer commandLauncher;
    private String token = ClientSingleton.getInstance().getToken();
    private CommandLineInterface CLI;
    private boolean quit;

    public Parserator(CommandLineInterface cli,  CommandContainer launcher){
        CLI = cli;
        commandLauncher = launcher;
        quit = false;
    }

    @Override
    public void run() {
        while (!quit) {
            try {
                this.parseCommand(CLI.getUserInputString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalArgumentException i){
                CLI.displayText(AnsiColor.RED + "Nessun comando esistente con questo formato" + AnsiColor.RESET);
            }
        }
    }


    public void parseCommand(String command) throws RemoteException {
        String param="";
        String realCommand="";
        if(command.contains(" ")) {
            String[] splittedCommand = command.split(" ");
            if (splittedCommand.length >0){
                realCommand = splittedCommand[0];
            }
            if(splittedCommand.length>1){
                param = splittedCommand[1];
            }
        }else{
            realCommand=command;
        }
        switch(realCommand.toLowerCase()) {
            case "quit": quit = true;
                return;
            case "muovi": commandLauncher.addCommand(new AskWalkCommand(token));
                return;
            case "raccogli": commandLauncher.addCommand(new AskPickCommand(token));
                return;
            case "spara": commandLauncher.addCommand(new AskShootCommand(token));
                return;
            case "ricarica": commandLauncher.addCommand(new AskReloadCommand(token));
                return;
            case "fineturno": commandLauncher.addCommand(new AskEndTurnCommand(token));
                return;
            case "powerup": commandLauncher.addCommand(new AskUsePowerUpCommand(token));
                return;
            case "setusername": commandLauncher.addCommand(new SetUsernameCommand(token,param));
                return;
            case "setfraseeffeto": commandLauncher.addCommand(new SetEffectPhraseCommand(token,param));
                return;
            case "setuccisioni": commandLauncher.addCommand(new SetNumberOfDeathCommand(token,Integer.valueOf(param)));
                return;
            case "setgiocatori": commandLauncher.addCommand(new SetPlayerNumberCommand(token,Integer.valueOf(param)));
                return;
            case "punti": commandLauncher.addCommand(new AskPointsCommand(token));
                return;
            case "setpersonaggio": commandLauncher.addCommand(new SetTokenCommand(token,param));
                return;
        }

        if(command.contains("up")||command.contains("right")||command.contains("left")||command.contains("down")){
            List<String> toadd=new ArrayList<>();
            toadd.addAll(Arrays.asList(command.split(",")));
            commandLauncher.addCommand(new MoveCommand(token,toadd));
            return;
        }
        throw new IllegalArgumentException();
    }
}
