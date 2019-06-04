package view;

import controller.CommandLauncher;
import controller.commandpack.*;
import model.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 *  The classic CLI class needed to run the software without GUI
 **/
public class CommandLineInterface extends AbstractView {
    private  CommandLauncher commandLauncher;
    private MessageListener originView;
    private List<MessageListener> allViews;
    private Player player;
    private User user;
    private String username;
    private String phrase;
    private int num;
    private User owner;
    private int players;
    private String token;
    /**
     *  Attribute needed to output text from the console
     **/
    public static   Writer consoleOutput = new OutputStreamWriter(System.out);

    /**
     * Attribute needed to grab the input entered by the user/player
     **/

 public static  Reader fromKeyBoard = new InputStreamReader(System.in);

    /* It starts a new scanner needed to grab user input from keyboard*/
   public static  Scanner scanner = new Scanner( System.in );

    /**
    * Method used to print out to the users waiting on lobby when a new user joins in the lobby
    * @param joinedUser refers to the user that just joined the lobby
    **/
    @Override
    public void onJoin(User joinedUser) {
        System.out.println(joinedUser.getUsername()+" si e' unito alla lobby");
    }

    /**
     * *Method used to print out to the users waiting on lobby if a joined user left the lobby
    * @param leavingUser refers to the user who just left the lobby
    **/
    @Override
    public void onLeave(User leavingUser) {
        System.out.println(leavingUser.getUsername()+" ha lasciato la lobby");
    }

    /**
     * Method used to notify all users when the gameMap changes in one of the four maps
    * @param match is the new Gamemap that got chosen as the map to be used during the next gameplay
    **/
    @Override
    public void onMapCHange(Match match) {

        for(Player player : match.getPlayers()) {
            if(player.getTile()==null){
                System.out.println("is null");
            }else{
                System.out.println("position is "+player.getTile().getID());
            }

        }
        System.out.println("La posizione dei giocatori sulla mapps si e cambiata");
    }

    /**
     * Method used to notify all users with a string message
    * @param message is a String tupe needed to notify all users printing out a string
     **/
    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    /**
     * Method needed to notify all players whenever a player gets damage
     * @param damagePlayer is the player who got damaged
     **/
    @Override
    public void onHpChange(Player damagePlayer) {
        System.out.println(damagePlayer.getName()+" ha subito danni e quindi e' rimasto con "+damagePlayer.getPlayerBoard().getNumDamagePoints()+" vite.");
    }

    /**
     * Method needed to notify all players whenever a player gets one or more marks
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(Player markedPlayer) {
        System.out.println("A"+ markedPlayer.getName()+" si e' cambiato il numero dei marchi in: "+markedPlayer.getPlayerBoard().getNumMarks()+" marks");
    }

    /**
     * Method needed to notify all players whenever a player who uses or picks up ammo-s
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(Player p) {
        System.out.println(p.getName()+" ha un numero diverso di ammo rispetto a prima:\n" +
                "Ammo blu : "+p.getPlayerBoard().getLoader().getNumBlueAmmo()+"\n" +
                "Ammo rosse : "+p.getPlayerBoard().getLoader().getNumRedAmmo()+"\n" +
                "Ammo gialle : "+p.getPlayerBoard().getLoader().getNumYellowAmmo()+"\n"
        );
    }
    /**
     * Method needed to notify all players whenever changes number of Poweups in his hand
     * @param p is the player who either used or dropped or picked up a PowerUp card
     **/
    @Override
    public void onPowerUpChange(Player p) {
        System.out.println(p.getName()+" ha cambiato il numero di carte PowerUp nella mano, quindi ora ha: "+p.getNumPowerUps());
    }

    /**
     * Method used to get a keyboard input from the users
     **/
    public String getUserInputString(){

 //       Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter some words");

        String cc = scanner.nextLine();
        System.out.println("Enter some words2"+cc);
        return cc;
    }

    /*
    Method used to parse the commands on CLI. It returns void and is based on a switch case.
    All cases should be case insensitive
     */

    public void parseCommand(String command){
        String param="";
        String realCommand="";

        if(command.contains(" ")) {
            realCommand = command.split(" ")[0];
            param = command.split(" ")[1];
        }else{
            realCommand=command;
        }
        switch(realCommand.toLowerCase()) {
            case "muovi": commandLauncher.addCommand(new AskWalkCommand(originView,allViews));
                return;
            case "raccogli": commandLauncher.addCommand(new AskPickCommand(originView,allViews));
                return;
            case "spara": commandLauncher.addCommand(new AskShootCommand(originView,allViews));
                return;
            case "ricarica": commandLauncher.addCommand(new AskReloadCommand(originView,allViews));
                return;
            case "fineturno": commandLauncher.addCommand(new AskEndTurnCommand(originView,allViews));
                return;
            case "powerup": commandLauncher.addCommand(new AskUsePowerUpCommand(originView,allViews));
                return;
            case "setusername": commandLauncher.addCommand(new SetUsernameCommand(originView,allViews,user, param));
                return;
            case "setfraseeffeto": commandLauncher.addCommand(new SetEffectPhraseCommand(originView,allViews,user,param));
                return;
            case "setuccisioni": commandLauncher.addCommand(new SetNumberOfDeathCommand(originView,allViews,Integer.valueOf(param),owner));
                return;
            case "setgiocatori": commandLauncher.addCommand(new SetPlayerNumberCommand(originView,allViews,Integer.valueOf(param),owner));
                return;
            case "punti": commandLauncher.addCommand(new AskPointsCommand(originView,allViews,player));
                return;
            case "setpersonaggio": commandLauncher.addCommand(new SetTokenCommand(originView,allViews,param));
                return;
         }

        if(command.contains("up")||command.contains("right")||command.contains("left")||command.contains("down")){
            List<String> toadd=new ArrayList<>();
            toadd.addAll(Arrays.asList(command.split(",")));
            commandLauncher.addCommand(new MoveCommand(originView,allViews,toadd));
        }
    }

    public int getUserInputInt(){


      //  Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
      //  scanner.close();
        return input;
    }
/**
 * Displays text in the CLI to the users/players
 **/
    public void displayText(String text){
        System.out.println(text);
    }
}
