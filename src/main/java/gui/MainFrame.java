package gui;

import controller.CommandLauncher;
import controller.commandpack.CreateUserCommand;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GameManager;

import java.io.File;
import java.io.FileInputStream;

public class MainFrame extends Application {
    final int buttonWidth = 150;
    private CommandLauncher cmdLauncher = new CommandLauncher(new GameManager());//TODO togliere

    public void init(CommandLauncher cmd){
        this.cmdLauncher = cmd;
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Adrenalina - the official game");
        stage.setResizable(false);

        Button startButtonSocket = new Button("Inizia con socket");
        Button startButtonRMI = new Button("Inizia con RMI");
        TextField userField = new TextField("Username");
        Label info = new Label();

        info.setTranslateY(275);
        info.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        userField.setMaxWidth(200);
        //positioning socket button
        startButtonSocket.setTranslateX(-80);
        startButtonSocket.setTranslateY(60);
        startButtonSocket.setMinWidth(buttonWidth);
        startButtonSocket.setMaxWidth(buttonWidth);
        startButtonSocket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkUsername(userField.getText().trim())){
                    //TODO impostazione tipo connessione
                    cmdLauncher.addCommand(new CreateUserCommand(null,  userField.getText().trim()));
                    openNextStage(stage);

                }
                else{
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }
            }
        });
        //positioning rmi button
        startButtonRMI.setTranslateX(80);
        startButtonRMI.setTranslateY(60);
        startButtonRMI.setMinWidth(buttonWidth);
        startButtonRMI.setMaxWidth(buttonWidth);
        startButtonRMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkUsername(userField.getText().trim())){
                    //TODO impostazione tipo connessione
                    cmdLauncher.addCommand(new CreateUserCommand(null,  userField.getText().trim()));
                    openNextStage(stage);
                }
                else{
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }
            }
        });

        //path of background image
        String path = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Adrenalina.png";

        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(path),1000,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        StackPane box = new StackPane();
        //adding background image to box
        box.setBackground(new Background(myBI));
        //adding components to box
        box.getChildren().add(userField);
        box.getChildren().add(startButtonSocket);
        box.getChildren().add(startButtonRMI);
        box.getChildren().add(info);

        stage.setScene(new Scene(box, 1000, 600));
        stage.show();
    }

    public void openNextStage(Stage stage){
        LobbyFrame lf = new LobbyFrame();
        try {
            lf.init(cmdLauncher);
            lf.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.close();
    }

    private boolean checkUsername(String username){
        return !username.equalsIgnoreCase("") && !username.equalsIgnoreCase("username");
    }

}
