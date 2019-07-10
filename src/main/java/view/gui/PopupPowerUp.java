package view.gui;

import controller.CommandContainer;
import controller.LOGGER;
import controller.commandpack.SpawnCommand;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PowerUpCard;
import model.PowerUpType;
import model.clientModel.SemplifiedPlayer;
import view.ClientSingleton;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class PopupPowerUp {

    private Gui gui;
    private SemplifiedPlayer semplifiedPlayer;
    private final CommandContainer cmdLauncher;
    private final InputStream pathBackPu = getClass().getResourceAsStream("/img/RetroPu.png");
    final Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    final BackgroundImage puBack = new BackgroundImage(new Image(pathBackPu, 110, 190, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    private Stage stage;
    private Pane pane;

    private Label pwu1;
    private Label pwu2;
    private Label pwu3;
    private Label pwu4;

    private List<Label> pwups;

    List<String> players;

    public PopupPowerUp(List<String> players, CommandContainer cmdLauncher, Gui gui){
        stage = new Stage();
        pane = new Pane();
        this.players = players;
        this.cmdLauncher = cmdLauncher;
        this.gui = gui;
        generate();
    }

    public void setSemplifiedPlayer(SemplifiedPlayer player){
        this.semplifiedPlayer = player;
    }

    private void generate() {
        stage.setTitle("Adrenalina - Scarta un powerup");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);

        generatePu();


        pwu1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new SpawnCommand(gui.getToken(), powerUpParser(semplifiedPlayer.getPowerUpCards().get(0).getPowerUpType()), colorParser(semplifiedPlayer.getPowerUpCards().get(0).getColor())));
                } catch (RemoteException e) {
                    LOGGER.LOGGER.log(Level.WARNING, e.getMessage());
                }
                stage.close();
            }
        });

        pwu2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new SpawnCommand(gui.getToken(), powerUpParser(semplifiedPlayer.getPowerUpCards().get(1).getPowerUpType()), colorParser(semplifiedPlayer.getPowerUpCards().get(1).getColor())));
                } catch (RemoteException e) {
                    LOGGER.LOGGER.log(Level.WARNING, e.getMessage());
                }
                stage.close();
            }
        });

        pwu3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new SpawnCommand(ClientSingleton.getInstance().getToken(), powerUpParser(semplifiedPlayer.getPowerUpCards().get(2).getPowerUpType()), colorParser(semplifiedPlayer.getPowerUpCards().get(2).getColor())));
                } catch (RemoteException e) {
                    LOGGER.LOGGER.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
                }
                stage.close();
            }
        });

        pwu4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new SpawnCommand(ClientSingleton.getInstance().getToken(), powerUpParser(semplifiedPlayer.getPowerUpCards().get(3).getPowerUpType()), colorParser(semplifiedPlayer.getPowerUpCards().get(3).getColor())));
                } catch (RemoteException e) {
                    LOGGER.LOGGER.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
                }
                stage.close();
            }
        });

        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
    }

    public void show(){
        stage.show();
    }

    private void generatePu(){
        pwu1 = new Label();
        pwu2 = new Label();
        pwu3 = new Label();
        pwu4 = new Label();

        pwups = new ArrayList<>(4);
        pwups.add(pwu1);
        pwups.add(pwu2);
        pwups.add(pwu3);
        pwups.add(pwu4);

        for (Label l:pwups){
            l.setVisible(false);
            l.setLayoutY(50);
            l.setMinWidth(110);
            l.setMinHeight(190);
            l.setBorder(border);
            pane.getChildren().add(l);
        }

        pwu1.setBackground(new Background(puBack));
        pwu2.setBackground(new Background(puBack));
        pwu3.setBackground(new Background(puBack));
        pwu4.setBackground(new Background(puBack));
        pwu1.setLayoutX(100);
        pwu2.setLayoutX(230);
        pwu3.setLayoutX(360);
        pwu4.setLayoutX(490);
    }

    public void updatePu(SemplifiedPlayer p){
        if (!players.contains(p.getName())){
            List<PowerUpCard> powerUp = p.getPowerUpCards();
            int index = 0;
            while ( index < p.getNumPowerUps()){
                switch (index) {
                    case 0:
                        pwu1.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 1:
                        pwu2.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 2:
                        pwu3.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    case 4:
                        pwu4.setBackground(new Background(new BackgroundImage(new Image(powerUpParser(powerUp.get(index)), 110, 190, false, true),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT)));
                        break;
                    default:
                        break;
                }
                index++;
            }
            while (index <4){
                switch (index){
                    case 0:
                        pwu1.setBackground(new Background(puBack));
                        break;
                    case 1:
                        pwu2.setBackground(new Background(puBack));
                        break;
                    case 2:
                        pwu3.setBackground(new Background(puBack));
                        break;
                    case 3:
                        pwu4.setBackground(new Background(puBack));
                        break;
                    default:
                        break;
                }
                index++;
            }
        }
        for (int i = 0; i < semplifiedPlayer.getPowerUpCards().size(); i++) {
            pwups.get(i).setVisible(true);
        }
    }

    private InputStream powerUpParser(PowerUpCard pc){
        switch (pc.getPowerUpType()){
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

    private String powerUpParser(PowerUpType powerUp){
        switch (powerUp){
            case TARGETING_SCOPE:
                return "mirino";
            case TAGBACK_GRANADE:
                return "granata";
            case TELEPORTER:
                return "teletrasporto";
            case NEWTON:
                return "raggiocinetico";
        }
        return null;
    }

    private String colorParser(Color color){
        if(color.equals(Color.RED)){
            return "rosso";
        }else if(color.equals(Color.BLUE)){
            return "blu";
        }else if(color.equals(Color.YELLOW)){
            return "giallo";
        }else{
            return null;
        }
    }

}
