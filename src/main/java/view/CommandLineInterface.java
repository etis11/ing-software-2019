package view;

import controller.CommandLauncherInterface;
import controller.commandpack.SetMapCommand;
import javafx.scene.paint.Color;
import model.PowerUpCard;
import model.PowerUpType;
import model.User;
import model.WeaponCard;
import model.clientModel.*;

import java.io.*;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The classic CLI class needed to run the software without GUI
 **/
public class CommandLineInterface extends AbstractView  {

    /**
     * Attribute needed to output text from the console
     **/
    private PrintWriter consoleOutput;

    /**
     * Attribute needed to grab the input entered by the user/player
     **/
    private Scanner fromKeyBoard;

    private String token;

    private String currentMap = "";


    /**
     * creates a cli that receives on the system.in and writes on system.out
     *
     * @throws IOException
     */
    public CommandLineInterface() throws IOException {
        consoleOutput = new PrintWriter(System.out);
        fromKeyBoard = new Scanner(System.in);

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * creates a generic cli
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
        displayText(AnsiColor.BLUE_BOLD_BRIGHT + joinedUser.getUsername() + " si e' unito alla lobby" +AnsiColor.RESET);
    }

    /**
     * *Method used to print out to the users waiting on lobby if a joined user left the lobby
     *
     * @param leavingUser refers to the user who just left the lobby
     **/
    @Override
    public void onLeave(User leavingUser) {
        displayText(AnsiColor.BLUE_BOLD_BRIGHT + leavingUser.getUsername() + " ha lasciato la lobby" + AnsiColor.RESET);
    }

