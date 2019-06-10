package view;

import controller.CommandContainer;
import controller.commandpack.Command;

import java.rmi.RemoteException;

public class CLILauncher {

    public static void main(String[] args) throws Exception {

        CommandLineInterface CLI = new CommandLineInterface();

        CommandContainer container = new CommandContainer() {
            @Override
            public void addCommand(Command c) throws RemoteException {
                System.out.println(c.getClass());
            }
        };

        Parserator p = new Parserator(CLI, container);

        Thread t = new Thread(p);
        t.start();
    }
}
