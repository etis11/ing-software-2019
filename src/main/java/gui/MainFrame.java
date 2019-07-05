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
import network.Socket.JsonRouterSocket;
import view.AnsiColor;
import view.ClientSingleton;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.logging.Level;

public class MainFrame{
    private static final int BUTTON_WIDTH = 150;
    private static JsonUnwrapper receiver;
    private static ServerRMIInterface serverRMI = null;
    private CommandContainer cmdLauncher;
    private boolean networkActive = true;
    private Stage stage;
    private final Gui gui;
    private final String ip;
    private final int port;
    private boolean connected;
    private BufferedReader input;
    private PrintWriter output;
    private Socket mySocket;
    private JsonRouterSocket jsonSocketReceiver = null;

    private final InputStream pathAdrenaline = getClass().getResourceAsStream("/img/Adrenalina.PNG");


    public MainFrame(Gui gui, String ip, int port) {
        this.stage = new Stage();
        this.gui = gui;
        this.ip =ip;
        this.port = port;
        this.connected = false;
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
                if (checkUsername(userField.getText().trim()) || checkToken(tokenField.getText().trim(), userField.getText().trim())) {
                    if (networkActive) {
                        if (!connected) {

                            try {
                                mySocket = new Socket(ip, port);
                                input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                                output = new PrintWriter(mySocket.getOutputStream());
                                String token = "";
                                if (checkToken(tokenField.getText().trim(), userField.getText().trim())) {
                                    token = tokenField.getText().trim();
                                }
                                output.println(token);
                                output.flush();
                                String tokenResponse;
                                tokenResponse = input.readLine();
                                if (tokenResponse.equals("TOKEN OK")) {
                                    gui.setToken(token);
                                } else {
                                    token = tokenResponse;
                                    gui.setToken(token);
                                }
                                connected = true;
                            } catch(IOException i){
                                info.setText(AnsiColor.RED + "Errore nella connessione. Probabilmente il server è down" + AnsiColor.RESET);
                                info.setVisible(true);
                                throw new RuntimeException("Server down");
                            }
                        }
                        try{
                            //inserisci uno username
                            String possibleName = userField.getText().trim();
                            String toPut = input.readLine();
                            output.println(possibleName);
                            output.flush();
                            String serverResponse = null;
                            serverResponse = input.readLine();

                            SemplifiedGame game = new SemplifiedGame();
                            receiver = new JsonUnwrapper(game);

                        if (serverResponse.equals("OK")) {
                            try {
                                cmdLauncher = new CommandLauncherProxySocket(mySocket, gui.getToken());
                                jsonSocketReceiver = new JsonRouterSocket(mySocket, receiver, gui.getToken());
                            } catch (IOException i) {
                                info.setText(AnsiColor.RED + ">>> Problemi con il socket" + AnsiColor.RESET);
                                info.setVisible(true);
                            }

                            receiver.attachMapObserver(gui);
                            receiver.attachMessageListener(gui);
                            receiver.attachPlayerObserver(gui);
                            receiver.attachMatchObserver(gui);
                            new Thread(jsonSocketReceiver).start();
                            gui.setCmd(cmdLauncher);
                            try {
                                cmdLauncher.addCommand(new SetUsernameCommand(gui.getToken(), possibleName));
                                gui.startLobby();
                            } catch (RemoteException e) {
                                LOGGER.LOGGER.log(Level.WARNING, e.getMessage());
                            }
                        } else {
                            info.setText(serverResponse);
                            info.setVisible(true);
                        }

                        }catch (IOException e) {
                            LOGGER.LOGGER.log(Level.WARNING, e.getMessage());
                        }

                        if (mySocket == null) throw new RuntimeException("null socket");
                    }

                } else {
                    info.setText("inserisci un username e/o token valido");
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
                if (checkUsername(userField.getText().trim()) || checkToken(tokenField.getText().trim(),userField.getText().trim())) {
                    if (networkActive) {

                            if(!connected){
                            String token = "";
                            SemplifiedGame game = new SemplifiedGame();
                            receiver= new JsonUnwrapper(game);
                            //exports the jsonUnwrapper
                            try{
                                UnicastRemoteObject.exportObject(receiver,0);
                            }catch(RemoteException i){
                                LOGGER.LOGGER.log(Level.WARNING, i.getMessage());
                                info.setText(i.getMessage());
                                info.setVisible(true);
                                throw  new RuntimeException(i);
                            }
                            //gets the command container
                            try {
                                String newToken;
                                Registry registry = LocateRegistry.getRegistry(ip, 1099);
    //                        ServerRMIInterface serverRMI = null;
                                try {
                                    serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
                                } catch (NotBoundException e) {
                                    throw new RuntimeException(e);
                                }
                                //gets the token from the user
                                if (checkToken(tokenField.getText().trim(), userField.getText().trim())) {
                                    token = tokenField.getText().trim();
                                }
                                newToken = serverRMI.getPersonalToken(token);
                                gui.setToken(newToken);
                                //if newToken != token, a new user should be created and restarts the game
    //                        if(newToken.equals(token)){
    //                            cmdLauncher = serverRMI.reconnect(newToken, receiver);
    //                            gui.setCmd(cmdLauncher);
    //                        }
                                connected = true;
                            }
                            catch (Exception r){
                                LOGGER.LOGGER.log(Level.WARNING, r.getMessage());
                                info.setText(r.getMessage());
                                info.setVisible(true);
                                throw new RuntimeException(r);
                            }
                        }
                        //user creation
                        String serverResponse;
                        String username;
                        username = userField.getText().trim();
                        try {
                            serverResponse = serverRMI.checkUsername(gui.getToken(), username, receiver);
                            if (serverResponse.contains("OK")) {

                                cmdLauncher = serverRMI.getCurrentCommandLauncher(receiver);

                                try {
                                    cmdLauncher.addCommand(new SetUsernameCommand(gui.getToken(), username));
                                    gui.setCmd(cmdLauncher);
                                    gui.startLobby();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                info.setText(serverResponse);
                                info.setVisible(true);
                            }
                        }catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        receiver.attachMapObserver(gui);
                        receiver.attachMessageListener(gui);
                        receiver.attachPlayerObserver(gui);
                        receiver.attachMatchObserver(gui);
                        //todo frose servce setcmd
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

    private boolean checkToken(String token, String username) {
        return !token.equalsIgnoreCase("") && !token.equalsIgnoreCase("token") && checkUsername(username);
    }

}
