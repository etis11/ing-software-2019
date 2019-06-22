package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SpawnCommand extends AbstractCommand {

    private String color;

    public SpawnCommand(String token, String color) {
        super(token);
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
