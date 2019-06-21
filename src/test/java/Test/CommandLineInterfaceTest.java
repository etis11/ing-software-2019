package Test;

import model.*;
import model.clientModel.*;
import view.CommandLineInterface;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterfaceTest {


    public static <list> void main(String[] args) throws IOException {
        //@Test
        //  void cliTest () {
        CommandLineInterface commandLineInterface = new CommandLineInterface();

       // System.out.println("write something!!!");
 //       Scanner scanner = new Scanner(System.in);
 //       String c = scanner.nextLine();
        // scanner.close();
 //       System.out.println("You wrote something again!!! " + c);

   //     Scanner ss = new Scanner(System.in);
   //     System.out.println("write something bb!!! ");
     //   String bb = scanner.nextLine();
     //   System.out.println("You wrote something bb!!! " + bb);
     //   System.out.println(bb);
     //   String cc = commandLineInterface.getUserInputString();
      //  commandLineInterface.displayText(cc);
     //   System.out.println("write some number man");
     //   Integer a = commandLineInterface.getUserInputInt();
    //    String aString = a.toString();
     //   commandLineInterface.displayText(aString);
        User user = new User();
        user.setUsername("etis@mail.it");
     //   commandLineInterface.onJoin(user);
   //     System.out.println("Enter some Number");
 //       String numberString = scanner.nextLine();
  //      numberString = numberString.trim();
 //       int numero = Integer.valueOf(numberString);
//        System.out.println("numero : " + numero);
//        int n = numero + 3;
//        System.out.println("numero = 3 : " + n);
        SemplifiedWeaponCard weapon1 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon2 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon3 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon4 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon5 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon6 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon7 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon8 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon9 = new SemplifiedWeaponCard();
        SemplifiedWeaponCard weapon10 = new SemplifiedWeaponCard();


        weapon1.setName("Torpedine");
        weapon2.setName("Fucile laser");
        weapon3.setName("Distruttore");
        weapon4.setName("Cannone Vortex");
        weapon5.setName("Falce Protonica");
        weapon6.setName("Raggio traente");
        weapon7.setName("Razzo termico");
        weapon8.setName("Vulcanizzatore");
        weapon9.setName("Lanciarazzi");
        weapon10.setName("Test");


                SemplifiedPlayer player = new SemplifiedPlayer();
        player.setName("dozer");
        SemplifiedPlayer player2 = new SemplifiedPlayer();
        player2.setName("sprog");
        SemplifiedPlayer player3 = new SemplifiedPlayer();
        player3.setName("banshee");
        SemplifiedPlayer player4 = new SemplifiedPlayer();
        player4.setName("violetta");
      //  commandLineInterface.onAmmoChange(player);
//        player.getPlayerBoard().getLoader().askReload(2, 0, 1);
      //  commandLineInterface.onAmmoChange(player);
     //   commandLineInterface.onHpChange(player);
     //   commandLineInterface.onMarksChange(player);
      //  commandLineInterface.onPowerUpChange(player);
        SemplifiedMap semplifiedMap = new SemplifiedMap();
        SemplifiedTile semplifiedTile = new SemplifiedTile(0,false,false);
        SemplifiedTile semplifiedTile1= new SemplifiedTile(1,false,false);

        SemplifiedTile semplifiedTile2 = new SemplifiedTile(2,false,false);

        SemplifiedTile semplifiedTile3 = new SemplifiedTile(3,false,false);

        SemplifiedTile semplifiedTile4 = new SemplifiedTile(4,false,false);
        SemplifiedTile semplifiedTile5= new SemplifiedTile(5,false,false);

        SemplifiedTile semplifiedTile6 = new SemplifiedTile(6,false,false);

        SemplifiedTile semplifiedTile7 = new SemplifiedTile(7,false,false);

        SemplifiedTile semplifiedTile8 = new SemplifiedTile(8,false,false);
        SemplifiedTile semplifiedTile9= new SemplifiedTile(9,false,false);

        SemplifiedTile semplifiedTile10 = new SemplifiedTile(10,false,false);

        SemplifiedTile semplifiedTile11 = new SemplifiedTile(11,false,false);

        List<SemplifiedPlayer> semplifiedPlayers = new LinkedList<>();
        List<SemplifiedPlayer> semplifiedPlayers3 = new LinkedList<>();

        List<SemplifiedPlayer> semplifiedPlayers2 = new LinkedList<>();
        List<SemplifiedWeaponCard> weaponsTile1 = new LinkedList<>();
        List<SemplifiedWeaponCard> weaponsTile2 = new LinkedList<>();
        List<SemplifiedWeaponCard> weaponsTile3 = new LinkedList<>();
SemplifiedWeaponCard[] weaponsPlayer = new SemplifiedWeaponCard[1];

        weaponsTile1.add(weapon1);
        weaponsTile1.add(weapon2);
        weaponsTile1.add(weapon3);
        weaponsTile2.add(weapon4);
        weaponsTile2.add(weapon5);
        weaponsTile2.add(weapon6);
        weaponsTile3.add(weapon7);
        weaponsTile3.add(weapon8);
        weaponsTile3.add(weapon9);
        weaponsPlayer[0]=weapon10;
        player.setWeaponCards(weaponsPlayer);

        semplifiedTile2.setWeaponCards(weaponsTile1);
        semplifiedTile4.setWeaponCards(weaponsTile2);
        semplifiedTile11.setWeaponCards(weaponsTile3);



        semplifiedPlayers.add(player);
        semplifiedPlayers.add(player2);
        semplifiedPlayers3.add(player4);
        semplifiedPlayers2.add(player3);
        semplifiedTile.setPlayers(semplifiedPlayers);
        semplifiedTile5.setPlayers(semplifiedPlayers3);
        player.setTile(0);
        player2.setTile(0);
        player3.setTile(2);
        player4.setTile(5);
        semplifiedTile2.setPlayers(semplifiedPlayers2);

        semplifiedMap.setTile(semplifiedTile,0,0);
        semplifiedMap.setTile(semplifiedTile1,0,1);
        semplifiedMap.setTile(semplifiedTile2,0,2);
        semplifiedMap.setTile(semplifiedTile3,0,3);
        semplifiedMap.setTile(semplifiedTile4,1,0);
        semplifiedMap.setTile(semplifiedTile5,1,1);
        semplifiedMap.setTile(semplifiedTile6,1,2);
        semplifiedMap.setTile(semplifiedTile7,1,3);
        semplifiedMap.setTile(semplifiedTile8,2,0);
        semplifiedMap.setTile(semplifiedTile9,2,1);
        semplifiedMap.setTile(semplifiedTile10,2,2);
        semplifiedMap.setTile(semplifiedTile11,2,3);

        AmmoCard ammoCard1 = new AmmoCard();
        semplifiedMap.getTile(1,2).setAmmoCard(ammoCard1);
        semplifiedMap.getTile(0,0).setAmmoCard(ammoCard1);


        // System.out.println("player3 tile : "+player3.getTile());
        commandLineInterface.onMapChange(semplifiedMap);
        System.out.println("onHpChange");

        player.setPlayerBoard(new SemplifiedPlayerBoard());
        SemplifiedBloodToken semplifiedBloodToken = new SemplifiedBloodToken(player2);
        player.getPlayerBoard().getDamageTokens().add(semplifiedBloodToken);
        SemplifiedBloodToken semplifiedBloodToken2 = new SemplifiedBloodToken(player2);
        player.getPlayerBoard().getDamageTokens().add(semplifiedBloodToken2);
        SemplifiedBloodToken semplifiedBloodToken3 = new SemplifiedBloodToken(player3);
        player.getPlayerBoard().getDamageTokens().add(semplifiedBloodToken3);

        commandLineInterface.onHpChange(player);

        SemplifiedBloodToken semplifiedBloodToken4 = new SemplifiedBloodToken(player3);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken4);
        SemplifiedBloodToken semplifiedBloodToken5 = new SemplifiedBloodToken(player3);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken5);
        SemplifiedBloodToken semplifiedBloodToken6 = new SemplifiedBloodToken(player3);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken6);
        SemplifiedBloodToken semplifiedBloodToken7 = new SemplifiedBloodToken(player2);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken7);
        SemplifiedBloodToken semplifiedBloodToken8 = new SemplifiedBloodToken(player2);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken8);
        SemplifiedBloodToken semplifiedBloodToken9 = new SemplifiedBloodToken(player3);
        player.getPlayerBoard().getMarksTokens().add(semplifiedBloodToken9);

        System.out.println("onMarksChange");
        commandLineInterface.onMarksChange(player);
        commandLineInterface.onHelp(player);
        //}
    }
}
