package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Adrenalina");
        Button startButton = new Button("Inizia a giocare");
        VBox box = new VBox();
        box.getChildren().add(startButton);
        stage.setScene(new Scene(box));
        stage.show();
    }
}