    /**
     * Method used to notifyMessage all users when the gameMap changes in one of the four maps
     *
     * @param mappa is the new Gamemap that got chosen as the map to be used during the next gameplay
     **/
    @Override
    public void onMapChange(SemplifiedMap mappa) {
        /*
       Strings declared needed for the map legend. Those are needed to check players position on the map
         */
        String user0="";
        String user1="";
        String user2="";
        String user3="";
        String user4="";

        /*
        String array containing all players on map
         */
        String[] user ={user0,user1,user2,user3,user4};

        /*
        Strings needed to PLACE the players with symbol @ on the map so that we can keep track of them evertyime the map changes
         */
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

        /*
        String containing all players
         */
        String[] p = {p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11};
        int k=0;
        int total=0;
        for(int i=0; i<3 ; i++){
            for(int j=0; j<4 ; j++){
                SemplifiedTile tile = mappa.getTile(i,j);
                if(tile != null){
                    if(mappa.getTile(i,j).getAmmoCard()!=null){
                        String toaddAmmo="";

                        for(int z=0;z<mappa.getTile(i,j).getAmmoCard().getNumBlue();z++){
                            toaddAmmo=toaddAmmo+"B";
                        }
                        for(int z=0;z<mappa.getTile(i,j).getAmmoCard().getNumRed();z++){
                            toaddAmmo=toaddAmmo+"R";
                        }
                        for(int z=0;z<mappa.getTile(i,j).getAmmoCard().getNumYellow();z++){
                            toaddAmmo=toaddAmmo+"Y";
                        }
                        p[mappa.getTile(i,j).getId()]=p[mappa.getTile(i,j).getId()].replaceFirst("    "," "+toaddAmmo+ (mappa.getTile(i,j).getAmmoCard().isDrawPowerUp()? "P" : ""));
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
        }

/*
The following 4 strings are the 4 maps.
 */

        String hash1 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET + "║          ║\t"+user[0]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + "║    3     ║\t"+user[1]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND +p[0] + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[1]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2] + AnsiColor.RESET + "║"  + p[3] + "║\t"+user[2]+"\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬══════════╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "     7    " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[5] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[6] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬══════════╬          ╣\t\t\t\tLEGENDA DELLE ARMI\n" +
                "║          ║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.YELLOW_BACKGROUND+" " + AnsiColor.RESET +"║\t"+AnsiColor.BLUE+"REG TILE: "+AnsiColor.RESET+mappa.getTile(0,2).getId()+" "+mappa.getTile(0,2).toString()+"\n" +
                "║     8    ║" + AnsiColor.GREEN_BACKGROUND + "     9    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+AnsiColor.RED+"REG TILE: "+AnsiColor.RESET+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║"+     p[8]     +"║" + AnsiColor.GREEN_BACKGROUND + p[9] + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + p[10] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[11] + AnsiColor.RESET + "║\t"+AnsiColor.YELLOW+"REG TILE: "+AnsiColor.RESET+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash2 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+ AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET +"║          ║\t"+user[0]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + "║     3    ║\t"+user[1]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[0] + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + p[1] + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2] + AnsiColor.RESET + "║"  + p[3] + "║\t"+user[2]+"\n" +
                "╠          ╬═══    ═══╬═══    ═══╬══════════╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "     5    "+AnsiColor.RESET+" "+ AnsiColor.GREEN_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "     7    " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[5] + AnsiColor.RESET + " " +AnsiColor.GREEN_BACKGROUND + p[6]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[7]+ AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬═══    ═══╬══════════╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + "          "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+ "REG TILE" + AnsiColor.YELLOW_BACKGROUND +" " +AnsiColor.RESET+ "║\t"+AnsiColor.BLUE+"REG TILE: "+AnsiColor.RESET+mappa.getTile(0,2).getId()+" "+mappa.getTile(0,2).toString()+"\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + "     8    "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"     9    "+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+"    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+AnsiColor.RED+"REG TILE: "+AnsiColor.RESET+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║" + AnsiColor.MAGENTA_BACKGROUND + p[8]+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+p[9]+AnsiColor.RESET+" "+AnsiColor.MAGENTA_BACKGROUND+p[10]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[11] + AnsiColor.RESET + "║\t"+AnsiColor.YELLOW+"REG TILE: "+AnsiColor.RESET+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash3 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "          "+AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET+ "REG TILE" +AnsiColor.BLUE_BACKGROUND+" "+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[0]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "     3    " + AnsiColor.RESET + "║\t"+user[1]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[0] + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + p[1]+AnsiColor.RESET+" "+ AnsiColor.BLUE_BACKGROUND+ p[2]+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[3] + AnsiColor.RESET + "║\t"+user[2]+"\n" +
                "╠          ╬═══    ═══╬═══    ═══╬═══    ═══╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + " "+AnsiColor.RESET+"REG TILE"+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " "  + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET +  " " + AnsiColor.YELLOW_BACKGROUND + "     7    " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4]+ AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + p[5]+ AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[6] + AnsiColor.RESET + " " +  AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠═══    ═══╬═══    ═══╬          ╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║" + AnsiColor.WHITE_BACKGROUND + "          "+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+"          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + " " + AnsiColor.RESET+ "REG TILE" + AnsiColor.YELLOW_BACKGROUND +" " +AnsiColor.RESET+ "║\t"+AnsiColor.BLUE+"REG TILE: "+AnsiColor.RESET+mappa.getTile(0,2).getId()+" "+mappa.getTile(0,2).toString()+"\n" +
                "║" + AnsiColor.WHITE_BACKGROUND + "     8    "+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+"     9    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\t"+AnsiColor.RED+"REG TILE: "+AnsiColor.RESET+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║" + AnsiColor.WHITE_BACKGROUND +p[8]+AnsiColor.RESET+" "+AnsiColor.WHITE_BACKGROUND+p[9] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[10] +  AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[11]+ AnsiColor.RESET + "║║\t"+AnsiColor.YELLOW+"REG TILE: "+AnsiColor.RESET+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        String hash4 = AnsiColor.WHITE+"╔══════════╦══════════╦══════════╦══════════╗\t\t\t\tLEGGENDA DELLA MAPPA\n" +
                "║" +AnsiColor.RESET+ AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + " "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.BLUE_BACKGROUND+" " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[0]+"\n"+
                "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "     3    " + AnsiColor.RESET + "║\t"+user[1]+"\n" +
                "║" + AnsiColor.BLUE_BACKGROUND +p[0]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND +p[1]+ AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + p[2]+ AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + p[3] + AnsiColor.RESET + "║\t"+user[2]+"\n" +
                "╠═══    ═══╬══════════╬═══    ═══╬═══    ═══╣\t"+user[3]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.RED_BACKGROUND+" " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\t"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "     7    " + AnsiColor.RESET + "║"+user[4]+"\n" +
                "║" + AnsiColor.RED_BACKGROUND + p[4] + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + p[5] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[6] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[7] + AnsiColor.RESET + "║\n" +
                "╠══════════╬═══    ═══╬          ╬          ╣\t\t\t\tLEGGENDA DELLE ARMI\n" +
                "║          ║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND +" "+AnsiColor.RESET+AnsiColor.BLACK+"REG TILE"+AnsiColor.RESET+AnsiColor.YELLOW_BACKGROUND+" " + AnsiColor.RESET +"║\t"+AnsiColor.BLUE+"REG TILE: "+AnsiColor.RESET+mappa.getTile(0,2).getId()+" "+mappa.getTile(0,2).toString()+"\n" +
                "║     8    ║" + AnsiColor.MAGENTA_BACKGROUND + "     9    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET +"║\t"+AnsiColor.RED+"REG TILE: "+AnsiColor.RESET+mappa.getTile(1,0).getId()+" "+mappa.getTile(1,0).toString()+"\n" +
                "║"+     p[8]     +"║" + AnsiColor.MAGENTA_BACKGROUND + p[9] + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + p[10] + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + p[11]+ AnsiColor.RESET +"║\t"+AnsiColor.YELLOW+"REG TILE: "+AnsiColor.RESET+mappa.getTile(2,3).getId()+mappa.getTile(2,3).toString()+"\n" +
                "╚══════════╩══════════╩══════════╩══════════╝";

        switch (currentMap){
            case ("piccola"):
                displayText(hash1);
                break;
            case ("media"):
                displayText(hash4);
                break;
            case ("grande"):
                displayText(hash2);
                break;
            case("esagerata"):
                displayText(hash3);
                break;
        }
    }

    @Override
    public void onTypeMapChange(String mapName) {
        if (mapName.equals("piccola") || mapName.equals("media") || mapName.equals("grande") ||mapName.equals("esagerata") ){
            currentMap = mapName;
            displayText("La mappa di gioco è: " + mapName);
            return;
        }
        displayText("NOME MAPPA SBAGLIATO");

    }


    /**
     * Method used to notifyMessage all users with a string message
     *
     * @param message is a String tupe needed to notifyMessage all users printing out a string
     **/
    @Override
    public void notifyMessage(String message) {
        displayText(AnsiColor.BLUE_BOLD_BRIGHT + message + AnsiColor.RESET);
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
                    output=output+"♡" ;
                }else{
                    String color = playerColor(semplifiedPlayer);
                    output=output+color+"♡"+AnsiColor.RESET;
                }
            }
        }
        displayText(AnsiColor.BLUE_BOLD_BRIGHT +"HP del giocatore "+playerColor(damagePlayer)+" "+output + AnsiColor.RESET);
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
                    output=output+"᮰" ;
                }else{
                    String color = playerColor(semplifiedPlayer);
                    output=output+color+"᮰"+AnsiColor.RESET;
                }
            }
        }
        displayText("I marchi del giocatore "+playerColor(markedPlayer)+" "+output);
