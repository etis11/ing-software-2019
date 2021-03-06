package view.gui;

import controller.CommandContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.User;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import view.*;
import java.util.List;





public class Gui extends Application implements LobbyListener, MapObserver, MessageListener, PlayerObserver, MatchObserver  {


    private String username;
    private String map =  "piccola";
    private String token;
    private CommandContainer cmd;
    private MainFrame mainFrame;
    private LobbyFrame lobbyFrame;
    private GameFrame gameFrame;

    @Override
    public void start(Stage stage) throws Exception {
        Parameters params = getParameters();
        List<String> args = params.getRaw();
        if (args.size() <2){
            System.out.println(AnsiColor.RED +  "Inserire IP e porta del server");
            System.exit(0);
        }
        String ip = args.get(0);
        int port = Integer.parseInt(args.get(1));

        mainFrame = new MainFrame(this, ip, port);
        mainFrame.show();
    }

    public void startLobby(){
        mainFrame.close();
        lobbyFrame = new LobbyFrame(cmd, this);
        lobbyFrame.show();
    }

    public void startGame(){
        if(username==null){
            createGameFrame();
        }
        Platform.runLater(()-> lobbyFrame.close());
        Platform.runLater(()-> gameFrame.show());
    }

    public void setUsername(String username){
        this.username=username;
    }

    public void createGameFrame(){
        gameFrame = new GameFrame(cmd, username, map, this);
    }

    public void setCmd(CommandContainer cmd) {
        this.cmd = cmd;
    }

    public void setMap(String map){
        this.map = map;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onJoin(User joinedUser) {

    }

    @Override
    public void onLeave(User leavingUser) {

    }

    @Override
    public void onMapChange(SemplifiedMap map) {
        Platform.runLater(()-> gameFrame.onMapChange(map));
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
        switch (message){
            case "La partita sta per iniziare":
                startGame();
                Platform.runLater(()-> gameFrame.notifyMessage(message));
                break;
            case "scegli quale powerup scartare per spawnare":
                Platform.runLater(()-> gameFrame.showPopUpPowerUp());
            default:
                if(gameFrame!= null) {
                    Platform.runLater(() -> gameFrame.notifyMessage(message));
                }
                break;
        }
    }

    @Override
    public void onHpChange(SemplifiedPlayer damagePlayer) {
        Platform.runLater(()-> gameFrame.onHpChange(damagePlayer));
    }

    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {
        Platform.runLater(()-> gameFrame.onMarksChange(markedPlayer));
    }

    @Override
    public void onAmmoChange(SemplifiedPlayer p) {
        Platform.runLater(()-> gameFrame.onAmmoChange(p));
    }

    @Override
    public void onPowerUpChange(SemplifiedPlayer p) {
        Platform.runLater(()-> gameFrame.onPowerUpChange(p));
    }

    @Override
    public void onWeaponChange(SemplifiedPlayer p) {
        Platform.runLater(()-> gameFrame.onWeaponChange(p));
    }

    @Override
    public void onPlayerChange(SemplifiedPlayer p) {
        Platform.runLater(()-> gameFrame.onPlayerChange(p));
    }
}
