package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedWeaponCard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeaponFrame {
    private final InputStream pathBackWeapon = getClass().getResourceAsStream("/img/RetroArmi.png");
    final InputStream pathDistruttore = getClass().getResourceAsStream( "/img/Distruttore.PNG");
    final InputStream pathBanshee = getClass().getResourceAsStream( "/img/Banshee.PNG");
    final InputStream pathDozer = getClass().getResourceAsStream( "/img/Dozer.PNG");
    final InputStream pathSprog = getClass().getResourceAsStream( "/img/Sprog.PNG");
    final InputStream pathVioletta = getClass().getResourceAsStream( "/img/Violetta.PNG");

    private Stage stage;
    private Pane pane;
    private List<String> players;

    private Label firstPl;
    private Label weapon11;
    private Label weapon12;
    private Label weapon13;
    private List<Label> firstW;

    private Label secondPl;
    private Label weapon21;
    private Label weapon22;
    private Label weapon23;
    private List<Label> secondW;

    private Label thirdPl;
    private Label weapon31;
    private Label weapon32;
    private Label weapon33;
    private List<Label> thirdW;

    private Label fourthPl;
    private Label weapon41;
    private Label weapon42;
    private Label weapon43;
    private List<Label> fourthW;

    private List<Label> playerLabel;
    private Border border;

    final BackgroundImage weaponBack = new BackgroundImage(new Image(pathBackWeapon, 125, 195, false, true),
    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    BackgroundSize.DEFAULT);

    public WeaponFrame(List<String> players) {
        this.stage = new Stage();
        this.players = players;
        border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        generate();
    }

    private void generate(){

        stage.setTitle("Adrenalina - Armi Avversari");
        stage.setResizable(false);
        pane = new Pane();

        generateTokenLabel();
        generateFirst();
        generateSecond();
        generateThird();
        generateFourth();


        Scene scene = new Scene(pane, 1125, 465);
        stage.setScene(scene);


    }

    public void show(){
        stage.show();
    }

    private void generateTokenLabel(){
        playerLabel = new ArrayList<>();
        firstPl = new Label();
        secondPl = new Label();
        thirdPl = new Label();
        fourthPl = new Label();

        playerLabel.add(firstPl);
        playerLabel.add(secondPl);
        playerLabel.add(thirdPl);
        playerLabel.add(fourthPl);
        for(Label l:playerLabel){
            l.setMinWidth(100);
            l.setMinHeight(100);
            pane.getChildren().add(l);
        }

        firstPl.setLayoutX(20);
        firstPl.setLayoutY(70);
        secondPl.setLayoutY(70);
        secondPl.setLayoutX(575);
        thirdPl.setLayoutX(firstPl.getLayoutX());
        thirdPl.setLayoutY(295);
        fourthPl.setLayoutX(secondPl.getLayoutX());
        fourthPl.setLayoutY(thirdPl.getLayoutY());
    }

    private void generateFirst(){

        weapon11 = new Label();
        weapon12 = new Label();
        weapon13 = new Label();
        firstW = new ArrayList<>();
        firstW.add(weapon11);
        firstW.add(weapon12);
        firstW.add(weapon13);
        firstPl.setBackground(new Background(new BackgroundImage(new Image(tokenImgParser(players.get(0)), 100, 100, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        for (Label l:firstW){
            l.setVisible(true);
            l.setBorder(border);
            l.setMinWidth(125);
            l.setMinHeight(195);
            l.setLayoutY(25);
            pane.getChildren().add(l);
        }
        weapon11.setLayoutX(130);
        weapon12.setLayoutX(265);
        weapon13.setLayoutX(400);
        weapon11.setBackground(new Background(weaponBack));
        weapon12.setBackground(new Background(weaponBack));
        weapon13.setBackground(new Background(weaponBack));
    }

    private void generateSecond(){

        weapon21 = new Label();
        weapon22 = new Label();
        weapon23 = new Label();
        secondW = new ArrayList<>();
        secondW.add(weapon21);
        secondW.add(weapon22);
        secondW.add(weapon23);
        secondPl.setBackground(new Background(new BackgroundImage(new Image(tokenImgParser(players.get(1)), 100, 100, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        for (Label l:secondW){
            l.setVisible(true);
            l.setBorder(border);
            l.setMinWidth(125);
            l.setMinHeight(195);
            l.setLayoutY(25);
            pane.getChildren().add(l);
        }
        weapon21.setLayoutX(685);
        weapon22.setLayoutX(820);
        weapon23.setLayoutX(955);
        weapon21.setBackground(new Background(weaponBack));
        weapon22.setBackground(new Background(weaponBack));
        weapon23.setBackground(new Background(weaponBack));
    }

    private void generateThird(){

        weapon31 = new Label();
        weapon32 = new Label();
        weapon33 = new Label();
        thirdW = new ArrayList<>();
        thirdW.add(weapon31);
        thirdW.add(weapon32);
        thirdW.add(weapon33);
        thirdPl.setBackground(new Background(new BackgroundImage(new Image(tokenImgParser(players.get(2)), 100, 100, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        for (Label l:thirdW){
            l.setVisible(true);
            l.setBorder(border);
            l.setMinWidth(125);
            l.setMinHeight(195);
            l.setLayoutY(250);
            pane.getChildren().add(l);
        }
        weapon31.setLayoutX(130);
        weapon32.setLayoutX(265);
        weapon33.setLayoutX(400);
        weapon31.setBackground(new Background(weaponBack));
        weapon32.setBackground(new Background(weaponBack));
        weapon33.setBackground(new Background(weaponBack));
    }

    private void generateFourth(){

        weapon41 = new Label();
        weapon42 = new Label();
        weapon43 = new Label();
        fourthW = new ArrayList<>();
        fourthW.add(weapon41);
        fourthW.add(weapon42);
        fourthW.add(weapon43);
        fourthPl.setBackground(new Background(new BackgroundImage(new Image(tokenImgParser(players.get(3)), 100, 100, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        for (Label l:fourthW){
            l.setVisible(true);
            l.setBorder(border);
            l.setMinWidth(125);
            l.setMinHeight(195);
            l.setLayoutY(250);
            pane.getChildren().add(l);
        }
        weapon41.setLayoutX(685);
        weapon42.setLayoutX(820);
        weapon43.setLayoutX(955);
        weapon41.setBackground(new Background(weaponBack));
        weapon42.setBackground(new Background(weaponBack));
        weapon43.setBackground(new Background(weaponBack));
    }

    private InputStream tokenImgParser(String token) {
        switch (token) {
            case "distruttore":
                return pathDistruttore;
            case "sprog":
                return pathSprog;
            case "dozer":
                return pathDozer;
            case "violetta":
                return pathVioletta;
            case "banshee":
                return pathBanshee;
            default:
                throw  new RuntimeException("name not allowed");
        }
    }

    public void updateWeapon(SemplifiedPlayer p){
        List<Label> parsedWeapon = playerWeaponParser(p.getName());
        List<SemplifiedWeaponCard> weaponUpdated = p.getWeaponCards();
        if(weaponUpdated.get(0).isLoaded()){
            parsedWeapon.get(0).setBackground(new Background(weaponBack));
        }
        else{
            parsedWeapon.get(0).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weaponUpdated.get(0).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
        }
        if(weaponUpdated.get(1).isLoaded()){
            parsedWeapon.get(1).setBackground(new Background(weaponBack));
        }
        else{
            parsedWeapon.get(1).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weaponUpdated.get(1).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
        }
        if(weaponUpdated.get(2).isLoaded()){
            parsedWeapon.get(2).setBackground(new Background(weaponBack));
        }
        else{
            parsedWeapon.get(2).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weaponUpdated.get(2).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
        }

    }

    private List<Label>playerWeaponParser(String p){
        int pos = players.indexOf(p);
        switch (pos){
            case 0:
                return firstW;
            case 1:
                return secondW;
            case 2:
                return thirdW;
            case 3:
                return fourthW;
            default:
                //TODO o exception?
                return firstW;
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
}
