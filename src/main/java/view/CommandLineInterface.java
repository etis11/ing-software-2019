package view;

import model.BloodToken;
import model.GameMap;
import model.Player;
import model.User;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

/**
 *  The classic CLI class needed to run the software without GUI
 **/
public class CommandLineInterface extends AbstractView {

    /**
     *  Attribute needed to output text from the console
     **/
    Writer consoleOutput ;

    /**
     * Attribute needed to grab the input entered by the user/player
     **/

    Reader fromKeyBoard ;

    /* It starts a new scanner needed to grab user input from keyboard*/
    public static  Scanner scanner = new Scanner( System.in );

    /**
    * Method used to print out to the users waiting on lobby when a new user joins in the lobby
    * @param joinedUser refers to the user that just joined the lobby
    **/
    @Override
    public void onJoin(User joinedUser) {
        System.out.println(joinedUser.getUsername()+" joined the lobby");
    }

    /**
     * *Method used to print out to the users waiting on lobby if a joined user left the lobby
    * @param leavingUser refers to the user who just left the lobby
    **/
    @Override
    public void onLeave(User leavingUser) {
        System.out.println(leavingUser.getUsername()+" left the lobby");
    }

    /**
     * Method used to notify all users when the gameMap changes in one of the four maps
    * @param m is the new Gamemap that got chosen as the map to be used during the next gameplay
    **/
    @Override
    public void onMapCHange(GameMap m) {
        System.out.println("Map changed!");
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
        System.out.println(damagePlayer.getName()+" is damaged, so has left "+damagePlayer.getPlayerBoard().getNumDamagePoints()+" blood tokens.");
    }


    //public void onHpChange(Player damagePlayer, Player shooter) {
      //  System.out.println(damagePlayer.getName()+" is damaged, so has left "+damagePlayer.getPlayerBoard().getNumDamagePoints()+" blood tokens.");
        //System.out.println("He was hit by"+ shooter.getPlayerBoard().dama + damagePlayer.getPlayerBoard() .calculateDamage(d);)

    //}

    /**
     * Method needed to notify all players whenever a player gets one or more marks
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(Player markedPlayer) {
        System.out.println(markedPlayer.getName()+" changed marks so now has "+markedPlayer.getPlayerBoard().getNumMarks()+" marks");
    }

    /**
     * Method needed to notify all players whenever a player who uses or picks up ammo-s
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(Player p) {
        System.out.println(p.getName()+" changed ammos, the current ammos : \n" +
                "Blue cards : "+p.getPlayerBoard().getLoader().getNumBlueAmmo()+"\n" +
                "Red cards : "+p.getPlayerBoard().getLoader().getNumRedAmmo()+"\n" +
                "Yellow cards : "+p.getPlayerBoard().getLoader().getNumYellowAmmo()+"\n"
        );
    }
    /**
     * Method needed to notify all players whenever changes number of Poweups in his hand
     * @param p is the player who either used or dropped or picked up a PowerUp card
     **/
    @Override
    public void onPowerUpChange(Player p) {
        System.out.println(p.getName()+" changed number of Power up cards on his hand, so now has "+p.getNumPowerUps());
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
