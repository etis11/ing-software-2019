package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetMapCommand extends AbstractCommand {

    /*
    string representig the map choosen
     */
    String map;

    public SetMapCommand(String token, String map) {
        super(token);
        this.map = map;
    }

    public SetMapCommand() {
        super();
    }


    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }


    public String getMap(){
        return map;
    }

}
