package view.gui;

import controller.CommandContainer;
import controller.LOGGER;
import controller.commandpack.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.ClientSingleton;
import view.MessageListener;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;

public class LobbyFrame implements MessageListener {
    final int buttonWidth = 75;

    private final Gui gui;
    private CommandContainer cmdLauncher;
    private Stage stage;

    private Label lobbyLabel;
    private Label info;

    final InputStream pathDistruttore = getClass().getResourceAsStream( "/img/Distruttore.PNG");
    final InputStream pathBanshee = getClass().getResourceAsStream( "/img/Banshee.PNG");
    final InputStream pathDozer = getClass().getResourceAsStream( "/img/Dozer.PNG");
    final InputStream pathSprog = getClass().getResourceAsStream( "/img/Sprog.PNG");
    final InputStream pathVioletta = getClass().getResourceAsStream( "/img/Violetta.PNG");

    public LobbyFrame(CommandContainer cmd, Gui gui) {
        this.cmdLauncher = cmd;
        this.gui=gui;
        this.stage = new Stage();
        generate();
    }


    private void generate(){
        stage.setTitle("Adrenalina - Lobby d'attesa");
        stage.setResizable(false);

        //path of button image


        Image imageDistruttore = new Image(pathDistruttore, 75, 75, false, true);
        Image imageBanshee = new Image(pathBanshee, 75, 75, false, true);
        Image imageDozer = new Image(pathDozer, 75, 75, false, true);
        Image imageSprog = new Image(pathSprog, 75, 75, false, true);
        Image imageVioletta = new Image(pathVioletta, 75, 75, false, true);


        Button buttonPlayer1 = new Button();
        Button buttonPlayer2 = new Button();
        Button buttonPlayer3 = new Button();
        Button buttonPlayer4 = new Button();
        Button buttonPlayer5 = new Button();
        Button buttonUsername = new Button("Imposta username");
        Button buttonPhrase = new Button("Imposta frase ad effetto");
        Button buttonDeath = new Button("Imposta numero morti");
        Button buttonPlayer = new Button("Imposta numero giocatori");
        TextField usernameField = new TextField("Cambia username");
        TextField effectPhraseField = new TextField("Cambia frase ad effetto");
        TextField deathField = new TextField("Cambia numero morti");
        TextField playerNumberField = new TextField("Cambia numero giocatori");
        ToggleButton toggleButtonFrenzy = new ToggleButton("Frenesia finale");

        info = new Label();
        ObservableList<String> comboItems = FXCollections.observableArrayList(
                "Scegli Mappa",
                "Piccola",
                "Media",
                "Grande",
                "Esagerata"
        );
        ComboBox comboBox = new ComboBox(comboItems);
        lobbyLabel = new Label("Partita in attesa di incominciare");

        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            try {
                cmdLauncher.addCommand(new SetMapCommand(gui.getToken(), mapParser(Integer.parseInt(newValue.toString()))));
                gui.setMap(mapParser(Integer.parseInt(newValue.toString())));
                gui.createGameFrame();
            } catch (RemoteException e) {
                LOGGER.LOGGER.log(Level.WARNING, e.getMessage());
            }
        });
        comboBox.setLayoutX(50);
        comboBox.setLayoutY(480);

        info.setLayoutY(550);
        info.setLayoutX(425);
        info.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        buttonPlayer1.setLayoutY(250);
        buttonPlayer1.setLayoutX(50);
        buttonPlayer1.setMinWidth(buttonWidth);
        buttonPlayer1.setMinHeight(buttonWidth);
        buttonPlayer1.setGraphic(new ImageView(imageDistruttore));
        buttonPlayer1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                    try {
                        cmdLauncher.addCommand(new SetTokenCommand(gui.getToken(), "distruttore"));
                        gui.setUsername("distruttore");
                        gui.createGameFrame();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
            }
        });

        buttonPlayer2.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer2.setLayoutX(250);
        buttonPlayer2.setMinWidth(buttonWidth);
        buttonPlayer2.setMinHeight(buttonWidth);
        buttonPlayer2.setGraphic(new ImageView(imageBanshee));
        buttonPlayer2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                    try {
                        cmdLauncher.addCommand(new SetTokenCommand(gui.getToken(), "banshee"));
                        gui.setUsername("banshee");
                        gui.createGameFrame();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
            }
        });

        buttonPlayer3.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer3.setLayoutX(450);
        buttonPlayer3.setMinWidth(buttonWidth);
        buttonPlayer3.setMinHeight(buttonWidth);
        buttonPlayer3.setGraphic(new ImageView(imageDozer));
        buttonPlayer3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                    try {
                        cmdLauncher.addCommand(new SetTokenCommand(gui.getToken(), "dozer"));
                        gui.setUsername("dozer");
                        gui.createGameFrame();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
            }
        });

        buttonPlayer4.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer4.setLayoutX(650);
        buttonPlayer4.setMinWidth(buttonWidth);
        buttonPlayer4.setMinHeight(buttonWidth);
        buttonPlayer4.setGraphic(new ImageView(imageSprog));
        buttonPlayer4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                    try {
                        cmdLauncher.addCommand(new SetTokenCommand(gui.getToken(), "sprog"));
                        gui.setUsername("sprog");
                        gui.createGameFrame();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
            }
        });

        buttonPlayer5.setLayoutY(buttonPlayer1.getLayoutY());
        buttonPlayer5.setLayoutX(850);
        buttonPlayer5.setMinWidth(buttonWidth);
        buttonPlayer5.setMinHeight(buttonWidth);
        buttonPlayer5.setGraphic(new ImageView(imageVioletta));
        buttonPlayer5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                    try {
                        cmdLauncher.addCommand(new SetTokenCommand(gui.getToken(), "violetta"));
                        gui.setUsername("violetta");
                        gui.createGameFrame();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
            }
        });

        usernameField.setLayoutX(50);
        usernameField.setLayoutY(160);
        usernameField.setMinWidth(150);

        effectPhraseField.setLayoutY(390);
        effectPhraseField.setLayoutX(50);
        effectPhraseField.setMinWidth(150);

        deathField.setLayoutX(590);
        deathField.setLayoutY(usernameField.getLayoutY());
        deathField.setMinWidth(150);

        playerNumberField.setLayoutX(590);
        playerNumberField.setLayoutY(effectPhraseField.getLayoutY());
        playerNumberField.setMinWidth(150);

        buttonUsername.setLayoutY(usernameField.getLayoutY());
        buttonUsername.setLayoutX(usernameField.getLayoutX() + usernameField.getMinWidth() + 50);
        buttonUsername.setMinWidth(150);
        buttonUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkUsername(usernameField.getText().trim())) {
                    try {
                        cmdLauncher.addCommand(new SetUsernameCommand(gui.getToken(), usernameField.getText().trim()));
                        gui.setUsername(usernameField.getText().trim());
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
                } else {
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }
            }
        });

        buttonPhrase.setLayoutY(effectPhraseField.getLayoutY());
        buttonPhrase.setLayoutX(effectPhraseField.getLayoutX() + effectPhraseField.getMinWidth() + 50);
        buttonPhrase.setMinWidth(150);
        buttonPhrase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkPhrase(effectPhraseField.getText())) {
                    try {
                        cmdLauncher.addCommand(new SetEffectPhraseCommand(gui.getToken(), effectPhraseField.getText()));
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
                } else {
                    info.setText("inserisci una frase");
                    info.setVisible(true);
                }
            }
        });

        buttonDeath.setLayoutY(deathField.getLayoutY());
        buttonDeath.setLayoutX(deathField.getLayoutX() + deathField.getMinWidth() + 50);
        buttonDeath.setMinWidth(150);
        buttonDeath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkDeath(deathField.getText())) {
                    try {
                        cmdLauncher.addCommand(new SetNumberOfDeathCommand(gui.getToken(), Integer.parseInt(deathField.getText())));
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
                } else {
                    info.setText("Inserisci un valore");
                    info.setVisible(true);
                }
            }
        });

        buttonPlayer.setLayoutY(playerNumberField.getLayoutY());
        buttonPlayer.setLayoutX(playerNumberField.getLayoutX() + playerNumberField.getMinWidth() + 50);
        buttonPlayer.setMinWidth(150);
        buttonPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkPlayer(playerNumberField.getText())) {
                    try {
                        cmdLauncher.addCommand(new SetPlayerNumberCommand(gui.getToken(), Integer.parseInt(playerNumberField.getText())));
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                    }
                } else {
                    info.setText("Inserisci un valore");
                    info.setVisible(true);
                }
            }
        });


        //path of background image
        final InputStream pathAdreanline = getClass().getResourceAsStream("/img/Adrenalina.PNG");

        BackgroundImage myBI = new BackgroundImage(new Image(pathAdreanline, 1000, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        lobbyLabel.setLayoutY(25);
        lobbyLabel.setLayoutX(450);
        lobbyLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        toggleButtonFrenzy.setLayoutY(comboBox.getLayoutY());
        toggleButtonFrenzy.setLayoutX(850);
        toggleButtonFrenzy.setSelected(false);
        toggleButtonFrenzy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    cmdLauncher.addCommand(new SetFinalFrenzyCommand(gui.getToken(), toggleButtonFrenzy.isSelected()));
                } catch (RemoteException e) {
                    LOGGER.LOGGER.log(Level.WARNING,e.getMessage());
                }
            }
        });

        Pane box = new Pane();
        //adding background image to box
        box.setBackground(new Background(myBI));
        //adding components to box
        box.getChildren().add(buttonPlayer1);
        box.getChildren().add(buttonPlayer2);
        box.getChildren().add(buttonPlayer3);
        box.getChildren().add(buttonPlayer4);
        box.getChildren().add(buttonPlayer5);
        box.getChildren().add(buttonPhrase);
        box.getChildren().add(buttonUsername);
        box.getChildren().add(buttonDeath);
        box.getChildren().add(buttonPlayer);
        box.getChildren().add(usernameField);
        box.getChildren().add(effectPhraseField);
        box.getChildren().add(deathField);
        box.getChildren().add(playerNumberField);
        box.getChildren().add(info);
        box.getChildren().add(comboBox);
        box.getChildren().add(lobbyLabel);
        box.getChildren().add(toggleButtonFrenzy);

        stage.setScene(new Scene(box, 1000, 600));

    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

    private boolean checkUsername(String username) {
        return !username.equalsIgnoreCase("") && !username.equalsIgnoreCase("username") && !username.equalsIgnoreCase("Cambia username");
    }

    private boolean checkPhrase(String phrase) {
        return !phrase.equalsIgnoreCase("") && !phrase.equalsIgnoreCase("Cambia frase ad effetto");
    }

    private boolean checkDeath(String phrase) {
        return !phrase.equalsIgnoreCase("") && !phrase.equalsIgnoreCase("Cambia numero morti");
    }

    private boolean checkPlayer(String phrase) {
        return !phrase.equalsIgnoreCase("") && !phrase.equalsIgnoreCase("Cambia numero giocatori");
    }

    private String mapParser(int map){
        switch (map){
            case 1:
                return "piccola";
            case 2:
                return "media";
            case 3:
                return "grande";
            case 4:
                return "estrema";
            default:
                return "piccola";
        }
    }

    @Override
    public void notifyMessage(String message) {
        info.setText(message);
        info.setVisible(true);
    }
}
