package Model;

import java.util.List;

public class Tile {



    public Tile EastTile;
    public Tile WestTile;
    public Tile NorthTile;
    public Tile SouthTile;

    private List<Player> getPlayers;

    public Tile getEastTile() {
        return EastTile;
    }

    public void setEastTile(Tile eastTile) {
        EastTile = eastTile;
    }

    public Tile getWestTile() {
        return WestTile;
    }

    public void setWestTile(Tile westTile) {
        WestTile = westTile;
    }

    public Tile getNorthTile() {
        return NorthTile;
    }

    public void setNorthTile(Tile northTile) {
        NorthTile = northTile;
    }

    public Tile getSouthTile() {
        return SouthTile;
    }

    public void setSouthTile(Tile southTile) {
        SouthTile = southTile;
    }


    public List<Player> getPlayersInTile() {
        return getPlayers;
    }

}
