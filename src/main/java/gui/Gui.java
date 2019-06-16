package gui;

import controller.CommandLauncher;
import javafx.application.Application;
import javafx.stage.Stage;
import model.GameManager;
import model.JsonCreator;
import model.Match;

public class Gui extends Application {

    CommandLauncher cmd = new CommandLauncher(new GameManager(), new JsonCreator());
    MainFrame mainFrame = new MainFrame();
//    LobbyFrame lobbyFrame = new LobbyFrame(cmd);
    GameFrame gameFrame = new GameFrame(cmd, "Distruttore", 1);

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
}
