package model.clientModel;

import view.AnsiColor;

import java.util.List;


public class SemplifiedMap {
    private SemplifiedTile[][] map;



    public static String hash1 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "    REG   " + AnsiColor.RESET + "║          ║\n" +
            "║" + AnsiColor.BLUE_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1   " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "     2    " + AnsiColor.RESET + "║    3     ║\n" +
            "║" + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "          " + AnsiColor.RESET + "║          ║\n" +
            "╠═══    ═══╬══════════╬═══    ═══╬══════════╣\n" +
            "║" + AnsiColor.RED_BACKGROUND + "    REG   " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "     5    " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "      6   " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╠══════════╬═══    ═══╬══════════╬          ╣\n" +
            "║          ║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "    REG   " + AnsiColor.RESET + "║\n" +
            "║    8     ║" + AnsiColor.GREEN_BACKGROUND + "    9     " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "     10   " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\n" +
            "║          ║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash2 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "                REG  " + AnsiColor.RESET + "║          ║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1         2    " + AnsiColor.RESET + "║    3     ║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "                     " + AnsiColor.RESET + "║          ║\n" +
            "╠" + AnsiColor.RED_BACKGROUND_BRIGHT + "          " + AnsiColor.RESET + "╬═══    ═══╬═══    ═══╬══════════╣\n" +
            "║" + AnsiColor.RED_BACKGROUND + "      REG " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "                     " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "     5          6    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "                     " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╠═══    ═══╬═══    ═══╬══════════╬" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + "          " + AnsiColor.RESET + "╣\n" +
            "║" + AnsiColor.MAGENTA_BACKGROUND + "                                " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "   REG    " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.MAGENTA_BACKGROUND + "    8          9          10    " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.MAGENTA_BACKGROUND + "                                " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash3 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "              REG    " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "     0    " + AnsiColor.RESET + " " + AnsiColor.BLUE_BACKGROUND + "      1         2    " + AnsiColor.RESET + " " + AnsiColor.GREEN_BACKGROUND + "    3     " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.BLUE_BACKGROUND + "                     " + AnsiColor.RESET + "║" + AnsiColor.GREEN_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╠" + AnsiColor.RED_BACKGROUND_BRIGHT + "          " + AnsiColor.RESET + "╬═══    ═══╬═══    ═══╬═══    ═══╣\n" +
            "║" + AnsiColor.RED_BACKGROUND + "      REG " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "     4    " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "     5    " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "     6    " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "    7     " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.RED_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.MAGENTA_BACKGROUND + "          " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╠═══    ═══╬═══    ═══╬" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + "          " + AnsiColor.RESET + "╬" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + "          " + AnsiColor.RESET + "╣\n" +
            "║" + AnsiColor.WHITE_BACKGROUND + "                     " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "   REG    " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.WHITE_BACKGROUND + "    8          9     " + AnsiColor.RESET + " " + AnsiColor.YELLOW_BACKGROUND + "    10    " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "    11    " + AnsiColor.RESET + "║\n" +
            "║" + AnsiColor.WHITE_BACKGROUND + "                     " + AnsiColor.RESET + "║" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND_BRIGHT + " " + AnsiColor.RESET + "" + AnsiColor.YELLOW_BACKGROUND + "          " + AnsiColor.RESET + "║\n" +
            "╚══════════╩══════════╩══════════╩══════════╝";

    public static String hash4 = "╔══════════╦══════════╦══════════╦══════════╗\n" +
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

    public SemplifiedTile getTile(int row, int col) {
        return map[row][col];
    }

    public void setTile(SemplifiedTile tile, int row, int col) {
        map[row][col] = tile;
    }

    public void updateTiles(List<SemplifiedTile> tiles){

    }

    /**
     * Prints each tile
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("rows: " + map.length + " cols: " + map[0].length + "\n");
        for (SemplifiedTile[] row : map) {
            for (SemplifiedTile tile : row) {
                builder.append(tile.toString() + "\n");
            }
        }
        return builder.toString();
    }
}
