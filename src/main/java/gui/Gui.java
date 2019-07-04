package gui;

import controller.CommandContainer;
import controller.CommandLauncher;
import controller.commandpack.Command;
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


    private String username;
    private String map =  "piccola";
    private String token;
    private CommandContainer cmd = new CommandLauncher(new GameManager(), new JsonCreator());
    private MainFrame mainFrame;
    private LobbyFrame lobbyFrame;
    private GameFrame gameFrame = new GameFrame(cmd, "distruttore", "picccola");

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
//        lobbyFrame.show();
//        gameFrame.show();
    }

    public void startLobby(){
        mainFrame.close();
        lobbyFrame = new LobbyFrame(cmd, this);
        lobbyFrame.show();
    }

    public void startGame(){
        lobbyFrame.close();
        gameFrame.show();
    }

    public void setUsername(String username){
        this.username=username;
    }

    public void createGameFrame(){
        gameFrame = new GameFrame(cmd, username, map);
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
