package Test;

import jsonparser.JsonFileReader;
import jsonparser.WeaponCardDeserializer;
import model.AmmoCard;
import model.Match;
import model.WeaponCard;
import model.clientModel.SemplifiedPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AnsiColor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponCardTest {

    private WeaponCard card;

    @BeforeEach
    public void init() {
        card = new WeaponCard();
    }

    @Test
    public void constructorTest() {
        assertTrue(card instanceof WeaponCard, "ERROR of instance");

        WeaponCard card2 = new WeaponCard(card);
        assertTrue(card2 instanceof WeaponCard, "ERROR of instance");
        //System.out.println(AnsiColor.RED+"\uD83D\uDDA4");
        System.out.print(AnsiColor.GREEN + "Sprog" + AnsiColor.RESET + " ha un numero diverso di ammo rispetto a prima:\n");
        System.out.print("Ammo " + AnsiColor.BLUE + "blu" + AnsiColor.RESET + ": 3 ");
        for (int i = 0; i < 3; i++) {
            System.out.print(AnsiColor.BLUE + "⬜" + AnsiColor.RESET);
        }
        System.out.print("\n");
        System.out.print("Ammo " + AnsiColor.RED + "rosse" + AnsiColor.RESET + ": 2 ");
        for (int i = 0; i < 2; i++) {
            System.out.print(AnsiColor.RED + "⬜" + AnsiColor.RESET);
        }
        System.out.print("\n");
        System.out.print("Ammo " + AnsiColor.YELLOW + "gialle" + AnsiColor.RESET + ": 1 ");
        for (int i = 0; i < 1; i++) {
            System.out.print(AnsiColor.YELLOW + "⬜" + AnsiColor.RESET);
        }
        System.out.println("\n");

        System.out.print("A" + AnsiColor.GREEN + " Sprog" + AnsiColor.RESET + " si e' cambiato il numero dei marchi in: 4 ");
        for (int i = 0; i < 4; i++) {
            System.out.print(AnsiColor.GREEN + "\uD83D\uDCA7" + AnsiColor.RESET);
        }
        System.out.println("\n");

        System.out.print(AnsiColor.GREEN + "Sprog" + AnsiColor.RESET + " ha subito danni e quindi e' rimasto con 6 vite ");
        for (int i = 0; i < 6; i++) {
            System.out.print(AnsiColor.GREEN+"\uD83D\uDDA4" + AnsiColor.RESET);
        }
    }
    @Test
    public void equalsTest() {
        assertFalse(card.equals(null), "ERROR: must be false card not equals with null");

        WeaponCard card2 = card;
        assertTrue(card.equals(card2), "ERROR: must be true cards are the same object");

        AmmoCard card3 = new AmmoCard();
        assertFalse(card.equals(card3), "ERROR: must be false, they are different classes");

        //TODO
        //oggetti distinti ma uguali->serve costruttore
    }

    @Test
    void jsonLoadTest() {
        JsonFileReader jsonFileReader = new JsonFileReader();
        String cards = jsonFileReader.loadWeaponCards("cards/cards.json");
        Match match = new Match();
        //List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        WeaponCardDeserializer weaponCardDeserializer = new WeaponCardDeserializer(match);
        List<WeaponCard> weaponCards = weaponCardDeserializer.parseWeaponCards(cards);
        System.out.println(weaponCards.get(0).getBaseEffect().get(0).getMarks());

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
   //     String[] fags = {fag0,fag1 ,fag2,fag3,fag4,fag5,fag6,fag7 ,fag8,fag9,fag10,fag11};
       // fag1="F";

        AnsiColor blueBackground = AnsiColor.BLUE_BACKGROUND ;
      //  fag1="FB        ";

        String[] fags = {fag0,fag1 ,fag2,fag3,fag4,fag5,fag6,fag7 ,fag8,fag9,fag10,fag11};
        SemplifiedPlayer semplifiedPlayer = new SemplifiedPlayer();
        semplifiedPlayer.setName("Dozer");

        semplifiedPlayer.setTile(1);

        SemplifiedPlayer semplifiedPlayerGay = new SemplifiedPlayer();
        semplifiedPlayerGay.setName("Sprog");
        semplifiedPlayerGay.setTile(1);

    //    fags[semplifiedPlayer.getTile()] = fags[semplifiedPlayer.getTile()].replaceFirst("\\s", AnsiColor.BLUE_BACKGROUND+playerColor(semplifiedPlayerGay)+"T");
        fags[semplifiedPlayer.getTile()] = fags[semplifiedPlayer.getTile()].substring(0, fags[semplifiedPlayer.getTile()].lastIndexOf(" ")) +playerColor(semplifiedPlayer)+"T"+fags[semplifiedPlayer.getTile()].substring(fags[semplifiedPlayerGay.getTile()].lastIndexOf(" ")+1);
        fags[semplifiedPlayer.getTile()] = fags[semplifiedPlayer.getTile()].substring(0, fags[semplifiedPlayerGay.getTile()].lastIndexOf(" ")) +playerColor(semplifiedPlayerGay)+"T"+fags[semplifiedPlayer.getTile()].substring(fags[semplifiedPlayerGay.getTile()].lastIndexOf(" ")+1);
        fags[semplifiedPlayer.getTile()].substring(fags[semplifiedPlayerGay.getTile()].lastIndexOf(" ")+1);

        System.out.println("last index of space "+fags[semplifiedPlayer.getTile()].lastIndexOf(" "));

                //.replaceFirst("\\s", playerColor(semplifiedPlayerGay)+"T");



        String bb = fags[semplifiedPlayer.getTile()];

        bb=bb.replaceFirst("\\s", "T");

        //System.out.println("bb : "+bb);

        for(int i=0; i<12 ; i++ ){

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

        int cellIndex =hash4.indexOf("2",91);
        String lol = AnsiColor.RESET+"";
        System.out.println((lol.length()));
        System.out.println(cellIndex);

        String riga = "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     1    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║";

        int cellIndexRiga =riga.indexOf("2");

   //     System.out.println("index of row "+cellIndexRiga);
   //     System.out.println("length of row"+riga.length());
   //     System.out.println(hash4.length());

        System.out.println(hash4);
    }

    public String playerColor(SemplifiedPlayer p) {
        String playercolor = "";
        p.getName();
        switch(p.getName()) {
            case "Dozer":
                playercolor = AnsiColor.WHITE+"";
                break;
            case "Sprog":
                playercolor = AnsiColor.GREEN_BOLD+"";
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
