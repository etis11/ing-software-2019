package gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PbFrame {

    private Stage stage;
    private List<String> players;

    private Pane pb1;
    private Pane pb2;
    private Pane pb3;
    private Pane pb4;

    private Circle damage1;
    private Circle damage2;
    private Circle damage3;
    private Circle damage4;
    private Circle damage5;
    private Circle damage6;
    private Circle damage7;
    private Circle damage8;
    private Circle damage9;
    private Circle damage10;
    private Circle damage11;
    private Circle damage12;
    private List<Circle> firstDamage;

    private Circle damage13;
    private Circle damage14;
    private Circle damage15;
    private Circle damage16;
    private Circle damage17;
    private Circle damage18;
    private Circle damage19;
    private Circle damage20;
    private Circle damage21;
    private Circle damage22;
    private Circle damage23;
    private Circle damage24;
    private List<Circle> secondDamage;

    private Circle damage25;
    private Circle damage26;
    private Circle damage27;
    private Circle damage28;
    private Circle damage29;
    private Circle damage30;
    private Circle damage31;
    private Circle damage32;
    private Circle damage33;
    private Circle damage34;
    private Circle damage35;
    private Circle damage36;
    private List<Circle> thirdDamage;

    public PbFrame(List<String> players) {
        this.stage = new Stage();
        this.players = players;
        this.firstDamage = new ArrayList<>();
        this.secondDamage = new ArrayList<>();
        generate();
    }

    private void generate(){
        stage.setTitle("Adrenalina - PlayerBoard avversari");
        stage.setResizable(false);

        pb1 = new Pane();
        pb2 = new Pane();
        pb3 = new Pane();
        pb4 = new Pane();

        damage1 = new Circle();
        damage2 = new Circle();
        damage3 = new Circle();
        damage4 = new Circle();
        damage5 = new Circle();
        damage6 = new Circle();
        damage7 = new Circle();
        damage8 = new Circle();
        damage9 = new Circle();
        damage10 = new Circle();
        damage11= new Circle();
        damage12 = new Circle();
        firstDamage.add(damage1);
        firstDamage.add(damage2);
        firstDamage.add(damage3);
        firstDamage.add(damage4);
        firstDamage.add(damage5);
        firstDamage.add(damage6);
        firstDamage.add(damage7);
        firstDamage.add(damage8);
        firstDamage.add(damage9);
        firstDamage.add(damage10);
        firstDamage.add(damage11);
        firstDamage.add(damage12);
        for (Circle c: firstDamage){
//            c.setVisible(false);
            c.setRadius(12);
            c.setLayoutY(90);
            c.setStroke(Color.BLACK);
            pb1.getChildren().add(c);
        }
        damage1.setLayoutX(90);
        damage2.setLayoutX(damage1.getLayoutX()+45);
        damage3.setLayoutX(damage2.getLayoutX()+50);
        damage4.setLayoutX(damage3.getLayoutX()+44);
        damage5.setLayoutX(damage4.getLayoutX()+44);
        damage6.setLayoutX(damage5.getLayoutX()+54);
        damage7.setLayoutX(damage6.getLayoutX()+44);
        damage8.setLayoutX(damage7.getLayoutX()+44);
        damage9.setLayoutX(damage8.getLayoutX()+44);
        damage10.setLayoutX(damage9.getLayoutX()+45);
        damage11.setLayoutX(damage10.getLayoutX()+49);
        damage12.setLayoutX(damage11.getLayoutX()+44);

        damage13 = new Circle();
        damage14 = new Circle();
        damage15 = new Circle();
        damage16 = new Circle();
        damage17 = new Circle();
        damage18 = new Circle();
        damage19 = new Circle();
        damage20 = new Circle();
        damage21 = new Circle();
        damage22 = new Circle();
        damage23= new Circle();
        damage24 = new Circle();
        secondDamage.add(damage13);
        secondDamage.add(damage14);
        secondDamage.add(damage15);
        secondDamage.add(damage16);
        secondDamage.add(damage17);
        secondDamage.add(damage18);
        secondDamage.add(damage19);
        secondDamage.add(damage20);
        secondDamage.add(damage21);
        secondDamage.add(damage22);
        secondDamage.add(damage23);
        secondDamage.add(damage24);
        for (Circle c: secondDamage){
//            c.setVisible(false);
            c.setRadius(12);
            c.setLayoutY(90);
            c.setStroke(Color.BLACK);
            pb2.getChildren().add(c);
        }
        damage13.setLayoutX(damage1.getLayoutX());
        damage14.setLayoutX(damage2.getLayoutX());
        damage15.setLayoutX(damage3.getLayoutX());
        damage16.setLayoutX(damage4.getLayoutX());
        damage17.setLayoutX(damage5.getLayoutX());
        damage18.setLayoutX(damage6.getLayoutX());
        damage19.setLayoutX(damage7.getLayoutX());
        damage20.setLayoutX(damage8.getLayoutX());
        damage21.setLayoutX(damage9.getLayoutX());
        damage22.setLayoutX(damage10.getLayoutX());
        damage23.setLayoutX(damage11.getLayoutX());
        damage24.setLayoutX(damage12.getLayoutX());

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
