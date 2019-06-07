package view;

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
    private CommandLauncherInterface commandLauncher;
    private Player player;
    private User user;
    private String username;
    private String phrase;
    private int num;
    private User owner;
    private int players;
    private String token;


    @Override
    public void run() {

        try {
            this.parseCommand(new CommandLineInterface().getUserInputString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parseCommand(String command) throws RemoteException {
        String param="";
        String realCommand="";
        this.token = ClientSingleton.getInstance().getToken();
        if(command.contains(" ")) {
            realCommand = command.split(" ")[0];
            param = command.split(" ")[1];
        }else{
            realCommand=command;
        }
        switch(realCommand.toLowerCase()) {
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
            case "setusername": commandLauncher.addCommand(new SetUsernameCommand(token,username));
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
