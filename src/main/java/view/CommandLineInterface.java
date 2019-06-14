package view;

import controller.CommandLauncherInterface;
import model.User;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedPlayerBoard;

import java.io.*;
import java.util.Scanner;

/**
 * The classic CLI class needed to run the software without GUI
 **/
public class CommandLineInterface extends AbstractView  {

    private CommandLauncherInterface commandLauncher;
    private User user;
    private String username;
    private String phrase;
    private int num;
    private User owner;
    private int players;
    private String token;
    private SemplifiedPlayer player;
    private SemplifiedPlayerBoard playerBoard;
    /**
     * Attribute needed to output text from the console
     **/
    private PrintWriter consoleOutput;
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
        displayText(joinedUser.getUsername() + " si e' unito alla lobby");
    }

    /**
     * *Method used to print out to the users waiting on lobby if a joined user left the lobby
     *
     * @param leavingUser refers to the user who just left the lobby
     **/
    @Override
    public void onLeave(User leavingUser) {
        displayText(leavingUser.getUsername() + " ha lasciato la lobby");
    }

    /**
     * Method used to notify all users when the gameMap changes in one of the four maps
     *
     * @param mappa is the new Gamemap that got chosen as the map to be used during the next gameplay
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
        //TODO da verificare
//        for (Player player : match.getPlayers()) {
//            if (player.getTile() == null) {
//                System.out.println("is null");
//            } else {
//                System.out.println("position is " + player.getTile().getID());
//            }
//
//        }
        displayText("La posizione dei giocatori sulla mappa è cambiata");
    }

    /**
     * Method used to notify all users with a string message
     *
     * @param message is a String tupe needed to notify all users printing out a string
     **/
    @Override
    public void notify(String message) {
        displayText(message);
    }

    /**
     * Method needed to notify all players whenever a player gets damage
     *
     * @param damagePlayer is the player who got damaged
     **/
    @Override
    public void onHpChange(SemplifiedPlayer damagePlayer) {
        displayText(damagePlayer.getName() + " ha subito danni e quindi e' rimasto con " + damagePlayer.getPlayerBoard().getNumDamagePoints() + " vite.");
    }

    /**
     * Method needed to notify all players whenever a player gets one or more marks
     *
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {
        displayText("A" + markedPlayer.getName() + " si e' cambiato il numero dei marchi in: " + markedPlayer.getPlayerBoard().getNumMarks() + " marks");
    }

    /**
     * Method needed to notify all players whenever a player who uses or picks up ammo-s
     *
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(SemplifiedPlayer p) {
        displayText(p.getName() + " ha un numero diverso di ammo rispetto a prima:\n" +
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
    public void onPowerUpChange(SemplifiedPlayer p) {
        displayText(p.getName() + " ha cambiato il numero di carte PowerUp nella mano, quindi ora ha: " + p.getNumPowerUps());
    }

    @Override
    public void onWeaponChange(SemplifiedPlayer p) {
        displayText(p.getName()+ "ha cambiato le armi e contiene " + p.getNumWeapons() + " carte armi, che sono: "+ p.getWeaponCards());
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
        consoleOutput.println(text);
    }

    @Override
    public void onPlayerChange(SemplifiedPlayer p) {
        displayText(AnsiColor.RED + "DA IMPLEMENTARE" + AnsiColor.RESET);
    }

    public String playerColor(SemplifiedPlayer p) {
        String playercolor = "";
        p.getName();
        switch(p.getName()) {
            case "Dozer":
                playercolor = AnsiColor.WHITE+"⬚"+AnsiColor.RESET;
                break;
            case "Sproger":
                playercolor = AnsiColor.GREEN+"⬚"+AnsiColor.RESET;
                break;
            case "Distruttore":
                playercolor = AnsiColor.YELLOW+"⬚"+AnsiColor.RESET;
                break;
            case "Banshee":
                playercolor = AnsiColor.BLUE+"⬚"+AnsiColor.RESET;
                break;
            case "Violetta":
                playercolor = AnsiColor.MAGENTA+"⬚"+AnsiColor.RESET;
                break;
        }
        return playercolor;
    }
}
