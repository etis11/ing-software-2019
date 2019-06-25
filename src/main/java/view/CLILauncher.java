package view;

import controller.*;
import controller.commandpack.Command;
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
                Registry registry = LocateRegistry.getRegistry();
                ServerRMIInterface serverRMI = (ServerRMIInterface) registry.lookup("serverRMI");
                token = serverRMI.getPersonalToken(receiver, "");
                CLI.displayText("TOKEN: " + token);
                ClientSingleton.getInstance().setToken(token);
                cmdLauncher = serverRMI.getCurrentCommandLauncher();
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
                output.write(ClientSingleton.getInstance().getToken() + "\n");
                output.flush();
                token = input.readLine();
                CLI.displayText("TOKEN: " + token);
                ClientSingleton.getInstance().setToken(token);
            }
            catch (IOException i){
                CLI.displayText(AnsiColor.RED + "Errore nella connessione. Probabilmente il server è down" + AnsiColor.RESET);
                throw new RuntimeException("Server down");
            }
            if (mySocket == null) throw  new RuntimeException("null socket");
            try{
                cmdLauncher = new CommandLauncherProxySocket(mySocket);
                jsonSocketReceiver = new JsonRouterSocket(mySocket, receiver);
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
