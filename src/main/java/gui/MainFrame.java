package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.FileInputStream;

public class MainFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Adrenalina - the official game");
        stage.setResizable(false);

        Button startButtonSocket = new Button("Inizia con socket");
        Button startButtonRMI = new Button("Inizia con RMI");
        TextField userField = new TextField("Username");
        Label info = new Label();

        userField.setMaxWidth(200);
        //positioning socket button
        startButtonSocket.setTranslateX(-70);
        startButtonSocket.setTranslateY(60);
        startButtonSocket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(crateUser(userField.getText().trim())){
                    //TODO impostazione tipo connessione

                }
                else{
                    info.setText("inserisci un username");
                    info.setVisible(true);
                }
            }
        });
        //positioning rmi button
        startButtonRMI.setTranslateX(70);
        startButtonRMI.setTranslateY(60);
        startButtonRMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(crateUser(userField.getText().trim())){
                    //TODO impostazione tipo connessione
                }
                else{
                    info.setText("inserisci un username");
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

        stage.setScene(new Scene(box, 1000, 600));
        stage.show();
    }

    private boolean crateUser(String username){
        if(!username.equalsIgnoreCase("")){
            User user = new User(username);
            //TODO join the lobby
            return true;
        }
        else{
            return false;
        }
    }
}
