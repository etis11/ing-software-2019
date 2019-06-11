package view;

import controller.CommandLauncherInterface;
import model.Match;
import model.Player;
import model.User;
import model.clientModel.SemplifiedMap;

import java.io.*;
import java.util.Scanner;

/**
 * The classic CLI class needed to run the software without GUI
 **/
public class CommandLineInterface extends AbstractView  {

    private CommandLauncherInterface commandLauncher;
    private Player player;
    private User user;
    private String username;
    private String phrase;
    private int num;
    private User owner;
    private int players;
    private String token;
    /**
     * Attribute needed to output text from the console
     **/
    private Writer consoleOutput;
    /**
     * Attribute needed to grab the input entered by the user/player
     **/
    private Scanner fromKeyBoard;


    /**
     * creates a cli that receives on the system.in and writes on system.out
     *
     * @throws IOException
     */
    public CommandLineInterface() throws IOException {
        consoleOutput = new PrintWriter(System.out);
        fromKeyBoard = new Scanner(System.in);

    }

    /**
     * creates a generic cli
     *
     * @param inputStream
     * @param outputStream
     */
    public CommandLineInterface(InputStream inputStream, PrintStream outputStream) {
        consoleOutput = new PrintWriter(outputStream);
        fromKeyBoard = new Scanner(inputStream);
    }

    /**
     * Method used to print out to the users waiting on lobby when a new user joins in the lobby
     *
     * @param joinedUser refers to the user that just joined the lobby
     **/
    @Override
    public void onJoin(User joinedUser) {
        System.out.println(joinedUser.getUsername() + " si e' unito alla lobby");
    }

    /**
     * *Method used to print out to the users waiting on lobby if a joined user left the lobby
     *
     * @param leavingUser refers to the user who just left the lobby
     **/
    @Override
    public void onLeave(User leavingUser) {
        System.out.println(leavingUser.getUsername() + " ha lasciato la lobby");
    }

    /**
     * Method used to notify all users when the gameMap changes in one of the four maps
     *
     * @param match is the new Gamemap that got chosen as the map to be used during the next gameplay
     **/
    @Override
    public void onMapChange(SemplifiedMap mappa) {

         String hash4 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      REG " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬═══    ═══╣\n" +
                "║" + AnsiColor.RED_BACKGROUND + "    REG   " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬          ╬          ╣\n" +
                "║          ║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    REG   " + AnsiColor.RESET + "║\n" +
                "║    8     ║" + AnsiColor.MAGENTA_BACKGROUND + "    9     " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\n" +
                "║          ║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";



        displayText(hash4);
        for (Player player : match.getPlayers()) {
            if (player.getTile() == null) {
                System.out.println("is null");
            } else {
                System.out.println("position is " + player.getTile().getID());
            }

        }
        System.out.println("La posizione dei giocatori sulla mapps si e cambiata");
    }

    /**
     * Method used to notify all users with a string message
     *
     * @param message is a String tupe needed to notify all users printing out a string
     **/
    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    /**
     * Method needed to notify all players whenever a player gets damage
     *
     * @param damagePlayer is the player who got damaged
     **/
    @Override
    public void onHpChange(Player damagePlayer) {
        System.out.println(damagePlayer.getName() + " ha subito danni e quindi e' rimasto con " + damagePlayer.getPlayerBoard().getNumDamagePoints() + " vite.");
    }

    /**
     * Method needed to notify all players whenever a player gets one or more marks
     *
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(Player markedPlayer) {
        System.out.println("A" + markedPlayer.getName() + " si e' cambiato il numero dei marchi in: " + markedPlayer.getPlayerBoard().getNumMarks() + " marks");
    }

    /**
     * Method needed to notify all players whenever a player who uses or picks up ammo-s
     *
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(Player p) {
        System.out.println(p.getName() + " ha un numero diverso di ammo rispetto a prima:\n" +
                "Ammo blu : " + p.getPlayerBoard().getLoader().getNumBlueAmmo() + "\n" +
                "Ammo rosse : " + p.getPlayerBoard().getLoader().getNumRedAmmo() + "\n" +
                "Ammo gialle : " + p.getPlayerBoard().getLoader().getNumYellowAmmo() + "\n"
        );
    }

    /**
     * Method needed to notify all players whenever changes number of Poweups in his hand
     *
     * @param p is the player who either used or dropped or picked up a PowerUp card
     **/
    @Override
    public void onPowerUpChange(Player p) {
        System.out.println(p.getName() + " ha cambiato il numero di carte PowerUp nella mano, quindi ora ha: " + p.getNumPowerUps());
    }

    /**
     * Method used to get a keyboard input from the users
     **/
    public String getUserInputString() throws IOException {
        //       Scanner scanner1 = new Scanner(System.in);
        consoleOutput.write(AnsiColor.GREEN + ">>> " + AnsiColor.RESET);
        consoleOutput.flush();
        String cc = fromKeyBoard.nextLine();
        return cc;
    }

    /*
    Method used to parse the commands on CLI. It returns void and is based on a switch case.
    All cases should be case insensitive
     */


    public int getUserInputInt() {


        //  Scanner scanner = new Scanner(System.in);
        int input = fromKeyBoard.nextInt();
        //  scanner.close();
        return input;
    }

    /**
     * Displays text in the CLI to the users/players
     **/
    public void displayText(String text) {
        System.out.println(text);
    }
}
