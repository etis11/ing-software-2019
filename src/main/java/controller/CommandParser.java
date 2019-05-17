package controller;

import controller.commandpack.*;
import exceptions.DuplicateException;
import model.GameManager;
import view.MessageListener;
import view.StringView;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The command parser is a registry in which command can be registered (this operation is usually done during initialization).
 * Then, for usage, the command can be obtained on request. A command is associated to a key (which can be considered his
 * name)
 */
public class CommandParser implements Runnable{

    /**
     * registry in which the commands are held
     */
    private final Map<String, Command> registry = new HashMap<>();

    private boolean quit;

    //TODO sono  qui?
    private StringView cli;

    private CommandExecutor cmdExe;
    private GameManager gm;
    private MessageListener originView;
    private List<MessageListener> allView;

    public CommandParser(){
        //TODo implementare
        this.quit = true;
        init();
    }

    private void init(){
        //TODO non mi convince
        registerCommand("PickUp", new AskPickCommand(gm, originView, allView));
        registerCommand("Shoot", new AskShootCommand(gm, originView, allView));
        registerCommand("Reload", new AskReloadCommand(gm, originView, allView));
        registerCommand("EndTurn", new AskEndTurnCommand(gm, originView, allView));
        //TODO come capisco se direzione o nome dell'arma o altro?
    }

    /**
     * Register a command in the parser
     * @param commandName the name of the command
     * @param c the command that has to be registered
     */
    private void registerCommand(String commandName, Command c){
        if (commandName == null || c == null) throw new IllegalArgumentException("The key or the command are null");
        if(registry.containsKey(commandName)) throw new DuplicateException("There's already a command with this key");
        if(registry.containsValue(c)) throw new DuplicateException("There is already a command with a different key");
        registry.put(commandName, c);
    }

    /**
     * gets the command with the given command name
     * @param commandName the key
     * @return a command, is never null
     */
    private Command getCommand(String commandName) {
        if(commandName == null) throw new  IllegalArgumentException("The key is null");
        if(!registry.containsKey(commandName)) throw new NoSuchElementException("There isnt a command associated to this key");
       //TODO copia del comando
        return registry.get(commandName);
    }

    public List<Command> getCommandList() {
        return new ArrayList<>(registry.values());
    }


    @Override
    public void run() {
        String commandName;
        Command command;
        while(quit){
            commandName = cli.getInput();
            if(!commandName.equalsIgnoreCase("quit")) {
                command = getCommand(commandName);
                cmdExe.addCommand(command);
            }
            else{
                quit = false;
            }
        }
    }
}
