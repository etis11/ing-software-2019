package gui;

import controller.CommandLauncher;
import controller.commandpack.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.ClientSingleton;

import java.io.File;
import java.io.FileInputStream;

public class LobbyFrame extends Application {
    final int buttonWidth = 75;
    private CommandLauncher cmdLauncher;

    public void init(CommandLauncher cmd){
        this.cmdLauncher = cmd;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Adrenalina - Lobby d'attesa");
        stage.setResizable(false);

        //path of button image
        final String pathDistruttore = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Distruttore.png";
        final String pathBanshee = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Banshee.png";
        final String pathDozer = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Dozer.png";
        final String pathSprog = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Sprog.png";
        final String pathVioletta = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Violetta.png";

        Image imageDistruttore = new Image(new FileInputStream(pathDistruttore),75,75,false, true);
        Image imageBanshee = new Image(new FileInputStream(pathBanshee),75,75,false, true);
        Image imageDozer = new Image(new FileInputStream(pathDozer),75,75,false, true);
        Image imageSprog = new Image(new FileInputStream(pathSprog),75,75,false, true);
        Image imageVioletta = new Image(new FileInputStream(pathVioletta),75,75,false, true);


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
        Label info = new Label();

        info.setLayoutY(550);
        info.setLayoutX(500);
        info.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        buttonPlayer1.setLayoutY(300);
        buttonPlayer1.setLayoutX(50);
        buttonPlayer1.setMinWidth(buttonWidth);
        buttonPlayer1.setMinHeight(buttonWidth);
        buttonPlayer1.setGraphic(new ImageView(imageDistruttore));
        buttonPlayer1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkToken("Distruttore")){
                    cmdLauncher.addCommand(new SetTokenCommand(ClientSingleton.getInstance().getToken(), "Distruttore"));
                }
                else{
                    info.setText("personaggio già scelto");
                    info.setVisible(true);
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
                if (checkToken("Banshee")){
                    cmdLauncher.addCommand(new SetTokenCommand(ClientSingleton.getInstance().getToken(), "Banshee"));
                }
                else{
                    info.setText("personaggio già scelto");
                    info.setVisible(true);
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
                if (checkToken("Dozer")){
                    cmdLauncher.addCommand(new SetTokenCommand(ClientSingleton.getInstance().getToken(), "Dozer"));
                }
                else{
                    info.setText("personaggio già scelto");
                    info.setVisible(true);
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
                if (checkToken("Sprog")){
                    cmdLauncher.addCommand(new SetTokenCommand(ClientSingleton.getInstance().getToken(), "Sprog"));
                }
                else{
                    info.setText("personaggio già scelto");
                    info.setVisible(true);
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
                if (checkToken("Violetta")){
                    cmdLauncher.addCommand(new SetTokenCommand(ClientSingleton.getInstance().getToken(), "Violetta"));
                }
                else{
                    info.setText("personaggio già scelto");
                    info.setVisible(true);
                }

            }
        });

        usernameField.setLayoutX(50);
        usernameField.setLayoutY(200);
        usernameField.setMinWidth(150);

        effectPhraseField.setLayoutY(450);
        effectPhraseField.setLayoutX(50);
        effectPhraseField.setMinWidth(150);

        deathField.setLayoutX(590);
        deathField.setLayoutY(200);
        deathField.setMinWidth(150);

        playerNumberField.setLayoutX(590);
        playerNumberField.setLayoutY(450);
        playerNumberField.setMinWidth(150);

        buttonUsername.setLayoutY(usernameField.getLayoutY());
        buttonUsername.setLayoutX(usernameField.getLayoutX()+usernameField.getMinWidth()+50);
        buttonUsername.setMinWidth(150);
        buttonUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkUsername(usernameField.getText().trim())){
                    cmdLauncher.addCommand(new SetUsernameCommand(ClientSingleton.getInstance().getToken(), usernameField.getText().trim()));
                }
                else{
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }
            }
        });

        buttonPhrase.setLayoutY(effectPhraseField.getLayoutY());
        buttonPhrase.setLayoutX(effectPhraseField.getLayoutX()+effectPhraseField.getMinWidth()+50);
        buttonPhrase.setMinWidth(150);
        buttonPhrase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkPhrase(effectPhraseField.getText())){
                    cmdLauncher.addCommand(new SetEffectPhraseCommand(ClientSingleton.getInstance().getToken(), effectPhraseField.getText()));
                }
                else{
                    info.setText("inserisci una frase");
                    info.setVisible(true);
                }
            }
        });

        buttonDeath.setLayoutY(deathField.getLayoutY());
        buttonDeath.setLayoutX(deathField.getLayoutX()+deathField.getMinWidth()+50);
        buttonDeath.setMinWidth(150);
        buttonDeath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkDeath(deathField.getText())){
                    cmdLauncher.addCommand(new SetNumberOfDeathCommand(ClientSingleton.getInstance().getToken(), Integer.parseInt(deathField.getText())));
                }
                else{
                    //TODO messaggio
                    info.setText("TODO");
                    info.setVisible(true);
                }
            }
        });

        buttonPlayer.setLayoutY(playerNumberField.getLayoutY());
        buttonPlayer.setLayoutX(playerNumberField.getLayoutX()+playerNumberField.getMinWidth()+50);
        buttonPlayer.setMinWidth(150);
        buttonPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if(checkPlayer(playerNumberField.getText())){
                    cmdLauncher.addCommand(new SetPlayerNumberCommand(ClientSingleton.getInstance().getToken(), Integer.parseInt(playerNumberField.getText())));
                }
                else{
                    //TODO messaggio
                    info.setText("TODO");
                    info.setVisible(true);
                }
            }
        });


        //path of background image
        final String path = "."+ File.separatorChar+ "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"img"+File.separatorChar+"Adrenalina.png";

        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream(path),1000,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

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

        stage.setScene(new Scene(box, 1000, 600));
        stage.show();
    }

    private boolean checkUsername(String username){
        return !username.equalsIgnoreCase("") && !username.equalsIgnoreCase("username") &&!username.equalsIgnoreCase("Cambia username") ;
    }

    private boolean checkPhrase(String phrase){
        return !phrase.equalsIgnoreCase("") &&!phrase.equalsIgnoreCase("Cambia frase ad effetto") ;
    }

    private boolean checkDeath(String phrase){
        return !phrase.equalsIgnoreCase("") &&!phrase.equalsIgnoreCase("Cambia numero morti") ;
    }

    private boolean checkPlayer(String phrase){
        return !phrase.equalsIgnoreCase("") &&!phrase.equalsIgnoreCase("Cambia numero giocatori") ;
    }

    private boolean checkToken(String name){
        //TODO implements control
        return false;
    }
}
