package gui;

import controller.CommandContainer;
import controller.CommandLauncher;
import controller.commandpack.Command;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameManager;
import model.JsonCreator;
import model.Match;

public class Gui extends Application {
    private String username;
    private String map =  "piccola";

    CommandContainer cmd = new CommandLauncher(new GameManager(), new JsonCreator());
    MainFrame mainFrame;
    LobbyFrame lobbyFrame;
    GameFrame gameFrame = new GameFrame(cmd, "distruttore", "picccola");

    @Override
    public void start(Stage stage) throws Exception {
        mainFrame = new MainFrame(this);
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
}
