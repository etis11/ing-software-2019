package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class LobbyFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Adrenalina - lobby d'attesa");
        stage.setResizable(false);

        Button buttonPlayer1 = new Button();
        Button buttonPlayer2 = new Button();
        Button buttonPlayer3 = new Button();
        Button buttonPlayer4 = new Button();
        Button buttonPlayer5 = new Button();

        //TODO rendere statici queste variabili?
        //path of background image
        String path = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Adrenalina.png";

        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(path),1000,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Pane box = new Pane();
        //adding background image to box
        box.setBackground(new Background(myBI));
        //adding components to box
        box.getChildren().add(buttonPlayer1);
        box.getChildren().add(buttonPlayer2);
        box.getChildren().add(buttonPlayer3);
        box.getChildren().add(buttonPlayer4);
        box.getChildren().add(buttonPlayer5);


        stage.setScene(new Scene(box, 1000, 600));
        stage.show();
    }
}
