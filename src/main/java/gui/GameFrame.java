package gui;

import controller.CommandLauncher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameFrame extends Application {

    private CommandLauncher cmdLauncher;

    public void init(CommandLauncher cmd){
        this.cmdLauncher = cmd;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //TODO da implementare
        StackPane mainPane = new StackPane();
        Pane gameLog = new Pane();
        TextArea infoGame = new TextArea();



        infoGame.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        mainPane.getChildren().add(gameLog);
        Scene scene = new Scene(mainPane, 1200, 650);
        stage.setScene(scene);
        stage.show();
    }
}
