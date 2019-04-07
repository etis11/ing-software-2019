package Model;

import java.util.List;

public class Room {

    private List<NormalTile> normalTile;
    private List<Player> getplayersinroom;

    public List<NormalTile> getNormalTile() {
        return normalTile;
    }

    public void setNormalTile(List<NormalTile> normalTile) {
        this.normalTile = normalTile;
    }

    public List<Player> getGetplayersinroom() {
        return getplayersinroom;
    }

    public void setGetplayersinroom(List<Player> getplayersinroom) {
        this.getplayersinroom = getplayersinroom;
    }


}
