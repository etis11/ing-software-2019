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
    final int buttonWidth = 75;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Adrenalina - lobby d'attesa");
        stage.setResizable(false);

        Button buttonPlayer1 = new Button();
        Button buttonPlayer2 = new Button();
        Button buttonPlayer3 = new Button();
        Button buttonPlayer4 = new Button();
        Button buttonPlayer5 = new Button();

        buttonPlayer1.setLayoutY(300);
        buttonPlayer1.setLayoutX(50);
        buttonPlayer1.setMinWidth(buttonWidth);
        buttonPlayer1.setMinHeight(buttonWidth);
        buttonPlayer2.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer2.setLayoutX(250);
        buttonPlayer2.setMinWidth(buttonWidth);
        buttonPlayer2.setMinHeight(buttonWidth);
        buttonPlayer3.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer3.setLayoutX(450);
        buttonPlayer3.setMinWidth(buttonWidth);
        buttonPlayer3.setMinHeight(buttonWidth);
        buttonPlayer4.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer4.setLayoutX(650);
        buttonPlayer4.setMinWidth(buttonWidth);
        buttonPlayer4.setMinHeight(buttonWidth);
        buttonPlayer5.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer5.setLayoutX(850);
        buttonPlayer5.setMinWidth(buttonWidth);
        buttonPlayer5.setMinHeight(buttonWidth);

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
