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
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import model.BloodToken;
import model.DamageTransporter;
import model.Match;
import model.Player;
import model.clientModel.SemplifiedMap;
import view.ClientSingleton;
import view.MapObserver;
import view.MessageListener;
import view.PlayerObserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameFrame implements MapObserver, PlayerObserver, MessageListener {

    final int ammoDimension = 30;
    final int buttonWidth = 100;

    private PbFrame pbFrame;

    private CommandContainer cmdLauncher;

    private InputStream mapPath;
    private InputStream boardPath;

    private Stage stage;
    private StackPane mainPane;
    private Pane gameLog;
    private VBox buttonPane;
    private Pane mapPane;
    private Pane playerBoardPane;
    private TextArea infoGame;

    Label blueAmmmo;
    Label redAmmmo;
    Label yellowAmmmo;

    private Circle mark1;
    private Circle mark2;
    private Circle mark3;
    private Circle mark4;

    private Label markT1;
    private Label markT2;
    private Label markT3;
    private Label markT4;


    private final InputStream pathBackWeapon = getClass().getResourceAsStream("/img/RetroArmi.png");
    private final InputStream pathBackPu = getClass().getResourceAsStream("/img/RetroPu.png");
    private final InputStream pathMartelloIonico = getClass().getResourceAsStream("/img/MartelloIonico.png");

    private List<Color> color;
    private List<String> players;
    private List<Label> marksT;
    private List<Circle> damage;
    private List<Circle> mark;

    public GameFrame(CommandContainer cmd, String board, int map) {
        this.cmdLauncher = cmd;
        this.mapPath = mapParser(map);
        this.color = new ArrayList<>();
        this.color.add(Color.GREY);
        this.color.add(Color.YELLOW);
        this.color.add(Color.BLUEVIOLET);
        this.color.add(Color.DARKGREEN);
        this.color.add(Color.TEAL);
        this.players = new ArrayList<>();
        this.marksT = new ArrayList<>();
        this.players.add("Dozer");
        this.players.add("Distruttore");
        this.players.add("Violetta");
        this.players.add("Sprog");
        this.players.add("Banshee");
        this.boardPath = boardParser(board);
        pbFrame = new PbFrame(this.players);
        stage = new Stage();
        generate();
    }


    public void generate() {
        //TODO da implementare

        stage.setTitle("Adrenalina - on game");
        stage.setResizable(false);

        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        mainPane = new StackPane();
        gameLog = new Pane();
        buttonPane = new VBox();
        mapPane = new Pane();
        playerBoardPane = new Pane();

        infoGame = new TextArea();

        Button walkButton = new Button("Spostati");
        Button pickButton = new Button("Raccogli");
        Button shootButton = new Button("Spara");
        Button powerUpButton = new Button("Usa Powerup");
        Button reloadButton = new Button("Ricarica");
        Button endTurnButton = new Button("Fine Turno");
        Button pointsButton = new Button("Punti");
        Button showWeapon = new Button("Mostra Armi");
        Button showPowerUp = new Button("Mostra PU");
        Button showPlBoard = new Button("Mostra PB");

        infoGame.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        infoGame.setMaxWidth(300);
        infoGame.setMinHeight(500);
        infoGame.setMaxHeight(500);
        infoGame.appendText("Benvenuto in Adrenalina! \n");
        infoGame.setEditable(false);
        infoGame.setBorder(border);

        blueAmmmo = new Label("1");
        redAmmmo = new Label("1");
        yellowAmmmo = new Label("1");

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
        mark1 = new Circle();
        mark2 = new Circle();
        mark3 = new Circle();
        mark4 = new Circle();
        markT1 = new Label("0");
        markT2 = new Label("0");
        markT3 = new Label("0");
        markT4 = new Label("0");
        damage= new LinkedList<>();
        mark= new LinkedList<>();
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
        mark.add(mark1);
        mark.add(mark2);
        mark.add(mark3);
        mark.add(mark4);
        marksT.add(markT1);
        marksT.add(markT2);
        marksT.add(markT3);
        marksT.add(markT4);

        //setting background image
        BackgroundImage myBI= new BackgroundImage(new Image(mapPath, 600,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        BackgroundImage myBIB = new BackgroundImage(new Image(boardPath, 845, 190, false, true),
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

        showPlBoard.setMinWidth(buttonWidth);
        showPlBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pbFrame.show();
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
        weapon1.setLayoutX(860);
        weapon2.setLayoutX(975);
        weapon3.setLayoutX(1090);
        weapon1.setLayoutY(0);
        weapon2.setLayoutY(0);
        weapon3.setLayoutY(0);
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
            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(95);
            c.setStroke(Color.BLACK);
            playerBoardPane.getChildren().add(c);
        }
        damage1.setLayoutX(95);
        damage2.setLayoutX(damage1.getLayoutX()+50);
        damage3.setLayoutX(damage2.getLayoutX()+50);
        damage4.setLayoutX(damage3.getLayoutX()+45);
        damage5.setLayoutX(damage4.getLayoutX()+45);
        damage6.setLayoutX(damage5.getLayoutX()+55);
        damage7.setLayoutX(damage6.getLayoutX()+45);
        damage8.setLayoutX(damage7.getLayoutX()+45);
        damage9.setLayoutX(damage8.getLayoutX()+45);
        damage10.setLayoutX(damage9.getLayoutX()+50);
        damage11.setLayoutX(damage10.getLayoutX()+50);
        damage12.setLayoutX(damage11.getLayoutX()+45);
        for (Circle c : mark){
            c.setVisible(false);
            c.setRadius(15);
            c.setLayoutY(20);
            c.setStroke(Color.BLACK);
            playerBoardPane.getChildren().add(c);
        }
        mark1.setLayoutX(450);
        mark2.setLayoutX(mark1.getLayoutX()+40);
        mark3.setLayoutX(mark2.getLayoutX()+40);
        mark4.setLayoutX(mark3.getLayoutX()+40);
        mark1.setFill(color.get(0));
        mark2.setFill(color.get(1));
        mark3.setFill(color.get(2));
        mark4.setFill(color.get(3));

        //setting label for marks text
        for(Label l : marksT){
            l.setVisible(false);
            l.setMaxHeight(15);
            l.setMaxWidth(15);
            l.setFont(Font.font("System Regular", FontWeight.BOLD, 15));
            l.setLayoutY(mark1.getLayoutY()-10);
        }
        markT1.setBackground(new Background(new BackgroundFill(color.get(0), CornerRadii.EMPTY, Insets.EMPTY)));
        markT2.setBackground(new Background(new BackgroundFill(color.get(1), CornerRadii.EMPTY, Insets.EMPTY)));
        markT3.setBackground(new Background(new BackgroundFill(color.get(2), CornerRadii.EMPTY, Insets.EMPTY)));
        markT4.setBackground(new Background(new BackgroundFill(color.get(3), CornerRadii.EMPTY, Insets.EMPTY)));
        markT1.setLayoutX(mark1.getLayoutX()-5);
        markT2.setLayoutX(mark2.getLayoutX()-5);
        markT3.setLayoutX(mark3.getLayoutX()-5);
        markT4.setLayoutX(mark4.getLayoutX()-5);



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
        buttonPane.getChildren().add(showPlBoard);

        playerBoardPane.getChildren().add(blueAmmmo);
        playerBoardPane.getChildren().add(redAmmmo);
        playerBoardPane.getChildren().add(yellowAmmmo);
        playerBoardPane.getChildren().add(weapon1);
        playerBoardPane.getChildren().add(weapon2);
        playerBoardPane.getChildren().add(weapon3);
        playerBoardPane.getChildren().add(pu1);
        playerBoardPane.getChildren().add(pu2);
        playerBoardPane.getChildren().add(markT1);
        playerBoardPane.getChildren().add(markT2);
        playerBoardPane.getChildren().add(markT3);
        playerBoardPane.getChildren().add(markT4);


        //setting position of pane
        buttonPane.setSpacing(10);
        buttonPane.setTranslateX(1175);
        buttonPane.setTranslateY(10);
        mapPane.setTranslateX(305);
        playerBoardPane.setTranslateY(505);
        playerBoardPane.setTranslateX(60);


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
                return getClass().getResourceAsStream("/img/SmallMapDef.png");
            case 2:
                return getClass().getResourceAsStream("/img/MediumMapDef.png");
            case 3:
                return getClass().getResourceAsStream("/img/LargeMapDef.png");
            case 4:
                return getClass().getResourceAsStream("/img/ExtraLargeMapDef.png");
            default:
                return getClass().getResourceAsStream("/img/SmallMapDef.png");
        }
    }

    private InputStream boardParser(String board) {
        switch (board) {
            case "Distruttore":
                color.remove(1);
                players.remove(1);
                return getClass().getResourceAsStream("/img/DistruttoreBoard.png");
            case "Sprog":
                color.remove(3);
                players.remove(3);
                return getClass().getResourceAsStream("/img/SprogBoard.png");
            case "Dozer":
                color.remove(0);
                players.remove(0);
                return getClass().getResourceAsStream("/img/DozerBoard.png");
            case "Violetta":
                color.remove(2);
                players.remove(2);
                return getClass().getResourceAsStream("/img/ViolettaBoard.png");
            case "Banshee":
                color.remove(4);
                players.remove(4);
                return getClass().getResourceAsStream("/img/BansheeBoard.png");
            default:
                color.remove(1);
                players.remove(1);
                return getClass().getResourceAsStream("/img/DistruttoreBoard.png");
        }
    }


    @Override
    public void onMapChange(SemplifiedMap map) {

    }

    @Override
    public void notify(String message) {
        infoGame.appendText(message+"\n");
    }

    @Override
    public void onHpChange(Player damagePlayer) {
        if (!players.contains(damagePlayer.getName())) {
            for(Circle c :damage){
                c.setVisible(false);
            }
            List<BloodToken> damageToken = damagePlayer.getPlayerBoard().getDamageTokens();
            for (int i = 0; i<damageToken.size();i++){
                int index = players.indexOf(damageToken.get(i).getOwner().getName());
                damage.get(i).setFill(color.get(index));
                damage.get(i).setVisible(true);
            }
        }
    }

    @Override
    public void onMarksChange(Player markedPlayer) {
        if (!players.contains(markedPlayer.getName())){
            for(Circle c :mark){
                c.setVisible(false);
            }
            for (Label l : marksT){
                l.setText("0");
                l.setVisible(false);
            }
            List<BloodToken> marks = markedPlayer.getPlayerBoard().getDamageTokens();
            for (BloodToken b : marks){
                int index = players.indexOf(b.getOwner().getName());
                mark.get(index).setVisible(true);
                marksT.get(index).setVisible(true);
                marksT.get(index).setText(""+Integer.parseInt(marksT.get(index).getText())+1);
            }
        }

    }

    @Override
    public void onAmmoChange(Player p) {
        if (!players.contains(p.getName())) {
            blueAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumBlueAmmo());
            redAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumRedAmmo());
            yellowAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumYellowAmmo());
        }
    }

    @Override
    public void onPowerUpChange(Player p) {

    }

    @Override
    public void onWeaponChange(Player p) {

    }
}
