package view;

import controller.*;
import controller.commandpack.Command;
import controller.commandpack.SetUsernameCommand;
import model.clientModel.SemplifiedGame;
import network.RMI.ServerRMIInterface;
import network.Socket.CommandLauncherProxySocket;
import network.Socket.JsonRouterSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CLILauncher {

    private static JsonUnwrapper receiver;
    private static CommandLineInterface CLI;

    public static void main(String[] args) throws IOException {

        CLI = new CommandLineInterface();
        SemplifiedGame game = new SemplifiedGame();
        receiver= new JsonUnwrapper(game);
        CommandContainer cmdLauncher = null;
        String token;
        String connectionType = "";

        while(!connectionType.equals("rmi") && !connectionType.equals("socket") && !connectionType.equals("locale")){
            CLI.displayText("Scegliere connessione rmi o socket");
            connectionType = CLI.getUserInputString();
        }
        if(connectionType.equals("rmi")){
            //exports the jsonUnwrapper
            try{
                UnicastRemoteObject.exportObject(receiver,0);
            }catch(RemoteException i){
                i.printStackTrace();
                CLI.displayText(i.getMessage());
                throw  new RuntimeException(i);
            }
            //gets the command container
            try{
                String newToken ;
                Registry registry = LocateRegistry.getRegistry();
                ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
                CLI.displayText("Inserisci un token.");
                //gets the token from the user
                token = CLI.getUserInputString();
                newToken = serverRMI.getPersonalToken(token);
                CLI.displayText("TOKEN: " + newToken);
                ClientSingleton.getInstance().setToken(newToken);
                //if newToken != token, a new user should be created
                if(!newToken.equals(token)){
                    //user creation
                    boolean ok = false;
                    String serverResponse;
                    String username;
                    while (!ok){
                        CLI.displayText("Inserisci uno username");
                        username = CLI.getUserInputString();
                        serverResponse = serverRMI.checkUsername(newToken, username,receiver);
                        if(serverResponse.contains("OK")){
                            ok = true;
                            cmdLauncher = serverRMI.getCurrentCommandLauncher(receiver);
                            cmdLauncher.addCommand(new SetUsernameCommand(newToken, username));
                        }
                        else CLI.displayText(serverResponse);
                    }
                }
                else {
                    cmdLauncher = serverRMI.reconnect(newToken, receiver);
                }



            }
            catch (Exception r){
                r.printStackTrace();
                CLI.displayText(r.getMessage());
                throw new RuntimeException(r);
            }
            receiver.attachMapObserver(CLI);
            receiver.attachMessageListener(CLI);
            receiver.attachPlayerObserver(CLI);
            receiver.attachMatchObserver(CLI);
            startCLI(CLI, cmdLauncher);
        }
        else if(connectionType.equals("socket")){
            Socket mySocket;
            JsonRouterSocket jsonSocketReceiver = null;
            try{
                mySocket = new Socket("localhost", 8000);
                BufferedReader input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                PrintWriter output = new PrintWriter(mySocket.getOutputStream());
                CLI.displayText("Inserisci un token.");
                token = CLI.getUserInputString();
                output.println(token);
                output.flush();
                String tokenResponse;
                tokenResponse = input.readLine();
                if (tokenResponse.equals("TOKEN OK")){
                    ClientSingleton.getInstance().setToken(token);
                }
                else{
                    token = tokenResponse;
                    CLI.displayText("TOKEN: " + token);
                    ClientSingleton.getInstance().setToken(token);
                    //lettura della routine di username
                    boolean ok = false;
                    while(!ok){
                        //inserisci uno username
                        CLI.displayText(input.readLine());
                        String possibleName = CLI.getUserInputString();
                        output.println(possibleName);
                        output.flush();
                        String serverResponse = input.readLine();
                        if (serverResponse.equals("OK")){
                            ok = true;
                            CLI.displayText("Username accettato");
                        }
                        else{
                            CLI.displayText(serverResponse);
                        }
                    }
                }
            }
            catch (IOException i){
                CLI.displayText(AnsiColor.RED + "Errore nella connessione. Probabilmente il server è down" + AnsiColor.RESET);
                throw new RuntimeException("Server down");
            }
            if (mySocket == null) throw  new RuntimeException("null socket");
            try{
                cmdLauncher = new CommandLauncherProxySocket(mySocket, token);
                jsonSocketReceiver = new JsonRouterSocket(mySocket, receiver, token);
            }
            catch (IOException i){
                CLI.displayText(AnsiColor.RED + ">>> Problemi con il socket" + AnsiColor.RESET);
            }
            receiver.attachMapObserver(CLI);
            receiver.attachMessageListener(CLI);
            receiver.attachPlayerObserver(CLI);
            receiver.attachMatchObserver(CLI);
            if (jsonSocketReceiver == null) throw new RuntimeException("the json socket receiver is null");
            new Thread(jsonSocketReceiver).start();
            startCLI(CLI, cmdLauncher);
            System.out.println("CLI lanciata");
        }
        else{
            cmdLauncher = new CommandContainer() {
                @Override
                public void addCommand(Command c) throws RemoteException {
                    System.out.println(c.getClass());
                }
            };
        }

    }


    private static void startCLI(CommandLineInterface CLI, CommandContainer cmdLauncher){
        Parserator p = new Parserator(CLI, cmdLauncher);
        Thread t = new Thread(p);
        t.start();
    }

    public static void stopCLI(){
        try{
            UnicastRemoteObject.unexportObject(receiver, true);
        }
        catch (NoSuchObjectException s){
            CLI.displayText(AnsiColor.BLUE + ">>>Chiusura della CLI" + AnsiColor.RESET);
            System.exit(0);
        }
    }
}
