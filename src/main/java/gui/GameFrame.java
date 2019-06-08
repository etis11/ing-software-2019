package gui;

import controller.CommandContainer;
import controller.CommandLauncher;
import controller.commandpack.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.ClientSingleton;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;

public class GameFrame extends Application {

    private CommandContainer cmdLauncher;

    public void init(CommandContainer cmd){
        this.cmdLauncher = cmd;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //TODO da implementare

        stage.setTitle("Adrenalina - on game");
        stage.setResizable(false);

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
        infoGame.setMaxWidth(300);
        infoGame.setMinHeight(500);
        infoGame.setMaxHeight(500);
        infoGame.appendText("Benvenuto in Adrenalina! \n");
        infoGame.setEditable(false);
        infoGame.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        //path of background image
        final String path = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"SmallMap.png";

        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(path),845,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        mapPane.setBackground(new Background(myBI));

        walkButton.setLayoutY(10);
        walkButton.setMinWidth(100);
        walkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskWalkCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        pickButton.setLayoutY(walkButton.getLayoutY()+30);
        pickButton.setMinWidth(100);
        pickButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskPickCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        shootButton.setLayoutY(pickButton.getLayoutY()+30);
        shootButton.setMinWidth(100);
        shootButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskShootCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        powerUpButton.setLayoutY(shootButton.getLayoutY()+30);
        powerUpButton.setMinWidth(100);
        powerUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskUsePowerUpCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        reloadButton.setLayoutY(powerUpButton.getLayoutY()+30);
        reloadButton.setMinWidth(100);
        reloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskReloadCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        endTurnButton.setLayoutY(reloadButton.getLayoutY()+30);
        endTurnButton.setMinWidth(100);
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskEndTurnCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //setting gamelog
        gameLog.getChildren().add(infoGame);

        //setting buttonpane
        buttonPane.getChildren().add(walkButton);
        buttonPane.getChildren().add(pickButton);
        buttonPane.getChildren().add(shootButton);
        buttonPane.getChildren().add(powerUpButton);
        buttonPane.getChildren().add(reloadButton);
        buttonPane.getChildren().add(endTurnButton);

        buttonPane.setTranslateX(1175);
        mapPane.setTranslateX(305);

        //setting mainpane
        mainPane.getChildren().add(gameLog);
        mainPane.getChildren().add(buttonPane);
        mainPane.getChildren().add(mapPane);
        mainPane.getChildren().add(playerBoardPane);

        //set scene
        Scene scene = new Scene(mainPane, 1300, 700);
        stage.setScene(scene);
        stage.show();
    }
}
