package view;

import model.GameMap;
import model.Player;
import model.User;

import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class CommandLineInterface extends AbstractView {

    Writer consoleOutput ;
    Reader fromKeyBoard ;

    public static  Scanner scanner = new Scanner( System.in );

    @Override
    public void onJoin(User joinedUser) {
        System.out.println(joinedUser.getUsername()+" joined the lobby");
    }

    @Override
    public void onLeave(User leavingUser) {
        System.out.println(leavingUser.getUsername()+" left the lobby");
    }

    @Override
    public void onMapCHange(GameMap m) {
        System.out.println("Map changed!");
    }

    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    @Override
    public void onHpChange(Player damagePlayer) {
        System.out.println(damagePlayer.getName()+" is damaged, so has left "+damagePlayer.getPlayerBoard().getNumDamagePoints()+" blood tokens.");
    }

    @Override
    public void onMarksChange(Player markedPlayer) {
        System.out.println(markedPlayer.getName()+" changed marks so now has "+markedPlayer.getPlayerBoard().getNumMarks()+" marks");
    }

    @Override
    public void onAmmoChange(Player p) {
        System.out.println(p.getName()+" changed ammos, the current ammos : \n" +
                "Blue cards : "+p.getPlayerBoard().getLoader().getNumBlueAmmo()+"\n" +
                "Red cards : "+p.getPlayerBoard().getLoader().getNumRedAmmo()+"\n" +
                "Yellow cards : "+p.getPlayerBoard().getLoader().getNumYellowAmmo()+"\n"
        );
    }

    @Override
    public void onPowerUpChange(Player p) {
        System.out.println(p.getName()+" changed number of Power up cards on his hand, so now has "+p.getNumPowerUps());
    }

    public String getUserInputString(){

 //       Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter some words Nigga");

        String cc = scanner.nextLine();
        System.out.println("Enter some words Nigga"+cc);
        return cc;
    }

    public int getUserInputInt(){
      //  Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
      //  scanner.close();
        return input;
    }

    public void displayText(String text){
        System.out.println(text);
    }
}
