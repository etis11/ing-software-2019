package gui;

import controller.CommandLauncher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Pane buttonPane = new Pane();
        Pane mapPane = new Pane();
        Pane playerBoardPane = new Pane();

        TextArea infoGame = new TextArea();

        Button walkButton = new Button("Spostati");
        Button pickButton = new Button("Raccogli");
        Button shootButton = new Button("Spara");
        Button powerUpButton = new Button("Usa Powerup");
        Button reloadButton = new Button("Ricarica");
        Button endTurnButton = new Button("Fine Turno");

        infoGame.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        walkButton.setLayoutY(10);
        walkButton.setMinWidth(100);
        pickButton.setLayoutY(walkButton.getLayoutY()+30);
        pickButton.setMinWidth(100);
        shootButton.setLayoutY(pickButton.getLayoutY()+30);
        shootButton.setMinWidth(100);
        powerUpButton.setLayoutY(shootButton.getLayoutY()+30);
        powerUpButton.setMinWidth(100);
        reloadButton.setLayoutY(powerUpButton.getLayoutY()+30);
        reloadButton.setMinWidth(100);
        endTurnButton.setLayoutY(reloadButton.getLayoutY()+30);
        endTurnButton.setMinWidth(100);

        //setting gamelog
        gameLog.getChildren().add(infoGame);

        //setting buttonpane
        buttonPane.getChildren().add(walkButton);
        buttonPane.getChildren().add(pickButton);
        buttonPane.getChildren().add(shootButton);
        buttonPane.getChildren().add(powerUpButton);
        buttonPane.getChildren().add(reloadButton);
        buttonPane.getChildren().add(endTurnButton);

        buttonPane.setTranslateX(900);

        //setting mainpane
        mainPane.getChildren().add(gameLog);
        mainPane.getChildren().add(buttonPane);
        mainPane.getChildren().add(mapPane);
        mainPane.getChildren().add(playerBoardPane);

        //set scene
        Scene scene = new Scene(mainPane, 1200, 650);
        stage.setScene(scene);
        stage.show();
    }
}
