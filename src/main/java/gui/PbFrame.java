package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedPlayer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PbFrame {

    final int ammoDimension = 30;
    final Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


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
    private Label blueAmmmo1;
    private Label redAmmmo1;
    private Label yellowAmmmo1;
    private List<Circle> firstDamage;
    private List<Circle> firstMark;
    private List<Label> firstMarkT;
    private List<Label> firstAmmo;
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
    private Label markT21;
    private Label markT22;
    private Label markT23;
    private Label markT24;
    private Label blueAmmmo2;
    private Label redAmmmo2;
    private Label yellowAmmmo2;
    private List<Circle> secondDamage;
    private List<Circle> secondMark;
    private List<Label> secondMarkT;
    private List<Label> secondAmmo;
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
    private Circle mark31;
    private Circle mark32;
    private Circle mark33;
    private Circle mark34;
    private Label markT31;
    private Label markT32;
    private Label markT33;
    private Label markT34;
    private Label blueAmmmo3;
    private Label redAmmmo3;
    private Label yellowAmmmo3;
    private List<Circle> thirdDamage;
    private List<Circle> thirdMark;
    private List<Label> thirdMarkT;
    private List<Label> thirdAmmo;
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
    private Circle mark41;
    private Circle mark42;
    private Circle mark43;
    private Circle mark44;
    private Label markT41;
    private Label markT42;
    private Label markT43;
    private Label markT44;
    private Label blueAmmmo4;
    private Label redAmmmo4;
    private Label yellowAmmmo4;
    private List<Circle> fourthDamage;
    private List<Circle> fourthMark;
    private List<Label> fourthMarkT;
    private List<Label> fourthAmmo;
    private List<Color> fourthColor;

    public PbFrame(List<String> players, List<Color> gameColor) {
        this.stage = new Stage();
        this.players = players;
        this.gameColor = gameColor;
        fillColorList();
        generate();
    }

    private void generate(){
        stage.setTitle("Adrenalina - PlayerBoard avversari");
        stage.setResizable(false);

        generateFirst();
        generateSecond();
        generateThird();
        generateFourth();

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

    private InputStream boardParser(String board) {
        switch (board) {
            case "distruttore":
                return getClass().getResourceAsStream("/img/DistruttoreBoard.png");
            case "sprog":
                return getClass().getResourceAsStream("/img/SprogBoard.png");
            case "dozer":
                return getClass().getResourceAsStream("/img/DozerBoard.png");
            case "violetta":
                return getClass().getResourceAsStream("/img/ViolettaBoard.png");
            case "banshee":
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

    private void generateFirst(){
        firstDamage = new ArrayList<>();
        firstMark = new ArrayList<>();
        firstMarkT = new ArrayList<>();
        firstAmmo = new ArrayList<>();
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
            c.setVisible(false);
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
            c.setVisible(false);
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

        markT11 = new Label("0");
        markT12 = new Label("0");
        markT13 = new Label("0");
        markT14 = new Label("0");
        firstMarkT.add(markT11);
        firstMarkT.add(markT12);
        firstMarkT.add(markT13);
        firstMarkT.add(markT14);
        //setting label for marks text
        for(Label l : firstMarkT){
            l.setVisible(false);
            l.setMaxHeight(15);
            l.setMaxWidth(15);
            l.setFont(Font.font("System Regular", FontWeight.BOLD, 15));
            l.setLayoutY(mark11.getLayoutY()-10);
            pb1.getChildren().add(l);
        }
        markT11.setBackground(new Background(new BackgroundFill(firstColor.get(0), CornerRadii.EMPTY, Insets.EMPTY)));
        markT12.setBackground(new Background(new BackgroundFill(firstColor.get(1), CornerRadii.EMPTY, Insets.EMPTY)));
        markT13.setBackground(new Background(new BackgroundFill(firstColor.get(2), CornerRadii.EMPTY, Insets.EMPTY)));
        markT14.setBackground(new Background(new BackgroundFill(firstColor.get(3), CornerRadii.EMPTY, Insets.EMPTY)));
        markT11.setLayoutX(mark11.getLayoutX()-5);
        markT12.setLayoutX(mark12.getLayoutX()-5);
        markT13.setLayoutX(mark13.getLayoutX()-5);
        markT14.setLayoutX(mark14.getLayoutX()-5);

        blueAmmmo1 = new Label("1");
        redAmmmo1 = new Label("1");
        yellowAmmmo1 = new Label("1");
        firstAmmo.add(blueAmmmo1);
        firstAmmo.add(redAmmmo1);
        firstAmmo.add(yellowAmmmo1);
        for (Label a : firstAmmo){
            a.setVisible(true);
            a.setMinWidth(ammoDimension);
            a.setMinHeight(ammoDimension);
            a.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
            a.setAlignment(Pos.CENTER);
            a.setBorder(border);
            pb1.getChildren().add(a);
        }
        blueAmmmo1.setTextFill(Color.WHITE);
        blueAmmmo1.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        blueAmmmo1.setLayoutX(720);
        blueAmmmo1.setLayoutY(20);
        redAmmmo1.setTextFill(Color.WHITE);
        redAmmmo1.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo1.setLayoutX(blueAmmmo1.getLayoutX()+20);
        redAmmmo1.setLayoutY(blueAmmmo1.getLayoutY() + 50);
        yellowAmmmo1.setTextFill(Color.BLACK);
        yellowAmmmo1.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo1.setLayoutX(blueAmmmo1.getLayoutX()+10);
        yellowAmmmo1.setLayoutY(redAmmmo1.getLayoutY() + 50);

        pb1.setTranslateY(2);
        pb1.setTranslateX(17);
        pb1.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(0)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    private void generateSecond(){
        secondDamage = new ArrayList<>();
        secondMark = new ArrayList<>();
        secondMarkT = new ArrayList<>();
        secondAmmo = new ArrayList<>();
        pb2 = new Pane();

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
            c.setVisible(false);
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
        mark21 = new Circle();
        mark22 = new Circle();
        mark23 = new Circle();
        mark24 = new Circle();
        secondMark.add(mark21);
        secondMark.add(mark22);
        secondMark.add(mark23);
        secondMark.add(mark24);
        for (Circle c : secondMark){
            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(20);
            c.setStroke(Color.BLACK);
            pb2.getChildren().add(c);
        }
        mark21.setLayoutX(mark11.getLayoutX());
        mark22.setLayoutX(mark12.getLayoutX());
        mark23.setLayoutX(mark13.getLayoutX());
        mark24.setLayoutX(mark14.getLayoutX());
        mark21.setFill(secondColor.get(0));
        mark22.setFill(secondColor.get(1));
        mark23.setFill(secondColor.get(2));
        mark24.setFill(secondColor.get(3));

        markT21 = new Label("0");
        markT22 = new Label("0");
        markT23 = new Label("0");
        markT24 = new Label("0");
        secondMarkT.add(markT21);
        secondMarkT.add(markT22);
        secondMarkT.add(markT23);
        secondMarkT.add(markT24);
        //setting label for marks text
        for(Label l : secondMarkT){
            l.setVisible(false);
            l.setMaxHeight(15);
            l.setMaxWidth(15);
            l.setFont(Font.font("System Regular", FontWeight.BOLD, 15));
            l.setLayoutY(mark21.getLayoutY()-10);
            pb2.getChildren().add(l);
        }
        markT21.setBackground(new Background(new BackgroundFill(secondColor.get(0), CornerRadii.EMPTY, Insets.EMPTY)));
        markT22.setBackground(new Background(new BackgroundFill(secondColor.get(1), CornerRadii.EMPTY, Insets.EMPTY)));
        markT23.setBackground(new Background(new BackgroundFill(secondColor.get(2), CornerRadii.EMPTY, Insets.EMPTY)));
        markT24.setBackground(new Background(new BackgroundFill(secondColor.get(3), CornerRadii.EMPTY, Insets.EMPTY)));
        markT21.setLayoutX(mark21.getLayoutX()-5);
        markT22.setLayoutX(mark22.getLayoutX()-5);
        markT23.setLayoutX(mark23.getLayoutX()-5);
        markT24.setLayoutX(mark24.getLayoutX()-5);

        blueAmmmo2 = new Label("1");
        redAmmmo2 = new Label("1");
        yellowAmmmo2 = new Label("1");
        secondAmmo.add(blueAmmmo2);
        secondAmmo.add(redAmmmo2);
        secondAmmo.add(yellowAmmmo2);
        for (Label a : secondAmmo){
            a.setVisible(true);
            a.setMinWidth(ammoDimension);
            a.setMinHeight(ammoDimension);
            a.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
            a.setAlignment(Pos.CENTER);
            a.setBorder(border);
            pb2.getChildren().add(a);
        }
        blueAmmmo2.setTextFill(Color.WHITE);
        blueAmmmo2.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        blueAmmmo2.setLayoutX(720);
        blueAmmmo2.setLayoutY(20);
        redAmmmo2.setTextFill(Color.WHITE);
        redAmmmo2.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo2.setLayoutX(blueAmmmo2.getLayoutX()+20);
        redAmmmo2.setLayoutY(blueAmmmo2.getLayoutY() + 50);
        yellowAmmmo2.setTextFill(Color.BLACK);
        yellowAmmmo2.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo2.setLayoutX(blueAmmmo2.getLayoutX()+10);
        yellowAmmmo2.setLayoutY(redAmmmo2.getLayoutY() + 50);

        pb2.setTranslateX(pb1.getTranslateX());
        pb2.setTranslateY(pb1.getTranslateY()+175);
        pb2.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(1)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    private void generateThird(){
        thirdDamage = new ArrayList<>();
        thirdMark = new ArrayList<>();
        thirdMarkT = new ArrayList<>();
        thirdAmmo = new ArrayList<>();
        pb3 = new Pane();

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
            c.setVisible(false);
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

        mark31 = new Circle();
        mark32 = new Circle();
        mark33 = new Circle();
        mark34 = new Circle();
        thirdMark.add(mark31);
        thirdMark.add(mark32);
        thirdMark.add(mark33);
        thirdMark.add(mark34);
        for (Circle c : thirdMark){
            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(20);
            c.setStroke(Color.BLACK);
            pb3.getChildren().add(c);
        }
        mark31.setLayoutX(mark11.getLayoutX());
        mark32.setLayoutX(mark12.getLayoutX());
        mark33.setLayoutX(mark13.getLayoutX());
        mark34.setLayoutX(mark14.getLayoutX());
        mark31.setFill(thirdColor.get(0));
        mark32.setFill(thirdColor.get(1));
        mark33.setFill(thirdColor.get(2));
        mark34.setFill(thirdColor.get(3));

        markT31 = new Label("0");
        markT32 = new Label("0");
        markT33 = new Label("0");
        markT34 = new Label("0");
        thirdMarkT.add(markT31);
        thirdMarkT.add(markT32);
        thirdMarkT.add(markT33);
        thirdMarkT.add(markT34);
        //setting label for marks text
        for(Label l : thirdMarkT){
            l.setVisible(false);
            l.setMaxHeight(15);
            l.setMaxWidth(15);
            l.setFont(Font.font("System Regular", FontWeight.BOLD, 15));
            l.setLayoutY(mark31.getLayoutY()-10);
            pb3.getChildren().add(l);
        }
        markT31.setBackground(new Background(new BackgroundFill(thirdColor.get(0), CornerRadii.EMPTY, Insets.EMPTY)));
        markT32.setBackground(new Background(new BackgroundFill(thirdColor.get(1), CornerRadii.EMPTY, Insets.EMPTY)));
        markT33.setBackground(new Background(new BackgroundFill(thirdColor.get(2), CornerRadii.EMPTY, Insets.EMPTY)));
        markT34.setBackground(new Background(new BackgroundFill(thirdColor.get(3), CornerRadii.EMPTY, Insets.EMPTY)));
        markT31.setLayoutX(mark31.getLayoutX()-5);
        markT32.setLayoutX(mark32.getLayoutX()-5);
        markT33.setLayoutX(mark33.getLayoutX()-5);
        markT34.setLayoutX(mark34.getLayoutX()-5);

        blueAmmmo3 = new Label("1");
        redAmmmo3 = new Label("1");
        yellowAmmmo3 = new Label("1");
        thirdAmmo.add(blueAmmmo3);
        thirdAmmo.add(redAmmmo3);
        thirdAmmo.add(yellowAmmmo3);
        for (Label a : thirdAmmo){
            a.setVisible(true);
            a.setMinWidth(ammoDimension);
            a.setMinHeight(ammoDimension);
            a.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
            a.setAlignment(Pos.CENTER);
            a.setBorder(border);
            pb3.getChildren().add(a);
        }
        blueAmmmo3.setTextFill(Color.WHITE);
        blueAmmmo3.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        blueAmmmo3.setLayoutX(720);
        blueAmmmo3.setLayoutY(20);
        redAmmmo3.setTextFill(Color.WHITE);
        redAmmmo3.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo3.setLayoutX(blueAmmmo3.getLayoutX()+20);
        redAmmmo3.setLayoutY(blueAmmmo3.getLayoutY() + 50);
        yellowAmmmo3.setTextFill(Color.BLACK);
        yellowAmmmo3.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo3.setLayoutX(blueAmmmo3.getLayoutX()+10);
        yellowAmmmo3.setLayoutY(redAmmmo3.getLayoutY() + 50);

        pb3.setTranslateX(pb1.getTranslateX());
        pb3.setTranslateY(pb2.getTranslateY()+175);
        pb3.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(2)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    private void generateFourth(){
        fourthDamage = new ArrayList<>();
        fourthMark = new ArrayList<>();
        fourthMarkT = new ArrayList<>();
        fourthAmmo = new ArrayList<>();
        pb4 = new Pane();

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
            c.setVisible(false);
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

        mark41 = new Circle();
        mark42 = new Circle();
        mark43 = new Circle();
        mark44 = new Circle();
        fourthMark.add(mark41);
        fourthMark.add(mark42);
        fourthMark.add(mark43);
        fourthMark.add(mark44);
        for (Circle c : fourthMark){
            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(20);
            c.setStroke(Color.BLACK);
            pb4.getChildren().add(c);
        }
        mark41.setLayoutX(mark11.getLayoutX());
        mark42.setLayoutX(mark12.getLayoutX());
        mark43.setLayoutX(mark13.getLayoutX());
        mark44.setLayoutX(mark14.getLayoutX());
        mark41.setFill(fourthColor.get(0));
        mark42.setFill(fourthColor.get(1));
        mark43.setFill(fourthColor.get(2));
        mark44.setFill(fourthColor.get(3));

        markT41 = new Label("0");
        markT42 = new Label("0");
        markT43 = new Label("0");
        markT44 = new Label("0");
        fourthMarkT.add(markT41);
        fourthMarkT.add(markT42);
        fourthMarkT.add(markT43);
        fourthMarkT.add(markT44);
        //setting label for marks text
        for(Label l : fourthMarkT){
            l.setVisible(false);
            l.setMaxHeight(15);
            l.setMaxWidth(15);
            l.setFont(Font.font("System Regular", FontWeight.BOLD, 15));
            l.setLayoutY(mark41.getLayoutY()-10);
            pb4.getChildren().add(l);
        }
        markT41.setBackground(new Background(new BackgroundFill(fourthColor.get(0), CornerRadii.EMPTY, Insets.EMPTY)));
        markT42.setBackground(new Background(new BackgroundFill(fourthColor.get(1), CornerRadii.EMPTY, Insets.EMPTY)));
        markT43.setBackground(new Background(new BackgroundFill(fourthColor.get(2), CornerRadii.EMPTY, Insets.EMPTY)));
        markT44.setBackground(new Background(new BackgroundFill(fourthColor.get(3), CornerRadii.EMPTY, Insets.EMPTY)));
        markT41.setLayoutX(mark41.getLayoutX()-5);
        markT42.setLayoutX(mark42.getLayoutX()-5);
        markT43.setLayoutX(mark43.getLayoutX()-5);
        markT44.setLayoutX(mark44.getLayoutX()-5);

        blueAmmmo4 = new Label("1");
        redAmmmo4 = new Label("1");
        yellowAmmmo4 = new Label("1");
        fourthAmmo.add(blueAmmmo4);
        fourthAmmo.add(redAmmmo4);
        fourthAmmo.add(yellowAmmmo4);
        for (Label a : fourthAmmo){
            a.setVisible(true);
            a.setMinWidth(ammoDimension);
            a.setMinHeight(ammoDimension);
            a.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
            a.setAlignment(Pos.CENTER);
            a.setBorder(border);
            pb4.getChildren().add(a);
        }
        blueAmmmo4.setTextFill(Color.WHITE);
        blueAmmmo4.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        blueAmmmo4.setLayoutX(720);
        blueAmmmo4.setLayoutY(20);
        redAmmmo4.setTextFill(Color.WHITE);
        redAmmmo4.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo4.setLayoutX(blueAmmmo4.getLayoutX()+20);
        redAmmmo4.setLayoutY(blueAmmmo4.getLayoutY() + 50);
        yellowAmmmo4.setTextFill(Color.BLACK);
        yellowAmmmo4.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo4.setLayoutX(blueAmmmo4.getLayoutX()+10);
        yellowAmmmo4.setLayoutY(redAmmmo4.getLayoutY() + 50);

        pb4.setTranslateX(pb1.getTranslateX());
        pb4.setTranslateY(pb3.getTranslateY()+175);
        pb4.setBackground(new Background(new BackgroundImage(new Image(boardParser(players.get(3)), 815,170,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    public void updateDamage(SemplifiedPlayer p){
        List<Circle> damagedPlayerDamage = damageParser(p.getName());
        for (Circle c : damagedPlayerDamage){
            c.setVisible(false);
        }
        List<SemplifiedBloodToken> damageToken = p.getPlayerBoard().getDamageTokens();
        for (int i = 0; i<damageToken.size();i++){
            int index = players.indexOf(damageToken.get(i).getOwner().getName());
            damagedPlayerDamage.get(i).setVisible(true);
            damagedPlayerDamage.get(i).setFill(gameColor.get(index));
        }

    }

    public void updateMarks(SemplifiedPlayer p){
        List<Circle> markedPlayerMarks = markParser(p.getName());
        List<Label> markedPlayerMarksText = markTextParser(p.getName());
        for (Circle c : markedPlayerMarks){
            c.setVisible(false);
        }
        for (Label l : markTextParser(p.getName())){
            l.setVisible(false);
            l.setText("0");
        }
        List<SemplifiedBloodToken> marks = p.getPlayerBoard().getMarksTokens();
        for (SemplifiedBloodToken b : marks){
            int index = players.indexOf(b.getOwner().getName());
            markedPlayerMarksText.get(index).setText(""+Integer.parseInt(markedPlayerMarksText.get(index).getText())+1);
            markedPlayerMarksText.get(index).setVisible(true);
            markedPlayerMarks.get(index).setVisible(true);
        }
    }

    public void updateAmmo(SemplifiedPlayer p){
        List<Label> ammo = ammoParser(p.getName());
        ammo.get(0).setText(""+p.getPlayerBoard().getLoader().getNumBlueAmmo());
        ammo.get(1).setText(""+p.getPlayerBoard().getLoader().getNumRedAmmo());
        ammo.get(2).setText(""+p.getPlayerBoard().getLoader().getNumYellowAmmo());
    }

    private List<Label>ammoParser(String p){
        int pos = players.indexOf(p);
        switch (pos){
            case 0:
                return firstAmmo;
            case 1:
                return secondAmmo;
            case 2:
                return thirdAmmo;
            case 3:
                return fourthAmmo;
            default:
                throw new RuntimeException("name passed is not for a player");
        }
    }

    private List<Circle>markParser(String p){
        int pos = players.indexOf(p);
        switch (pos){
            case 0:
                return firstMark;
            case 1:
                return secondMark;
            case 2:
                return thirdMark;
            case 3:
                return fourthMark;
            default:
                throw new RuntimeException("name passed is not for a player");
        }
    }

    private List<Label>markTextParser(String p){
        int pos = players.indexOf(p);
        switch (pos){
            case 0:
                return firstMarkT;
            case 1:
                return secondMarkT;
            case 2:
                return thirdMarkT;
            case 3:
                return fourthMarkT;
            default:
                throw new RuntimeException("name passed is not for a player");
        }
    }

    private List<Circle>damageParser(String p){
        int pos = players.indexOf(p);
        switch (pos){
            case 0:
                return firstDamage;
            case 1:
                return secondDamage;
            case 2:
                return thirdDamage;
            case 3:
                return fourthDamage;
            default:
                throw new RuntimeException("name passed is not for a player");
        }
    }
}
