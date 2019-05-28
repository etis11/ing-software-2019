package controller;

import controller.commandpack.*;
import view.StringView;


/**
 * The command parser is a registry in which command can be registered (this operation is usually done during initialization).
 * Then, for usage, the command can be obtained on request. A command is associated to a key (which can be considered his
 * name)
 */
public class CommandParser implements Runnable{


    private boolean quit;

    //TODO sono  qui?
    private StringView cli;

    private CommandLauncherInterface cmdExe;


    //TODO bisognerà renderlo più generale per tutte le gui, poi ne parliamo. OSCAR
    public CommandParser(){
        //TODo implementare
        this.quit = true;
    }

    public CommandParser(StringView view){
        //TODo implementare
        cli = view;
        this.quit = true;
    }

    private Command parseInput(String command){
        //todo
        return null;
    }


    @Override
    public void run() {
        String commandName;
        while(quit){
            commandName = cli.getInput();
            if(!commandName.equalsIgnoreCase("quit")) {
                cmdExe.addCommand(parseInput(commandName));
            }
            else{
                quit = false;
            }
        }
    }
}
