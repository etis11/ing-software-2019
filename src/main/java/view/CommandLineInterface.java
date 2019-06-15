package view;

import controller.CommandLauncherInterface;
import model.User;
import model.clientModel.*;

import java.io.*;
import java.util.List;
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
    private SemplifiedTile tile;
    private SemplifiedMap map;
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

        String fag0="          ";
        String fag1="          ";
        String fag2="          ";
        String fag3="          ";
        String fag4="          ";
        String fag5="          ";
        String fag6="          ";
        String fag7="          ";
        String fag8="          ";
        String fag9="          ";
        String fag10="          ";
        String fag11="          ";
        String[] fags = {fag0,fag1 ,fag2,fag3,fag4,fag5,fag6,fag7 ,fag8,fag9,fag10,fag11};
        String fag=" ";
        for(int i=0; i<3 ; i++){
            for(int j=0; j<4 ; j++){
                System.out.println(i+","+j);
                List<SemplifiedPlayer> players = mappa.getTile(i,j).getPlayers();
                if(!players.isEmpty()){
                    for(SemplifiedPlayer player : players){
                        fags[player.getTile()] = fags[player.getTile()].substring(0, fags[player.getTile()].lastIndexOf(" ")) +playerColor(player)+"T"+fags[player.getTile()].substring(fags[player.getTile()].lastIndexOf(" ")+1);
                    }
                }

            }
        }
        for(int i=0; i<12 ; i++){
            System.out.println(fags[i]);
        }

        String hash4 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      REG " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.BLUE_BACKGROUND +fags[0]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND +fags[1]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + fags[2]+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + fags[3] + AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬═══    ═══╣\n" +
                "║" + AnsiColor.RED_BACKGROUND + "    REG   " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║\n" +
                "║" + AnsiColor.RED_BACKGROUND + fags[4] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + fags[5] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + fags[6] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + fags[7] + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬          ╬          ╣\n" +
                "║          ║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    REG   " + AnsiColor.RESET + "║\n" +
                "║    8     ║" + AnsiColor.MAGENTA_BACKGROUND + "    9     " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\n" +
                "║"+     fags[8]     +"║" + AnsiColor.MAGENTA_BACKGROUND + fags[9] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + fags[10] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + fags[11]+ AnsiColor.RESET + "║\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";


System.out.println(hash4);
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
        String output = "";
        List<SemplifiedBloodToken> damageTokens = damagePlayer.getPlayerBoard().getDamageTokens() ;
        int a = damageTokens.size() ;
        for(int i =0 ; i<12-a; i++){
            damageTokens.add(new SemplifiedBloodToken());
        }

        for(SemplifiedBloodToken semplifiedBloodToken : damageTokens){
            if(semplifiedBloodToken!=null){
                SemplifiedPlayer semplifiedPlayer = semplifiedBloodToken.getOwner();
                if(semplifiedPlayer==null){
                    output=output+"\uD83D\uDDA4" ;
                }else{
                    String color = playerColor(semplifiedPlayer);
                    output=output+color+"\uD83D\uDDA4"+AnsiColor.RESET;
                }
            }
        }
        System.out.println(output);
        displayText(output);


        //     damagePlayer.getPlayerBoard().getDamageTokens().get(0).get

    //    damagePlayer.getPlayerBoard().getDamageTokens().stream().filter(t -> t.getColor()!=null ).findAny().get();
    //    displayText(damagePlayer.getName() + " ha subito danni e quindi e' rimasto con " + damagePlayer.getPlayerBoard().getNumDamagePoints() + " vite.\n");
    //    for(int i=0; i<damagePlayer.getPlayerBoard().getNumDamagePoints();i++){
    //        displayText(playerColor(damagePlayer)+"\uD83D\uDDA4"+AnsiColor.RESET);
    //    }
    }

    /**
     * Method needed to notify all players whenever a player gets one or more marks
     *
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {
        String output = "";
        List<SemplifiedBloodToken> marxTokens = markedPlayer.getPlayerBoard().getMarksTokens() ;
        int a = marxTokens.size() ;
        for(int i =0 ; i<12-a; i++){
            marxTokens.add(new SemplifiedBloodToken());
        }

        for(SemplifiedBloodToken semplifiedBloodToken : marxTokens){
            if(semplifiedBloodToken!=null){
                SemplifiedPlayer semplifiedPlayer = semplifiedBloodToken.getOwner();
                if(semplifiedPlayer==null){
                    output=output+"\uD83D\uDCA7" ;
                }else{
                    String color = playerColor(semplifiedPlayer);
                    output=output+color+"\uD83D\uDCA7"+AnsiColor.RESET;
                }
            }
        }
        System.out.println(output);
        displayText(output);
//        displayText("A" + markedPlayer.getName() + " si e' cambiato il numero dei marchi in: " + markedPlayer.getPlayerBoard().getNumMarks() + " marks\n");
 //       for(int i=0; i<markedPlayer.getPlayerBoard().getNumDamagePoints();i++){
 //           displayText(playerColor(markedPlayer)+"\uD83D\uDCA7"+AnsiColor.RESET);
 //       }
    }

    /**
     * Method needed to notify all players whenever a player who uses or picks up ammo-s
     *
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(SemplifiedPlayer p) {

        displayText(playerColor(p)+p.getName() +AnsiColor.RESET + " ha un numero diverso di ammo rispetto a prima:\n");

        displayText("Ammo "+ AnsiColor.BLUE+ "blu"+AnsiColor.RESET+": " + p.getPlayerBoard().getLoader().getNumBlueAmmo()+" ");
        for(int i=0;i<p.getPlayerBoard().getLoader().getNumBlueAmmo();i++){
            displayText(AnsiColor.BLUE+"⬜"+AnsiColor.RESET);
        }
        displayText("Ammo "+ AnsiColor.RED+ "blu"+AnsiColor.RESET+": " + p.getPlayerBoard().getLoader().getNumRedAmmo()+" ");
        for(int i=0;i<p.getPlayerBoard().getLoader().getNumRedAmmo();i++){
            displayText(AnsiColor.RED+"⬜"+AnsiColor.RESET);
        }
        displayText("Ammo "+ AnsiColor.YELLOW+ "blu"+AnsiColor.RESET+": " + p.getPlayerBoard().getLoader().getNumYellowAmmo()+" ");
        for(int i=0;i<p.getPlayerBoard().getLoader().getNumYellowAmmo();i++){
            displayText(AnsiColor.YELLOW+"⬜"+AnsiColor.RESET);
        }
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
                playercolor = AnsiColor.WHITE+"";
                break;
            case "Sprog":
                playercolor = AnsiColor.GREEN+"";
                break;
            case "Distruttore":
                playercolor = AnsiColor.YELLOW+"";
                break;
            case "Banshee":
                playercolor = AnsiColor.BLUE+"";
                break;
            case "Violetta":
                playercolor = AnsiColor.MAGENTA+"";
                break;
        }
        return playercolor;
    }
}
