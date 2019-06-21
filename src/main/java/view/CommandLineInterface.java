package view;

import controller.CommandLauncherInterface;
import model.User;
import model.clientModel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
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
     * Method used to notifyMessage all users when the gameMap changes in one of the four maps
     *
     * @param mappa is the new Gamemap that got chosen as the map to be used during the next gameplay
     **/
    @Override
    public void onMapChange(SemplifiedMap mappa) {

        String user0="";
        String user1="";
        String user2="";
        String user3="";
        String user4="";
        String[] user ={user0,user1,user2,user3,user4};

        String p0="          ";
        String p1="          ";
        String p2="          ";
        String p3="          ";
        String p4="          ";
        String p5="          ";
        String p6="          ";
        String p7="          ";
        String p8="          ";
        String p9="          ";
        String p10="          ";
        String p11="          ";
        String[] p = {p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11};
        int k=0;
        int total=0;
        for(int i=0; i<3 ; i++){
            for(int j=0; j<4 ; j++){
                if(mappa.getTile(i,j).getAmmoCard()!=null){
                    p[mappa.getTile(i,j).getId()]=p[mappa.getTile(i,j).getId()].replaceFirst("    ","AMMO");
                }
                total=total+mappa.getTile(i,j).getPlayers().size();

                List<SemplifiedPlayer> players = mappa.getTile(i,j).getPlayers();
           if(!players.isEmpty()){

               int l=0;
                    for(SemplifiedPlayer player : players){
                        user[k]=playerColor(mappa.getTile(i,j).getPlayers().get(l))+"@"+AnsiColor.RESET+" e' il giocatore "+playerColor(mappa.getTile(i,j).getPlayers().get(l))+player.getName()+ AnsiColor.RESET+" e si trova nel tile: "+ player.getTile();
                        k++;
                        l++;
                        p[player.getTile()] = p[player.getTile()].substring(0, p[player.getTile()].lastIndexOf(" ")) +playerColor(player)+"@"+p[player.getTile()].substring(p[player.getTile()].lastIndexOf(" ")+1);

                       }

                }

            }

        }


        String hash1 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET + "║          ║\t"+user[0]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1   " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + "║    3     ║\t"+user[1]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND +p[0] + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[1]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2] + AnsiColor.RESET + "║"  + p[3] + "║\t"+user[2]+"\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬══════════╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "      6   " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[5] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[6] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬══════════╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║          ║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.YELLOW_BACKGROUND+" " + AnsiColor.RESET +"║\t"+"REG TILE: "+mappa.getTile(0,2).getId()+"  "+mappa.getTile(0,2).toString()+"\n" +
                "║    8     ║" + AnsiColor.GREEN_BACKGROUND + "    9     " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "     10   " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+"REG TILE: "+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║"+     p[8]     +"║" + AnsiColor.GREEN_BACKGROUND + p[9] + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + p[10] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[11] + AnsiColor.RESET + "║\t"+"REG TILE: "+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash2 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+ AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET +"║          ║\t"+user[0]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1   " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + "║    3     ║\t"+user[1]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[0] + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + p[1] + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2] + AnsiColor.RESET + "║"  + p[3] + "║\t"+user[2]+"\n" +
                "╠          ╬═══    ═══╬═══    ═══╬══════════╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "     5    "+AnsiColor.RESET+" "+ AnsiColor.GREEN_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[5] + AnsiColor.RESET + " " +AnsiColor.GREEN_BACKGROUND + p[6]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[7]+ AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬═══    ═══╬══════════╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + "          "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+ "REG TILE" + AnsiColor.YELLOW_BACKGROUND +" " +AnsiColor.RESET+ "║\t"+"REG TILE: "+mappa.getTile(0,2).getId()+"  "+mappa.getTile(0,2).toString()+"\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + "    8     "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"     9    "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"      10  " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+"REG TILE: "+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + p[8]+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+p[9]+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+p[10]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[11] + AnsiColor.RESET + "║\t"+"REG TILE: "+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash3 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "          "+AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET+ "REG TILE" +AnsiColor.BLUE_BACKGROUND+" "+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[0]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1   "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║\t"+user[1]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[0] + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + p[1]+AnsiColor.RESET+" "+ AnsiColor.BLUE_BACKGROUND+ p[2]+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[3] + AnsiColor.RESET + "║\t"+user[2]+"\n" +
                "╠          ╬═══    ═══╬═══    ═══╬═══    ═══╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+"REG TILE"+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " "  + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET +  " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4]+ AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + p[5]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[6] + AnsiColor.RESET + " " +  AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬═══    ═══╬          ╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║" + AnsiColor.WHITE_BACKGROUND + "          "+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+"          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + " " + AnsiColor.RESET+ "REG TILE" + AnsiColor.YELLOW_BACKGROUND +" " +AnsiColor.RESET+ "║\t"+"REG TILE: "+mappa.getTile(0,2).getId()+"  "+mappa.getTile(0,2).toString()+"\n" +
                "║" + AnsiColor.WHITE_BACKGROUND + "    8     "+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+"     9    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+"REG TILE: "+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║" + AnsiColor.WHITE_BACKGROUND +p[8]+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+p[9] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[10] +  AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[11]+ AnsiColor.RESET + "║║\t"+"REG TILE: "+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash4 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[0]+"\n"+
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║\t"+user[1]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND +p[0]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND +p[1]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2]+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[3] + AnsiColor.RESET + "║\t"+user[2]+"\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬═══    ═══╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[5] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[6] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬          ╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║          ║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.YELLOW_BACKGROUND+" " + AnsiColor.RESET +"║\t"+"REG TILE: "+mappa.getTile(0,2).getId()+"  "+mappa.getTile(0,2).toString()+"\n" +
                "║    8     ║" + AnsiColor.MAGENTA_BACKGROUND + "    9     " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET +"║\t"+"REG TILE: "+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║"+     p[8]     +"║" + AnsiColor.MAGENTA_BACKGROUND + p[9] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[10] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[11]+ AnsiColor.RESET +"║\t"+"REG TILE: "+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

displayText(hash3);
        displayText("La posizione dei giocatori sulla mappa è cambiata");
    }


    /**
     * Method used to notifyMessage all users with a string message
     *
     * @param message is a String tupe needed to notifyMessage all users printing out a string
     **/
    @Override
    public void notifyMessage(String message) {
        displayText(message);
    }

    /**
     * Method needed to notifyMessage all players whenever a player gets damage
     *
     * @param damagePlayer is the player who got damaged
     **/
    @Override
    public void onHpChange(SemplifiedPlayer damagePlayer) {
        String output = "";
        List<SemplifiedBloodToken> damageTokens = damagePlayer.getPlayerBoard().getDamageTokens() ;
        int a = damageTokens.size() ;
        for(int i =0 ; i<10-a; i++){
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
        displayText(output);
    }

    /**
     * Method needed to notifyMessage all players whenever a player gets one or more marks
     *
     * @param markedPlayer is the player who got one or more marks
     **/
    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {
        String output = "";
        List<SemplifiedBloodToken> marxTokens = markedPlayer.getPlayerBoard().getMarksTokens() ;
        int a = marxTokens.size() ;
        for(int i =0 ; i<10-a; i++){
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
        displayText(output);
//
    }

    /**
     * Method needed to notifyMessage all players whenever a player who uses or picks up ammo-s
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
     * Method needed to notifyMessage all players whenever changes number of Poweups in his hand
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
        consoleOutput.flush();
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
            default: throw new RuntimeException("The player name is not correct");
        }
        return playercolor;
    }
}
