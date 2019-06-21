package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedTile;
import model.clientModel.SemplifiedWeaponCard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegenFrame {

    final int regenDimension = 50;
    private final InputStream pathBackWeapon = getClass().getResourceAsStream("/img/RetroArmi.png");
    final Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    final BackgroundImage weaponBack = new BackgroundImage(new Image(pathBackWeapon, 110, 190, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    private Stage stage;
    private Pane pane;

    private List<Label> blueRegenWeapon;
    private List<Label> redRegenWeapon;
    private List<Label> yellowRegenWeapon;

    private Label blueRegen;
    private Label redRegen;
    private Label yellowRegen;

    private Label weapon1Br;
    private Label weapon2Br;
    private Label weapon3Br;

    private Label weapon1Rr;
    private Label weapon2Rr;
    private Label weapon3Rr;

    private Label weapon1Yr;
    private Label weapon2Yr;
    private Label weapon3Yr;

    public RegenFrame(){
        stage = new Stage();
        pane = new Pane();
        generate();
    }

    private void generate(){
        stage.setTitle("Adrenalina - Punti rigenerazione");
        stage.setResizable(false);

        generateRegen();

        Scene scene = new Scene(pane, 600, 700);
        stage.setScene(scene);
    }

    public void show(){
        stage.show();
    }

    private void generateRegen(){
        blueRegen = new Label();
        redRegen = new Label();
        yellowRegen = new Label();
        weapon1Br = new Label();
        weapon2Br = new Label();
        weapon3Br = new Label();
        weapon1Rr = new Label();
        weapon2Rr = new Label();
        weapon3Rr = new Label();
        weapon1Yr = new Label();
        weapon2Yr = new Label();
        weapon3Yr = new Label();
        blueRegenWeapon = new ArrayList<>(3);
        blueRegenWeapon.add(weapon1Br);
        blueRegenWeapon.add(weapon2Br);
        blueRegenWeapon.add(weapon3Br);
        redRegenWeapon = new ArrayList<>(3);
        redRegenWeapon.add(weapon1Rr);
        redRegenWeapon.add(weapon2Rr);
        redRegenWeapon.add(weapon3Rr);
        yellowRegenWeapon = new ArrayList<>(3);
        yellowRegenWeapon.add(weapon1Yr);
        yellowRegenWeapon.add(weapon2Yr);
        yellowRegenWeapon.add(weapon3Yr);

        blueRegen.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        redRegen.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        yellowRegen.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        blueRegen.setMinWidth(regenDimension);
        blueRegen.setMinHeight(regenDimension);
        redRegen.setMinWidth(regenDimension);
        redRegen.setMinHeight(regenDimension);
        yellowRegen.setMinWidth(regenDimension);
        yellowRegen.setMinHeight(regenDimension);

        blueRegen.setLayoutX(25);
        blueRegen.setLayoutY(125);
        redRegen.setLayoutX(blueRegen.getLayoutX());
        redRegen.setLayoutY(blueRegen.getLayoutY()+190);
        yellowRegen.setLayoutX(blueRegen.getLayoutX());
        yellowRegen.setLayoutY(redRegen.getLayoutY()+190);

        for (Label l:blueRegenWeapon){
            l.setVisible(true);
            l.setBorder(border);
            l.setLayoutY(50);
            l.setMinWidth(110);
            l.setMinHeight(190);
            pane.getChildren().add(l);
        }
        weapon1Br.setLayoutX(100);
        weapon2Br.setLayoutX(230);
        weapon3Br.setLayoutX(360);
        weapon1Br.setBackground(new Background(weaponBack));
        weapon2Br.setBackground(new Background(weaponBack));
        weapon3Br.setBackground(new Background(weaponBack));

        for (Label l:redRegenWeapon){
            l.setVisible(true);
            l.setBorder(border);
            l.setLayoutY(250);
            l.setMinWidth(110);
            l.setMinHeight(190);
            pane.getChildren().add(l);
        }
        weapon1Rr.setLayoutX(weapon1Br.getLayoutX());
        weapon2Rr.setLayoutX(weapon2Br.getLayoutX());
        weapon3Rr.setLayoutX(weapon3Br.getLayoutX());
        weapon1Rr.setBackground(new Background(weaponBack));
        weapon2Rr.setBackground(new Background(weaponBack));
        weapon3Rr.setBackground(new Background(weaponBack));

        for (Label l:yellowRegenWeapon){
            l.setVisible(true);
            l.setBorder(border);
            l.setLayoutY(450);
            l.setMinWidth(110);
            l.setMinHeight(190);
            pane.getChildren().add(l);
        }
        weapon1Yr.setLayoutX(weapon1Br.getLayoutX());
        weapon2Yr.setLayoutX(weapon2Br.getLayoutX());
        weapon3Yr.setLayoutX(weapon3Br.getLayoutX());
        weapon1Yr.setBackground(new Background(weaponBack));
        weapon2Yr.setBackground(new Background(weaponBack));
        weapon3Yr.setBackground(new Background(weaponBack));

        pane.getChildren().add(blueRegen);
        pane.getChildren().add(redRegen);
        pane.getChildren().add(yellowRegen);

    }

    public void updateRegen(SemplifiedMap map){
        SemplifiedTile tileToUpdate = map.getTile(0,2);
        List<SemplifiedWeaponCard> weapons = tileToUpdate.getWeapons();
        int index = 0;
        while(index<weapons.size()){
            blueRegenWeapon.get(index).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
            index++;
        }
        while(index<3){
            blueRegenWeapon.get(index).setBackground(new Background(weaponBack));
            index++;
        }

        tileToUpdate = map.getTile(1,0);
        weapons = tileToUpdate.getWeapons();
        index = 0;
        while(index<weapons.size()){
            redRegenWeapon.get(index).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
            index++;
        }
        while(index<3){
            redRegenWeapon.get(index).setBackground(new Background(weaponBack));
            index++;
        }

        tileToUpdate = map.getTile(2,3);
        weapons = tileToUpdate.getWeapons();
        index = 0;
        while(index<weapons.size()){
            yellowRegenWeapon.get(index).setBackground(new Background(new BackgroundImage(new Image(weaponParser(weapons.get(index).getName()), 110, 190, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT)));
            index++;
        }
        while(index<3){
            yellowRegenWeapon.get(index).setBackground(new Background(weaponBack));
            index++;
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
