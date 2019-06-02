package network;

import controller.CommandLauncher;
import controller.CommandLauncherInterface;
import controller.commandpack.Command;

import java.io.IOException;

public class ServerLauncher {

    public static void main(String[] args) {
        CommandLauncherInterface launcher = new CommandLauncherInterface(){
            @Override
            public void executeCommand() {
                System.out.println(">>> Eseguo");
            }

            @Override
            public void addCommand(Command c) {
                //System.out.println(">>> Arrivato il comando: " + c.getClass());
            }
        };

        SocketServer ss;
        try{

            ss = new SocketServer(8000, launcher);
            ss.run();
        }
        catch (IOException i){
            System.out.println("che merda");
            System.out.println(i.getMessage());
            i.printStackTrace();
        }


    }
}
