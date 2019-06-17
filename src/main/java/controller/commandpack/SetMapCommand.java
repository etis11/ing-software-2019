package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetMapCommand extends AbstractCommand {

    /*
    index representig the map choosen
     */
    int map;

    public SetMapCommand(String token, int map) {
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


    public int getMap(){
        return map;
    }

}
