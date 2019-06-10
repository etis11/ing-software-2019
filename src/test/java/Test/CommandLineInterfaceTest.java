package Test;

import model.Player;
import model.User;
import view.CommandLineInterface;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterfaceTest {


    public static void main(String[] args) throws IOException {
        //@Test
        //  void cliTest () {
        CommandLineInterface commandLineInterface = new CommandLineInterface();

        System.out.println("write something!!!");
        Scanner scanner = new Scanner(System.in);
        String c = scanner.nextLine();
        // scanner.close();
        System.out.println("You wrote something again!!! " + c);

        Scanner ss = new Scanner(System.in);
        System.out.println("write something bb!!! ");
        String bb = scanner.nextLine();
        System.out.println("You wrote something bb!!! " + bb);
        System.out.println(bb);
        String cc = commandLineInterface.getUserInputString();
        commandLineInterface.displayText(cc);
        System.out.println("write some number man");
        Integer a = commandLineInterface.getUserInputInt();
        String aString = a.toString();
        commandLineInterface.displayText(aString);
        User user = new User();
        user.setUsername("etis@mail.it");
        commandLineInterface.onJoin(user);
        System.out.println("Enter some Number");
        String numberString = scanner.nextLine();
        numberString = numberString.trim();
        int numero = Integer.valueOf(numberString);
        System.out.println("numero : " + numero);
        int n = numero + 3;
        System.out.println("numero = 3 : " + n);

        Player player = new Player("Etis");
        commandLineInterface.onAmmoChange(player);
        player.getPlayerBoard().getLoader().askReload(2, 0, 1);
        commandLineInterface.onAmmoChange(player);
        commandLineInterface.onHpChange(player);
        commandLineInterface.onMarksChange(player);
        commandLineInterface.onPowerUpChange(player);

        //}
    }
}
