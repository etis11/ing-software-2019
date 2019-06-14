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
import model.*;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedWeaponCard;
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
    private final InputStream pathBackWeapon = getClass().getResourceAsStream("/img/RetroArmi.png");
    private final InputStream pathBackPu = getClass().getResourceAsStream("/img/RetroPu.png");
    final BackgroundImage weaponBack = new BackgroundImage(new Image(pathBackWeapon, 110, 190, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    final BackgroundImage puBack = new BackgroundImage(new Image(pathBackPu, 110, 190, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    private PbFrame pbFrame;
    private WeaponFrame weaponFrame;

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

    private Label weapon1;
    private Label weapon2;
    private Label weapon3;

    private Label pu1;
    private Label pu2;

    private Label blueAmmmo;
    private Label redAmmmo;
    private Label yellowAmmmo;

    private Circle mark1;
    private Circle mark2;
    private Circle mark3;
    private Circle mark4;

    private Label markT1;
    private Label markT2;
    private Label markT3;
    private Label markT4;

    private Circle distruttoreToken;
    private Circle dozerToken;
    private Circle sprogToken;
    private Circle violettaToken;
    private Circle bansheeToken;

    private List<Color> color;
    private List<String> players;
    private List<Label> marksT;
    private List<Circle> damage;
    private List<Circle> mark;
    private List<Circle> token;

    public GameFrame(CommandContainer cmd, String board, int map) {
        this.cmdLauncher = cmd;
        this.mapPath = mapParser(map);
        this.color = new ArrayList<>();
        this.token = new ArrayList<>();
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
        pbFrame = new PbFrame(this.players, this.color);
        weaponFrame = new WeaponFrame(this.players);
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
        Button showPlBoard = new Button("Mostra PB av");
        Button showWeaponCard = new Button("Mostra Armi av");

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

        weapon1 = new Label();
        weapon2 = new Label();
        weapon3 = new Label();

        pu1 = new Label();
        pu2 = new Label();

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

        showWeaponCard.setMinWidth(buttonWidth);
        showWeaponCard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                weaponFrame.show();
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
        weapon1.setBackground(new Background(weaponBack));
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
        buttonPane.getChildren().add(showWeaponCard);

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

        distruttoreToken = new Circle();
        dozerToken = new Circle();
        sprogToken = new Circle();
        violettaToken = new Circle();
        bansheeToken = new Circle();
        token.add(distruttoreToken);
        token.add(dozerToken);
        token.add(sprogToken);
        token.add(violettaToken);
        token.add(bansheeToken);
        for (Circle c : token){
            c.setVisible(false);
            c.setRadius(15);
            c.setStroke(Color.BLACK);
            mapPane.getChildren().add(c);
        }
        distruttoreToken.setFill(Color.YELLOW);
        dozerToken.setFill(Color.GREY);
        sprogToken.setFill(Color.DARKGREEN);
        violettaToken.setFill(Color.BLUEVIOLET);
        bansheeToken.setFill(Color.TEAL);

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

    private InputStream weaponParser(String weapon) {
        switch (weapon) {
            case "Cannone vortex":
                return getClass().getResourceAsStream("/img/CannoneVortex.png");
            case "CyberGuanto":
                return getClass().getResourceAsStream("/img/CyberGuanto.png");
            case "Distruttore":
                return getClass().getResourceAsStream("/img/DistruttoreWeap.png");
            case "Falce protonica":
                return getClass().getResourceAsStream("/img/FalceProtonica.png");
            case "Fucile al plasma":
                return getClass().getResourceAsStream("/img/FucileAlPlasma.png");
            case "Fucile a pompa":
                return getClass().getResourceAsStream("/img/FucileAPompa.png");
            case "Fucile di precisione":
                return getClass().getResourceAsStream("/img/FucileDiPrecisione.png");
            case "Fucile laser":
                return getClass().getResourceAsStream("/img/FucileLaser.png");
            case "Lanciafiamme":
                return getClass().getResourceAsStream("/img/LanciaFiamme.png");
            case "Lanciagranate":
                return getClass().getResourceAsStream("/img/LanciaGranate.png");
            case "Lanciarazzi":
                return getClass().getResourceAsStream("/img/LanciaRazzi.png");
            case "Martello ionico":
                return getClass().getResourceAsStream("/img/MartelloIonico.png");
            case "Mitragliatrice":
                return getClass().getResourceAsStream("/img/Mitragliatrice.png");
            case "Onda d'urto":
                return getClass().getResourceAsStream("/img/OndaDurto.png");
            case "Raggio solare":
                return getClass().getResourceAsStream("/img/RaggioSolare.png");
            case "Raggio traente":
                return getClass().getResourceAsStream("/img/RaggioTraente.png");
            case "Razzo termico":
                return getClass().getResourceAsStream("/img/RazzoTermico.png");
            case "Spada fotonica":
                return getClass().getResourceAsStream("/img/SpadaFotonica.png");
            case "Torpedine":
                return getClass().getResourceAsStream("/img/Torpedine.png");
            case "Vulcanizzatore":
                return getClass().getResourceAsStream("/img/Vulcanizzatore.png");
            case "Zx-2":
                return getClass().getResourceAsStream("/img/ZxZ.png");
            default:
                return getClass().getResourceAsStream("/img/RetroArmi.png");
        }
    }

    private InputStream powerUpParser(PowerUpCard pc){
        switch (pc.getPowerUpType()){
            case NEWTON:
                if (pc.getColor().equals(Color.YELLOW)){
                    return getClass().getResourceAsStream("/img/RaggioCineticoG.png");
                }
                else if (pc.getColor().equals(Color.BLUE)){
                    return getClass().getResourceAsStream("/img/RaggioCineticoB.png");
                }
                else{
                    return getClass().getResourceAsStream("/img/RaggioCineticoR.png");
                }
            case TELEPORTER:
                if (pc.getColor().equals(Color.YELLOW)){
                    return getClass().getResourceAsStream("/img/TeletrasportoG.png");
                }
                else if (pc.getColor().equals(Color.BLUE)){
                    return getClass().getResourceAsStream("/img/TeletrasportoB.png");
                }
                else{
                    return getClass().getResourceAsStream("/img/TeletrasportoR.png");
                }
            case TAGBACK_GRANADE:
                if (pc.getColor().equals(Color.YELLOW)){
                    return getClass().getResourceAsStream("/img/GranataVenomG.png");
                }
                else if (pc.getColor().equals(Color.BLUE)){
                    return getClass().getResourceAsStream("/img/GranataVenomB.png");
                }
                else{
                    return getClass().getResourceAsStream("/img/GranataVenomR.png");
                }
            case TARGETING_SCOPE:
                if (pc.getColor().equals(Color.YELLOW)){
                    return getClass().getResourceAsStream("/img/MirinoG.png");
                }
                else if (pc.getColor().equals(Color.BLUE)){
                    return getClass().getResourceAsStream("/img/MirinoB.png");
                }
                else{
                    return getClass().getResourceAsStream("/img/MirinoR.png");
                }
            default:
                return getClass().getResourceAsStream("/img/RetroPu.png");
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
    public void onHpChange(SemplifiedPlayer damagePlayer) {
        if (!players.contains(damagePlayer.getName())) {
            for(Circle c :damage){
                c.setVisible(false);
            }
            List<SemplifiedBloodToken> damageToken = damagePlayer.getPlayerBoard().getDamageTokens();
            for (int i = 0; i<damageToken.size();i++){
                int index = players.indexOf(damageToken.get(i).getOwner().getName());
                damage.get(i).setFill(color.get(index));
                damage.get(i).setVisible(true);
            }
        }
    }

    @Override
    public void onMarksChange(SemplifiedPlayer markedPlayer) {
        if (!players.contains(markedPlayer.getName())){
            for(Circle c :mark){
                c.setVisible(false);
            }
            for (Label l : marksT){
                l.setText("0");
                l.setVisible(false);
            }
            List<SemplifiedBloodToken> marks = markedPlayer.getPlayerBoard().getMarksTokens();
            for (SemplifiedBloodToken b : marks){
                int index = players.indexOf(b.getOwner().getName());
                mark.get(index).setVisible(true);
                marksT.get(index).setVisible(true);
                marksT.get(index).setText(""+Integer.parseInt(marksT.get(index).getText())+1);
            }
        }

    }

    @Override
    public void onAmmoChange(SemplifiedPlayer p) {
        if (!players.contains(p.getName())) {
            blueAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumBlueAmmo());
            redAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumRedAmmo());
            yellowAmmmo.setText(""+p.getPlayerBoard().getLoader().getNumYellowAmmo());
        }
    }

    @Override
    public void onPowerUpChange(SemplifiedPlayer p) {
        if (!players.contains(p.getName())){
            List<PowerUpCard> powerUp = p.getPowerUpCards();
            int index = 0;
            while ( index < p.getNumPowerUps()){
                switch (index) {
                    case 0:
                        pu1.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 1:
                        pu2.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    default:
                        break;
                }
                index++;
            }
            while (index <2){
                switch (index){
                    case 0:
                        pu1.setBackground(new Background(puBack));
                        break;
                    case 1:
                        pu2.setBackground(new Background(puBack));
                        break;
                    default:
                        break;
                }
                index++;
            }
        }
    }

    @Override
    public void onWeaponChange(SemplifiedPlayer p) {
        if (!players.contains(p.getName())){
            List<SemplifiedWeaponCard> weapons = p.getWeaponCards();
            int index = 0;
            while ( index < p.getNumWeapons()){
                switch (index) {
                    case 0:
                        weapon1.setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 1:
                        weapon2.setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 2:
                        weapon3.setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    default:
                        break;
                }
                index++;
            }
            while (index <3){
                switch (index){
                    case 0:
                        weapon1.setBackground(new Background(weaponBack));
                        break;
                    case 1:
                        weapon2.setBackground(new Background(weaponBack));
                        break;
                    case 2:
                        weapon3.setBackground(new Background(weaponBack));
                        break;
                    default:
                        break;
                }
                index++;
            }
        }

    }

    @Override
    public void onPlayerChange(SemplifiedPlayer p) {
        //TODO
    }

}
