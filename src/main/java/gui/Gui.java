package gui;

import controller.CommandLauncher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.GameManager;
import model.JsonCreator;
import model.Match;
import model.User;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import view.*;

import java.util.List;

public class Gui extends Application implements LobbyListener, MapObserver, MessageListener, PlayerObserver, MatchObserver  {

    CommandLauncher cmd = new CommandLauncher(new GameManager(), new JsonCreator());
    MainFrame mainFrame = new MainFrame();
//    LobbyFrame lobbyFrame = new LobbyFrame(cmd);
    GameFrame gameFrame = new GameFrame(cmd, "distruttore", "picccola");

    @Override
    public void start(Stage stage) throws Exception {
//        mainFrame.show();
//        lobbyFrame.show();
        gameFrame.show();
    }

    public void startLobby(){
        mainFrame.close();
        //lobbyFrame.show();
    }

    @Override
    public void onJoin(User joinedUser) {

    }

    @Override
    public void onLeave(User leavingUser) {

    }

    @Override
    public void onMapChange(SemplifiedMap map) {

    }

    @Override
    public void onTypeMapChange(String mapName) {

    }

    @Override
    public void onCurrentPlayerChange(SemplifiedPlayer p) {

    }

    @Override
    public void onSkullChange(List<List<SemplifiedBloodToken>> skull) {

    }

    @Override
    public void notifyMessage(String message) {

        Platform.runLater(()-> gameFrame.notifyMessage("yolo"));
    }

    @Override
    public void onHpChange(SemplifiedPlayer damagePlayer) {

    }

    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {

    }

    @Override
    public void onAmmoChange(SemplifiedPlayer p) {

    }

    @Override
    public void onPowerUpChange(SemplifiedPlayer p) {

    }

    @Override
    public void onWeaponChange(SemplifiedPlayer p) {

    }

    @Override
    public void onPlayerChange(SemplifiedPlayer p) {

    }
}
