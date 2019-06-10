package gui;

import controller.CommandContainer;
import controller.CommandLauncher;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.ClientSingleton;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;

public class GameFrame extends Application {

    private CommandContainer cmdLauncher;
    private String mapPath;
    private String boardPath;

    final int ammoDimension = 30;
    final int buttonWidth = 100;

    public void init(CommandContainer cmd, String board, int map){
        this.cmdLauncher = cmd;
        this.mapPath = mapParser(map);
        this.boardPath = boardParser(board);
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        //path of map image
        //TODO da eliminare
        final String pathSmall = "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "img" + File.separatorChar + "SmallMap.png";

        //path of PLayaboard image
        final String pathDistruttoreBoard = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"DistruttoreBoard.png";

        final String pathBackWeapon = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"RetroArmi.png";

        final String pathMartelloIonico = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"MartelloIonico.png";

        //TODO modificare assegnazione path
        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(pathSmall),845,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage myBIB= new BackgroundImage(new Image(new FileInputStream(pathDistruttoreBoard),845,190,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage weaponBack= new BackgroundImage(new Image(new FileInputStream(pathBackWeapon),110,190,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage weap1Img= new BackgroundImage(new Image(new FileInputStream(pathMartelloIonico),845,190,false,true),
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
        blueAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension-6));
        blueAmmmo.setAlignment(Pos.CENTER);
        blueAmmmo.setLayoutX(720);
        blueAmmmo.setLayoutY(20);
        blueAmmmo.setBorder(border);
        blueAmmmo.setVisible(true);
        redAmmmo.setTextFill(Color.WHITE);
        redAmmmo.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        redAmmmo.setMinWidth(ammoDimension);
        redAmmmo.setMinHeight(ammoDimension);
        redAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension-6));
        redAmmmo.setAlignment(Pos.CENTER);
        redAmmmo.setLayoutX(740);
        redAmmmo.setLayoutY(blueAmmmo.getLayoutY()+50);
        redAmmmo.setBorder(border);
        redAmmmo.setVisible(true);
        yellowAmmmo.setTextFill(Color.BLACK);
        yellowAmmmo.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowAmmmo.setMinWidth(ammoDimension);
        yellowAmmmo.setMinHeight(ammoDimension);
        yellowAmmmo.setFont(Font.font("System Regular", FontWeight.BOLD, ammoDimension-6));
        yellowAmmmo.setAlignment(Pos.CENTER);
        yellowAmmmo.setLayoutX(730);
        yellowAmmmo.setLayoutY(redAmmmo.getLayoutY()+50);
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
            }
        });

        showPowerUp.setMinWidth(buttonWidth);

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

        weapon1.setTranslateX(850);
        weapon2.setTranslateX(965);
        weapon3.setTranslateX(1080);
        weapon1.setTranslateY(505);
        weapon2.setTranslateY(505);
        weapon3.setTranslateY(505);



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
        mainPane.getChildren().add(weapon1);
        mainPane.getChildren().add(weapon2);
        mainPane.getChildren().add(weapon3);

        //set scene
        Scene scene = new Scene(mainPane, 1300, 700);
        stage.setScene(scene);
        stage.show();
    }

    private String mapParser(int map) {
        switch (map) {
            case 1:
                return "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar + "img" + File.separatorChar + "SmallMap.png";
            case 2:
                return "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar + "img" + File.separatorChar + "MediumMap.png";
            case 3:
                return "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar + "img" + File.separatorChar + "BigMap.png";
            case 4:
                return "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar + "img" + File.separatorChar + "ExtraLargeMap.png";
            default:
                return "." + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar + "img" + File.separatorChar + "SmallMap.png";
        }
    }

    private String boardParser (String board){
        switch (board){
            case "Distruttore":
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"DistruttoreBoard.png";
            case "Sprog":
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"SprogBoard.png";
            case "Dozer":
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"DozerBoard.png";
            case "Violeta":
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"ViolettaBoard.png";
            case "Banshee":
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"BansheeBoard.png";
            default:
                return "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                        + File.separatorChar +"img"+File.separatorChar+"DistruttoreBoard.png";
        }
    }

}
