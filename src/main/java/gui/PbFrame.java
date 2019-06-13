package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PbFrame {

    private Stage stage;
    private List<String> players;
    private List<Color> gameColor;

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
    private Circle mark11;
    private Circle mark12;
    private Circle mark13;
    private Circle mark14;
    private Label markT11;
    private Label markT12;
    private Label markT13;
    private Label markT14;
    private List<Circle> firstDamage;
    private List<Circle> firstMark;
    private List<Label> firstMarkT;
    private List<Color> firstColor;

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
    private Circle mark21;
    private Circle mark22;
    private Circle mark23;
    private Circle mark24;
    private List<Circle> secondDamage;
    private List<Circle> secondMark;
    private List<Label> secondMarkT;
    private List<Color> secondColor;

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
    private List<Color> thirdColor;

    private Circle damage37;
    private Circle damage38;
    private Circle damage39;
    private Circle damage40;
    private Circle damage41;
    private Circle damage42;
    private Circle damage43;
    private Circle damage44;
    private Circle damage45;
    private Circle damage46;
    private Circle damage47;
    private Circle damage48;
    private List<Circle> fourthDamage;
    private List<Color> fourthColor;

    public PbFrame(List<String> players, List<Color> gameColor) {
        this.stage = new Stage();
        this.players = players;
        this.gameColor = gameColor;
        this.secondDamage = new ArrayList<>();
        this.thirdDamage = new ArrayList<>();
        this.fourthDamage = new ArrayList<>();

        fillColorList();
        generate();
    }

    private void generate(){
        stage.setTitle("Adrenalina - PlayerBoard avversari");
        stage.setResizable(false);

        pb2 = new Pane();
        pb3 = new Pane();
        pb4 = new Pane();

        generateFirst();

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

        damage25 = new Circle();
        damage26 = new Circle();
        damage27 = new Circle();
        damage28 = new Circle();
        damage29 = new Circle();
        damage30 = new Circle();
        damage31 = new Circle();
        damage32 = new Circle();
        damage33 = new Circle();
        damage34 = new Circle();
        damage35= new Circle();
        damage36 = new Circle();
        thirdDamage.add(damage25);
        thirdDamage.add(damage26);
        thirdDamage.add(damage27);
        thirdDamage.add(damage28);
        thirdDamage.add(damage29);
        thirdDamage.add(damage30);
        thirdDamage.add(damage31);
        thirdDamage.add(damage32);
        thirdDamage.add(damage33);
        thirdDamage.add(damage34);
        thirdDamage.add(damage35);
        thirdDamage.add(damage36);
        for (Circle c: thirdDamage){
//            c.setVisible(false);
            c.setRadius(12);
            c.setLayoutY(90);
            c.setStroke(Color.BLACK);
            pb3.getChildren().add(c);
        }
        damage25.setLayoutX(damage1.getLayoutX());
        damage26.setLayoutX(damage2.getLayoutX());
        damage27.setLayoutX(damage3.getLayoutX());
        damage28.setLayoutX(damage4.getLayoutX());
        damage29.setLayoutX(damage5.getLayoutX());
        damage30.setLayoutX(damage6.getLayoutX());
        damage31.setLayoutX(damage7.getLayoutX());
        damage32.setLayoutX(damage8.getLayoutX());
        damage33.setLayoutX(damage9.getLayoutX());
        damage34.setLayoutX(damage10.getLayoutX());
        damage35.setLayoutX(damage11.getLayoutX());
        damage36.setLayoutX(damage12.getLayoutX());

        damage37 = new Circle();
        damage38 = new Circle();
        damage39 = new Circle();
        damage40 = new Circle();
        damage41 = new Circle();
        damage42 = new Circle();
        damage43 = new Circle();
        damage44 = new Circle();
        damage45 = new Circle();
        damage46 = new Circle();
        damage47= new Circle();
        damage48 = new Circle();
        fourthDamage.add(damage37);
        fourthDamage.add(damage38);
        fourthDamage.add(damage39);
        fourthDamage.add(damage40);
        fourthDamage.add(damage41);
        fourthDamage.add(damage42);
        fourthDamage.add(damage43);
        fourthDamage.add(damage44);
        fourthDamage.add(damage45);
        fourthDamage.add(damage46);
        fourthDamage.add(damage47);
        fourthDamage.add(damage48);
        for (Circle c: fourthDamage){
//            c.setVisible(false);
            c.setRadius(12);
            c.setLayoutY(90);
            c.setStroke(Color.BLACK);
            pb4.getChildren().add(c);
        }
        damage37.setLayoutX(damage1.getLayoutX());
        damage38.setLayoutX(damage2.getLayoutX());
        damage39.setLayoutX(damage3.getLayoutX());
        damage40.setLayoutX(damage4.getLayoutX());
        damage41.setLayoutX(damage5.getLayoutX());
        damage42.setLayoutX(damage6.getLayoutX());
        damage43.setLayoutX(damage7.getLayoutX());
        damage44.setLayoutX(damage8.getLayoutX());
        damage45.setLayoutX(damage9.getLayoutX());
        damage46.setLayoutX(damage10.getLayoutX());
        damage47.setLayoutX(damage11.getLayoutX());
        damage48.setLayoutX(damage12.getLayoutX());

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
        //TODO update dati board
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

    private void fillColorList(){
        this.firstColor = new LinkedList<>();
        this.secondColor = new LinkedList<>();
        this.thirdColor = new LinkedList<>();
        this.fourthColor = new LinkedList<>();
        firstColor.add(Color.GREY);
        firstColor.add(Color.YELLOW);
        firstColor.add(Color.BLUEVIOLET);
        firstColor.add(Color.DARKGREEN);
        firstColor.add(Color.TEAL);
        secondColor.add(Color.GREY);
        secondColor.add(Color.YELLOW);
        secondColor.add(Color.BLUEVIOLET);
        secondColor.add(Color.DARKGREEN);
        secondColor.add(Color.TEAL);
        thirdColor.add(Color.GREY);
        thirdColor.add(Color.YELLOW);
        thirdColor.add(Color.BLUEVIOLET);
        thirdColor.add(Color.DARKGREEN);
        thirdColor.add(Color.TEAL);
        fourthColor.add(Color.GREY);
        fourthColor.add(Color.YELLOW);
        fourthColor.add(Color.BLUEVIOLET);
        fourthColor.add(Color.DARKGREEN);
        fourthColor.add(Color.TEAL);
        firstColor.remove(gameColor.get(0));
        secondColor.remove(gameColor.get(1));
        thirdColor.remove(gameColor.get(2));
        fourthColor.remove(gameColor.get(3));
    }

    public void generateFirst(){
        firstDamage = new ArrayList<>();
        firstMark = new ArrayList<>();
        firstMarkT = new ArrayList<>();
        pb1 = new Pane();

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

        mark11 = new Circle();
        mark12 = new Circle();
        mark13 = new Circle();
        mark14 = new Circle();
        firstMark.add(mark11);
        firstMark.add(mark12);
        firstMark.add(mark13);
        firstMark.add(mark14);
        for (Circle c : firstMark){
//            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(20);
            c.setStroke(Color.BLACK);
            pb1.getChildren().add(c);
        }
        mark11.setLayoutX(450);
        mark12.setLayoutX(mark11.getLayoutX()+40);
        mark13.setLayoutX(mark12.getLayoutX()+40);
        mark14.setLayoutX(mark13.getLayoutX()+40);
        mark11.setFill(firstColor.get(0));
        mark12.setFill(firstColor.get(1));
        mark13.setFill(firstColor.get(2));
        mark14.setFill(firstColor.get(3));
    }
}
