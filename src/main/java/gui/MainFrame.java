package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class MainFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Adrenalina - the official game");
        stage.setResizable(false);

        //Button startButton = new Button("Inizia");
        TextField userField = new TextField("Username");

        String path = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Adrenalina.png";

        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(path),1000,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        StackPane box = new StackPane();
        box.setBackground(new Background(myBI));
        //box.getChildren().add(startButton);
        box.getChildren().add(userField);
        stage.setScene(new Scene(box, 1000, 600));
        stage.show();
    }
}