//
    }

    /**
     * Method needed to notifyMessage all players whenever a player who uses or picks up ammo-s
     *
     * @param p is the player who used or picked up ammo-s
     **/
    @Override
    public void onAmmoChange(SemplifiedPlayer p) {

        StringBuilder stringBuilder = new StringBuilder();
        //blue
        stringBuilder.append(AnsiColor.WHITE + "" +"Ammo " + AnsiColor.BLUE + "blu" + AnsiColor.RESET + ": ")
                .append(p.getPlayerBoard().getNumBlueAmmo())
                .append(" ");

        for(int i=0;i<p.getPlayerBoard().getNumBlueAmmo();i++){
            stringBuilder.append(AnsiColor.BLUE + "⬜" + AnsiColor.RESET);
        }
        stringBuilder.append("\n");
        //red
        stringBuilder.append("Ammo " + AnsiColor.RED + "rosse" + AnsiColor.RESET + ": ")
                .append(p.getPlayerBoard().getNumRedAmmo())
                .append(" ");
        for(int i=0;i<p.getPlayerBoard().getNumRedAmmo();i++){
            stringBuilder.append(AnsiColor.RED + "⬜" + AnsiColor.RESET);
        }
        stringBuilder.append("\n");
        //yellow
        stringBuilder.append("Ammo " + AnsiColor.YELLOW + "gialle" + AnsiColor.RESET + ": ")
                .append(p.getPlayerBoard().getNumYellowAmmo())
                .append(" ");
        for(int i=0;i<p.getPlayerBoard().getNumYellowAmmo();i++){
            //if present the previuos line should have a \n.
//            if(i == 0)
//                stringBuilder.append("\n");
            stringBuilder.append(AnsiColor.YELLOW + "⬜" + AnsiColor.RESET);
            //i dont put the \n after the last ammo since display text will
        }
        displayText(stringBuilder.toString());
    }

    /**
     * Method needed to notifyMessage all players whenever changes number of Poweups in his hand
     *
     * @param p is the player who either used or dropped or picked up a PowerUp card
     **/
    @Override
    public void onPowerUpChange(SemplifiedPlayer p) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POWER UPS: ")
                .append(p.getNumPowerUps());
        List<PowerUpCard> powerUpCards = p.getPowerUpCards();
        for(PowerUpCard card: powerUpCards){
            AnsiColor color = getAnsiColorPowerUp(card);
            String powerUpName = getPowerUpNameByType(card);
            stringBuilder.append("\n");
            stringBuilder.append(color)
                    .append(powerUpName)
                    .append(AnsiColor.RESET);
        }
        //displayText(p.getName() + " ha cambiato il numero di carte PowerUp nella mano, quindi ora ha: " + p.getNumPowerUps());
        displayText(stringBuilder.toString());
    }

    @Override
    public void onWeaponChange(SemplifiedPlayer p) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ARMI: ")
                .append(p.getNumWeapons())
                .append("\n");
        stringBuilder.append("ARMI CARICHE: ")
                .append(p.getNumLoadedWeapons())
                .append("\n");
        for(SemplifiedWeaponCard w: p.getWeaponCards()){
            if (w.isLoaded()){
                stringBuilder.append(getWeaponColor(w))
                        .append(w.getName())
                        .append(AnsiColor.RESET)
                        .append("\n");
            }
        }
        stringBuilder.append("ARMI SCARICHE: ")
                .append(p.getNumEmptyWeapons());
        for(SemplifiedWeaponCard w: p.getWeaponCards()){
            if (!w.isLoaded()){
                stringBuilder.append("\n");
                stringBuilder.append(getWeaponColor(w))
                        .append(w.getName())
                        .append(AnsiColor.RESET);
            }
        }
        displayText(stringBuilder.toString());
    }

    @Override
    public void onCurrentPlayerChange(SemplifiedPlayer p) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("E' iniziato il turno di: ")
                .append(playerColor(p))
                .append(p.getName())
                .append(AnsiColor.RESET);
        displayText(stringBuilder.toString());
    }

    @Override
    public void onSkullChange(List<List<SemplifiedBloodToken>> deathTrack) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tracciato mortale aggiornato:\n");
        for(List<SemplifiedBloodToken> lsb: deathTrack){
            for (SemplifiedBloodToken b: lsb){
                SemplifiedPlayer owner = b.getOwner();
                String color = playerColor(owner);
                stringBuilder.append(color)
                        .append("\uD83D\uDDA4")
                        .append(AnsiColor.RESET);
            }
            stringBuilder.append(" ");
        }
        displayText(stringBuilder.toString());
    }

    /**
     * Method used to get a keyboard input from the users
     **/
    public String getUserInputString() throws IOException {
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
        consoleOutput.println(AnsiColor.BLUE_BOLD_BRIGHT + text + AnsiColor.RESET);
        consoleOutput.flush();
    }

    public void displayTextSameLine(String text){
        consoleOutput.print(AnsiColor.BLUE_BOLD_BRIGHT + text + AnsiColor.RESET);
        consoleOutput.flush();
    }

    public void waitingInput(){
        consoleOutput.print(AnsiColor.GREEN + ">>> " + AnsiColor.RESET);
        consoleOutput.flush();
    }

    @Override
    public void onPlayerChange(SemplifiedPlayer p) {
        displayText("PLAYERBOARD DI "+AnsiColor.RESET + playerColor(p) + p.getName().toUpperCase());
        onHpChange(p);
        onMarksChange(p);
        onAmmoChange(p);
        onPowerUpChange(p);
        onWeaponChange(p);

    }

    public String playerColor(SemplifiedPlayer p) {
        String playercolor = "";
        p.getName();
        switch(p.getName()) {
            case "dozer":
                playercolor = AnsiColor.WHITE+"";
                break;
            case "sprog":
                playercolor = AnsiColor.GREEN+"";
                break;
            case "distruttore":
                playercolor = AnsiColor.YELLOW+"";
                break;
            case "banshee":
                playercolor = AnsiColor.BLUE+"";
                break;
            case "violetta":
                playercolor = AnsiColor.MAGENTA+"";
                break;
            default: throw new RuntimeException("The player name is not correct");
        }
        return playercolor;
    }

    public void displayMap(SemplifiedMap map){

    }

    /**
     * Returns the ansi color of the power up
     * @param powerUpCard
     * @return the relative ansi color
     */
    private AnsiColor getAnsiColorPowerUp(PowerUpCard powerUpCard){
        Color color = powerUpCard.getColor();
        if (color.equals(Color.RED))
            return AnsiColor.RED;
        if(color.equals(Color.BLUE))
            return AnsiColor.BLUE;
        if(color.equals(Color.YELLOW))
            return AnsiColor.YELLOW;
        throw new RuntimeException("Color not valid");
    }

    private String getPowerUpNameByType(PowerUpCard powerUpCard){
        PowerUpType powerUpType = powerUpCard.getPowerUpType();
        if (powerUpType.equals(PowerUpType.NEWTON))
            return "RaggioCinetico";
        if (powerUpType.equals(PowerUpType.TAGBACK_GRANADE))
            return "Granata";
        if (powerUpType.equals(PowerUpType.TARGETING_SCOPE))
            return "Mirino";
        if (powerUpType.equals(PowerUpType.TELEPORTER))
            return "Teletrasporto";
        throw new RuntimeException("There is not such a type of power up");
    }

    private AnsiColor getWeaponColor(SemplifiedWeaponCard weaponCard){
        String firstCost = weaponCard.getCost().get(0);
        firstCost = firstCost.toLowerCase();
        switch (firstCost){
            case "red":
                return AnsiColor.RED;
            case "blue":
                return AnsiColor.BLUE;
            case "yellow":
                return AnsiColor.YELLOW;
            default:
                throw new RuntimeException("Unkown color for a weapon card");
        }
    }

}
