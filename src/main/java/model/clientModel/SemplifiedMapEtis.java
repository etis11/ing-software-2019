package model.clientModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.GameMap;
import model.Match;
import model.Room;
import model.Tile;
import view.AnsiColor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SemplifiedMapEtis {

    //TODO decidere se ci servira' o meno
    String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    private SemplifiedTile[][] semplifiedTiles;

    public static String hash1 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"    REG   "+AnsiColor.RESET+"║          ║\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"     0    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"      1   "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"     2    "+AnsiColor.RESET+"║    3     ║\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+"║          ║\n" +
            "╠═══    ═══╬══════════╬═══    ═══╬══════════╣\n" +
            "║"+AnsiColor.RED_BACKGROUND+"    REG   "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     4    "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"     5    "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"      6   "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    7     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠══════════╬═══    ═══╬══════════╬          ╣\n" +
            "║          ║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"    REG   "+AnsiColor.RESET+"║\n" +
            "║    8     ║"+AnsiColor.GREEN_BACKGROUND+"    9     "+AnsiColor.RESET+" "+AnsiColor.GREEN_BACKGROUND+"     10   "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    11    "+AnsiColor.RESET+"║\n" +
            "║          ║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash2 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.BLUE_BACKGROUND+"                REG  "+AnsiColor.RESET+"║          ║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     0    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"      1         2    "+AnsiColor.RESET+"║    3     ║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.BLUE_BACKGROUND+"                     "+AnsiColor.RESET+"║          ║\n" +
            "╠"+AnsiColor.RED_BACKGROUND_BRIGHT+"          "+AnsiColor.RESET+"╬═══    ═══╬═══    ═══╬══════════╣\n" +
            "║"+AnsiColor.RED_BACKGROUND+"      REG "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"                     "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     4    "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"     5          6    "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    7     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"                     "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠═══    ═══╬═══    ═══╬══════════╬"+AnsiColor.YELLOW_BACKGROUND_BRIGHT+"          "+AnsiColor.RESET+"╣\n" +
            "║"+AnsiColor.MAGENTA_BACKGROUND+"                                "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"   REG    "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.MAGENTA_BACKGROUND+"    8          9          10    "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    11    "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.MAGENTA_BACKGROUND+"                                "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash3 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.BLUE_BACKGROUND+"              REG    "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     0    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"      1         2    "+AnsiColor.RESET+" "+AnsiColor.GREEN_BACKGROUND+"    3     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.BLUE_BACKGROUND+"                     "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠"+AnsiColor.RED_BACKGROUND_BRIGHT+"          "+AnsiColor.RESET+"╬═══    ═══╬═══    ═══╬═══    ═══╣\n" +
            "║"+AnsiColor.RED_BACKGROUND+"      REG "+AnsiColor.RESET+"║"+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     4    "+AnsiColor.RESET+"║"+AnsiColor.MAGENTA_BACKGROUND+"     5    "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"     6    "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"    7     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠═══    ═══╬═══    ═══╬"+AnsiColor.YELLOW_BACKGROUND_BRIGHT+"          "+AnsiColor.RESET+"╬"+AnsiColor.YELLOW_BACKGROUND_BRIGHT+"          "+AnsiColor.RESET+"╣\n" +
            "║"+AnsiColor.WHITE_BACKGROUND+"                     "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"   REG    "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.WHITE_BACKGROUND+"    8          9     "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    10    "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"    11    "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.WHITE_BACKGROUND+"                     "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND_BRIGHT+" "+AnsiColor.RESET+""+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash4 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"      REG "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"     0    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"     1    "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"     2    "+AnsiColor.RESET+" "+AnsiColor.GREEN_BACKGROUND+"    3     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.BLUE_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.GREEN_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠═══    ═══╬══════════╬═══    ═══╬═══    ═══╣\n" +
            "║"+AnsiColor.RED_BACKGROUND+"    REG   "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"     4    "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"     5    "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"     6    "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    7     "+AnsiColor.RESET+"║\n" +
            "║"+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.RED_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╠══════════╬═══    ═══╬          ╬          ╣\n" +
            "║          ║"+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    REG   "+AnsiColor.RESET+"║\n" +
            "║    8     ║"+AnsiColor.MAGENTA_BACKGROUND+"    9     "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    10    "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"    11    "+AnsiColor.RESET+"║\n" +
            "║          ║"+AnsiColor.MAGENTA_BACKGROUND+"          "+AnsiColor.RESET+"║"+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+" "+AnsiColor.YELLOW_BACKGROUND+"          "+AnsiColor.RESET+"║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    private Match match ;

    public SemplifiedMapEtis(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        String hashlol = null;
        try {
            hashlol = readFile("C:\\Users\\new xps\\Documents\\ing-sw-2019-44\\maps\\map4.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameMap gameMap = this.match.getMap();
        List<Tile> allTiles = new LinkedList<>();
        List<Integer> ids = new LinkedList<>();
        String hash = "";
        switch (gameMap.getFilePath()){
            case "maps/map1.json" :
                hash = hash1;
                break;
            case "maps/map2.json" :
                hash = hash2;
                break;
            case "maps/map3.json" :
                hash = hash3;
                break;
            case "maps/map4.json" :
                hash = hash4;
                break;
        }


        for(Room room : gameMap.getRooms()){
            allTiles.addAll(room.getTiles());
        }
        for(Tile tile : allTiles){
            ids.add(tile.getID());
        }

        String combo = "";
        List<SemplifiedTile> simplified = new LinkedList<>();
        List<Tile> sortedallTiles = allTiles.stream().sorted(Comparator.comparing(Tile::getID)).collect(Collectors.toList());
        for(Tile tile :sortedallTiles){
            SemplifiedTile semplifiedTile = new SemplifiedTile(tile.getID(), tile.getPlayers(), tile.canContainAmmo(), tile.getAmmoCard(), tile.canContainWeapons(), null);
            semplifiedTile.setRow(tile.getID()/4);
            semplifiedTile.setColumn(tile.getID()%4);

            simplified.add(semplifiedTile);
            combo= combo+semplifiedTile+"\n";

        }
        combo = hash+ "\n"+combo;

        return combo;
    }
}
