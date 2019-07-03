package gui;

import controller.*;
import controller.commandpack.SetUsernameCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GameManager;
import model.JsonCreator;
import model.Match;
import model.clientModel.SemplifiedGame;
import network.RMI.ServerRMIInterface;
import network.Socket.CommandLauncherProxySocket;
import view.ClientSingleton;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Level;

public class MainFrame{
    private static final int BUTTON_WIDTH = 150;
    private CommandContainer cmdLauncher;
    private boolean networkActive = true;
    private Stage stage;
    private final Gui gui;

    private final InputStream pathAdrenaline = getClass().getResourceAsStream("/img/Adrenalina.PNG");


    public MainFrame(Gui gui) {
        this.stage = new Stage();
        this.gui = gui;
        generate();
    }

    private void generate(){

        stage.setTitle("Adrenalina - the official game");
        stage.setResizable(false);

        Button startButtonSocket = new Button("Inizia con socket");
        Button startButtonRMI = new Button("Inizia con RMI");
        TextField userField = new TextField("Username");
        TextField tokenField = new TextField("Token");
        Label info = new Label();

        info.setTranslateY(275);
        info.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        userField.setMaxWidth(250);
        tokenField.setMaxWidth(250);
        tokenField.setTranslateY(30);
        //positioning socket button
        startButtonSocket.setTranslateX(-80);
        startButtonSocket.setTranslateY(90);
        startButtonSocket.setMinWidth(BUTTON_WIDTH);
        startButtonSocket.setMaxWidth(BUTTON_WIDTH);
        startButtonSocket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkUsername(userField.getText().trim())) {
                    if (networkActive) {
                        Socket mySocket = null;
                        try {

                            mySocket = new Socket("localhost", 8000);
                            BufferedReader input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                            PrintWriter output = new PrintWriter(mySocket.getOutputStream());
                            output.write(ClientSingleton.getInstance().getToken() + "\n");
                            output.flush();
                            ClientSingleton.getInstance().setToken(input.readLine());
                        } catch (IOException i) {
                            System.out.println(">>> Errore nella connessione. Probabilmente il server è down");
                        }

                        if (mySocket != null) {
                            try {
                                cmdLauncher = new CommandLauncherProxySocket(mySocket, "");
                                gui.setCmd(cmdLauncher);
                            } catch (IOException i) {
                                LOGGER.LOGGER.log(Level.WARNING,i.getMessage());
                                LOGGER.LOGGER.log(Level.WARNING, Arrays.toString(i.getStackTrace()));
                                LOGGER.LOGGER.log(Level.WARNING,">>> Problemi con il socket");
                            }
                        }
                    }
                    if (cmdLauncher == null) {
                        cmdLauncher = new CommandLauncher(new GameManager(), new JsonCreator());
                        gui.setCmd(cmdLauncher);
                    }
                    try {
                        cmdLauncher.addCommand(new SetUsernameCommand(ClientSingleton.getInstance().getToken(), userField.getText().trim()));
                        gui.startLobby();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,Arrays.toString(e.getStackTrace()));
                    }

                } else {
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }

            }

        });
        //positioning rmi button
        startButtonRMI.setTranslateX(80);
        startButtonRMI.setTranslateY(90);
        startButtonRMI.setMinWidth(BUTTON_WIDTH);
        startButtonRMI.setMaxWidth(BUTTON_WIDTH);
        startButtonRMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                info.setVisible(false);
                if (checkUsername(userField.getText().trim())) {
                    if (networkActive) {
                        String token = "";
                        //todo
                        JsonReceiver receiver = new JsonUnwrapper(new SemplifiedGame());
                        try {
                            //the json receiver now is exportable
                            UnicastRemoteObject.exportObject(receiver, 0);
                        } catch (RemoteException i) {
                            //TODO gestire il lancio della remote exception
                            throw new RuntimeException(i);
                        }
                        try {
                            //gets the registry
                            Registry registry = LocateRegistry.getRegistry();
                            //asks fro the server stub
                            ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
                            //token = serverRMI.getPersonalToken(receiver, "");
                            ClientSingleton.getInstance().setToken(token);
                            cmdLauncher = serverRMI.getCurrentCommandLauncher(receiver);
                            gui.setCmd(cmdLauncher);
                        } catch (Exception r) {
                            throw new RuntimeException(r);
                        }
                    } else {
                        cmdLauncher = new CommandLauncher(new GameManager(), new JsonCreator());
                        gui.setCmd(cmdLauncher);
                    }
                    try {
                        cmdLauncher.addCommand(new SetUsernameCommand(ClientSingleton.getInstance().getToken(), userField.getText().trim()));
                        gui.startLobby();
                    } catch (RemoteException e) {
                        LOGGER.LOGGER.log(Level.WARNING,Arrays.toString(e.getStackTrace()));
                    }
                } else {
                    info.setText("inserisci un username valido");
                    info.setVisible(true);
                }
            }
        });

        BackgroundImage myBI = new BackgroundImage(new Image(pathAdrenaline, 1000, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        StackPane box = new StackPane();
        //adding background image to box
        box.setBackground(new Background(myBI));
        //adding components to box
        box.getChildren().add(userField);
        box.getChildren().add(tokenField);
        box.getChildren().add(startButtonSocket);
        box.getChildren().add(startButtonRMI);
        box.getChildren().add(info);

        stage.setScene(new Scene(box, 1000, 600));
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }


    private boolean checkUsername(String username) {
        return !username.equalsIgnoreCase("") && !username.equalsIgnoreCase("username");
    }

}
