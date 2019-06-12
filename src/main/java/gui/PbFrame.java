package gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.List;

public class PbFrame {

    private Stage stage;
    private List<String> players;

    private Pane pb1;
    private Pane pb2;
    private Pane pb3;
    private Pane pb4;

    public PbFrame(List<String> players) {
        this.stage = new Stage();
        this.players = players;
        generate();
    }

    private void generate(){
        stage.setTitle("Adrenalina - PlayerBoard avversari");
        stage.setResizable(false);

        pb1 = new Pane();
        pb2 = new Pane();
        pb3 = new Pane();
        pb4 = new Pane();

        pb1.setTranslateY(2);
        pb1.setTranslateX(17);
        pb1.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(0)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        pb2.setTranslateX(pb1.getTranslateX());
        pb2.setTranslateY(pb1.getTranslateY()+175);
        pb2.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(1)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        pb3.setTranslateX(pb1.getTranslateX());
        pb3.setTranslateY(pb2.getTranslateY()+175);
        pb3.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(2)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        pb4.setTranslateX(pb1.getTranslateX());
        pb4.setTranslateY(pb3.getTranslateY()+175);
        pb4.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(3)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        StackPane box = new StackPane();
        box.getChildren().add(pb1);
        box.getChildren().add(pb2);
        box.getChildren().add(pb3);
        box.getChildren().add(pb4);

        stage.setScene(new Scene(box, 850, 700));
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

    private InputStream boardParser(String board) {
        switch (board) {
            case "Distruttore":
                return getClass().getResourceAsStream("/img/DistruttoreBoard.png");
            case "Sprog":
                return getClass().getResourceAsStream("/img/SprogBoard.png");
            case "Dozer":
                return getClass().getResourceAsStream("/img/DozerBoard.png");
            case "Violetta":
                return getClass().getResourceAsStream("/img/ViolettaBoard.png");
            case "Banshee":
                return getClass().getResourceAsStream("/img/BansheeBoard.png");
            default:
                return getClass().getResourceAsStream("/img/DistruttoreBoard.png");
        }
    }
}
