package gui;

import controller.CommandContainer;
import controller.commandpack.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import view.ClientSingleton;
import view.MapObserver;
import view.MessageListener;
import view.PlayerObserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class GameFrame implements MapObserver, PlayerObserver, MessageListener {

    final int ammoDimension = 30;
    final int buttonWidth = 100;
    private CommandContainer cmdLauncher;
    private InputStream mapPath;
    private InputStream boardPath;
    private Stage stage;

    final String pathDistruttoreBoard = "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
            + File.separatorChar + "img" + File.separatorChar + "DistruttoreBoard.png";

    final String pathBackWeapon = "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
            + File.separatorChar + "img" + File.separatorChar + "RetroArmi.png";

    final String pathMartelloIonico = "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
            + File.separatorChar + "img" + File.separatorChar + "MartelloIonico.png";

    public GameFrame(CommandContainer cmd, String board, int map) {
        this.cmdLauncher = cmd;
        this.mapPath = mapParser(map);
        this.boardPath = boardParser(board);
        stage = new Stage();
        generate();
    }

    public void generate() {
        //TODO da implementare

        stage.setTitle("Adrenalina - on game");
        stage.setResizable(false);

        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        StackPane mainPane = new StackPane();
        Pane gameLog = new Pane();
        VBox buttonPane = new VBox();
        Pane mapPane = new Pane();
        Pane playerBoardPane = new Pane();

        TextArea infoGame = new TextArea();

        Button walkButton = new Button("Spostati");
        Button pickButton = new Button("Raccogli");
        Button shootButton = new Button("Spara");
        Button powerUpButton = new Button("Usa Powerup");
        Button reloadButton = new Button("Ricarica");
        Button endTurnButton = new Button("Fine Turno");
        Button pointsButton = new Button("Punti");
        Button showWeapon = new Button("Mostra Armi");
        Button showPowerUp = new Button("Mostra PU");

        infoGame.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        infoGame.setMaxWidth(300);
        infoGame.setMinHeight(500);
        infoGame.setMaxHeight(500);
        infoGame.appendText("Benvenuto in Adrenalina! \n");
        infoGame.setEditable(false);
        infoGame.setBorder(border);

        Label blueAmmmo = new Label("1");
        Label redAmmmo = new Label("1");
        Label yellowAmmmo = new Label("1");

        Label weapon1 = new Label();
        Label weapon2 = new Label();
        Label weapon3 = new Label();

        Label pu1 = new Label();
        Label pu2 = new Label();

        Circle damage1 = new Circle();
        Circle damage2 = new Circle();
        Circle damage3 = new Circle();
        Circle damage4 = new Circle();
        Circle damage5 = new Circle();
        Circle damage6 = new Circle();
        Circle damage7 = new Circle();
        Circle damage8 = new Circle();
        Circle damage9 = new Circle();
        Circle damage10 = new Circle();
        Circle damage11= new Circle();
        Circle damage12 = new Circle();
        List<Circle> damage= new LinkedList<>();
        damage.add(damage1);
        damage.add(damage2);
        damage.add(damage3);
        damage.add(damage4);
        damage.add(damage5);
        damage.add(damage6);
        damage.add(damage7);
        damage.add(damage8);
        damage.add(damage9);
        damage.add(damage10);
        damage.add(damage11);
        damage.add(damage12);

        //path of map image
        //TODO da eliminare
        InputStream pathSmall = getClass().getResourceAsStream("/img/SmallMap.png");

        InputStream pathBackPu = getClass().getResourceAsStream("/img/RetroPu.png");

        //TODO modificare assegnazione path
        BackgroundImage myBI= new BackgroundImage(new Image(pathSmall,845,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage myBIB = new BackgroundImage(new Image(pathDistruttoreBoard, 845, 190, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage weaponBack = new BackgroundImage(new Image(pathBackWeapon, 110, 190, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage weap1Img = new BackgroundImage(new Image(pathMartelloIonico, 110, 190, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage puBack = new BackgroundImage(new Image(pathBackPu, 110, 190, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        //map setting
        mapPane.setBackground(new Background(myBI));

        //playerboard setting
        playerBoardPane.setBackground(new Background(myBIB));

        blueAmmmo.setTextFill(Color.WHITE);
        blueAmmmo.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        blueAmmmo.setMinWidth(ammoDimension);
        blueAmmmo.setMinHeight(ammoDimension);
        blueAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
        blueAmmmo.setAlignment(Pos.CENTER);
        blueAmmmo.setLayoutX(720);
        blueAmmmo.setLayoutY(20);
        blueAmmmo.setBorder(border);
        blueAmmmo.setVisible(true);
        redAmmmo.setTextFill(Color.WHITE);
        redAmmmo.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo.setMinWidth(ammoDimension);
        redAmmmo.setMinHeight(ammoDimension);
        redAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
        redAmmmo.setAlignment(Pos.CENTER);
        redAmmmo.setLayoutX(740);
        redAmmmo.setLayoutY(blueAmmmo.getLayoutY() + 50);
        redAmmmo.setBorder(border);
        redAmmmo.setVisible(true);
        yellowAmmmo.setTextFill(Color.BLACK);
        yellowAmmmo.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo.setMinWidth(ammoDimension);
        yellowAmmmo.setMinHeight(ammoDimension);
        yellowAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension - 6));
        yellowAmmmo.setAlignment(Pos.CENTER);
        yellowAmmmo.setLayoutX(730);
        yellowAmmmo.setLayoutY(redAmmmo.getLayoutY() + 50);
        yellowAmmmo.setBorder(border);
        yellowAmmmo.setVisible(true);


        //button setting
        walkButton.setMinWidth(buttonWidth);
        walkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskWalkCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        pickButton.setMinWidth(buttonWidth);
        pickButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskPickCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        shootButton.setMinWidth(buttonWidth);
        shootButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskShootCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        powerUpButton.setMinWidth(buttonWidth);
        powerUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskUsePowerUpCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        reloadButton.setMinWidth(buttonWidth);
        reloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskReloadCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        endTurnButton.setMinWidth(buttonWidth);
        endTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskEndTurnCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        pointsButton.setMinWidth(buttonWidth);
        pointsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new AskPointsCommand(ClientSingleton.getInstance().getToken()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        showWeapon.setMinWidth(buttonWidth);
        showWeapon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                weapon1.setVisible(true);
                weapon2.setVisible(true);
                weapon3.setVisible(true);
                pu1.setVisible(false);
                pu2.setVisible(false);
            }
        });

        showPowerUp.setMinWidth(buttonWidth);
        showPowerUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                weapon1.setVisible(false);
                weapon2.setVisible(false);
                weapon3.setVisible(false);
                pu1.setVisible(true);
                pu2.setVisible(true);
            }
        });

        //setting gamelog
        gameLog.getChildren().add(infoGame);

        //setting card
        weapon1.setVisible(false);
        weapon2.setVisible(false);
        weapon3.setVisible(false);
        weapon1.setBorder(border);
        weapon2.setBorder(border);
        weapon3.setBorder(border);
        weapon1.setBackground(new Background(weap1Img));
        weapon2.setBackground(new Background(weaponBack));
        weapon3.setBackground(new Background(weaponBack));
        weapon1.setMinWidth(110);
        weapon1.setMinHeight(190);
        weapon2.setMinWidth(110);
        weapon2.setMinHeight(190);
        weapon3.setMinWidth(110);
        weapon3.setMinHeight(190);
        weapon1.setLayoutX(850);
        weapon2.setLayoutX(965);
        weapon3.setLayoutX(1080);
        weapon1.setLayoutY(5);
        weapon2.setLayoutY(5);
        weapon3.setLayoutY(5);
        pu1.setVisible(false);
        pu2.setVisible(false);
        pu1.setBorder(border);
        pu2.setBorder(border);
        pu1.setMinWidth(110);
        pu1.setMinHeight(190);
        pu2.setMinWidth(110);
        pu2.setMinHeight(190);
        pu1.setLayoutX(860);
        pu2.setLayoutX(975);
        pu1.setLayoutY(0);
        pu2.setLayoutY(0);
        pu1.setBackground(new Background(puBack));
        pu2.setBackground(new Background(puBack));

        //set damage
        for (Circle c : damage){
            //c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(95);
            playerBoardPane.getChildren().add(c);
        }
        damage1.setLayoutX(95);
        damage2.setLayoutX(damage1.getLayoutX()+50);
        damage3.setLayoutX(damage2.getLayoutX()+50);
        damage4.setLayoutX(damage3.getLayoutX()+45);
        damage5.setLayoutX(damage4.getLayoutX()+45);
        damage6.setLayoutX(damage5.getLayoutX()+50);
        damage7.setLayoutX(damage6.getLayoutX()+50);
        damage8.setLayoutX(damage7.getLayoutX()+50);
        damage9.setLayoutX(damage8.getLayoutX()+50);
        damage10.setLayoutX(damage9.getLayoutX()+50);
        damage11.setLayoutX(damage10.getLayoutX()+45);
        damage12.setLayoutX(damage11.getLayoutX()+45);



        //setting buttonpane
        buttonPane.getChildren().add(walkButton);
        buttonPane.getChildren().add(pickButton);
        buttonPane.getChildren().add(shootButton);
        buttonPane.getChildren().add(powerUpButton);
        buttonPane.getChildren().add(reloadButton);
        buttonPane.getChildren().add(endTurnButton);
        buttonPane.getChildren().add(pointsButton);
        buttonPane.getChildren().add(showWeapon);
        buttonPane.getChildren().add(showPowerUp);

        playerBoardPane.getChildren().add(blueAmmmo);
        playerBoardPane.getChildren().add(redAmmmo);
        playerBoardPane.getChildren().add(yellowAmmmo);
        playerBoardPane.getChildren().add(weapon1);
        playerBoardPane.getChildren().add(weapon2);
        playerBoardPane.getChildren().add(weapon3);
        playerBoardPane.getChildren().add(pu1);
        playerBoardPane.getChildren().add(pu2);


        //setting position of pane
        buttonPane.setSpacing(10);
        buttonPane.setTranslateX(1175);
        buttonPane.setTranslateY(10);
        mapPane.setTranslateX(305);
        playerBoardPane.setTranslateY(505);
        playerBoardPane.setTranslateX(25);


        //setting mainpane
        mainPane.getChildren().add(gameLog);
        mainPane.getChildren().add(mapPane);
        mainPane.getChildren().add(playerBoardPane);
        mainPane.getChildren().add(buttonPane);

        //set scene
        Scene scene = new Scene(mainPane, 1300, 700);
        stage.setScene(scene);
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

    private InputStream mapParser(int map) {
        switch (map) {
            case 1:
                return getClass().getResourceAsStream("img" + File.separatorChar + "SmallMap.png");
            case 2:
                return getClass().getResourceAsStream("img" + File.separatorChar + "MediumMap.png");
            case 3:
                return getClass().getResourceAsStream("img" + File.separatorChar + "BigMap.png");
            case 4:
                return getClass().getResourceAsStream("img" + File.separatorChar + "ExtraLargeMap.png");
            default:
                return getClass().getResourceAsStream("img" + File.separatorChar + "SmallMap.png");
        }
    }

    private InputStream boardParser(String board) {
        switch (board) {
            case "Distruttore":
                return getClass().getResourceAsStream("img" + File.separatorChar + "DistruttoreBoard.png");
            case "Sprog":
                return getClass().getResourceAsStream("img" + File.separatorChar + "SprogBoard.png");
            case "Dozer":
                return getClass().getResourceAsStream("img" + File.separatorChar + "DozerBoard.png");
            case "Violeta":
                return getClass().getResourceAsStream("img" + File.separatorChar + "ViolettaBoard.png");
            case "Banshee":
                return getClass().getResourceAsStream("img" + File.separatorChar + "BansheeBoard.png");
            default:
                return getClass().getResourceAsStream("img" + File.separatorChar + "DistruttoreBoard.png");
        }
    }

    @Override
    public void onMapChange(Match m) {

    }

    @Override
    public void notify(String message) {

    }

    @Override
    public void onHpChange(Player damagePlayer) {

    }

    @Override
    public void onMarksChange(Player markedPlayer) {

    }

    @Override
    public void onAmmoChange(Player p) {

    }

    @Override
    public void onPowerUpChange(Player p) {

    }
}
