package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class UsePowerUpCommand extends AbstractCommand {

    private String powerUpType;
    private String Color;

    public UsePowerUpCommand(String token, String powerUpType, String color){
        super(token);
        this.powerUpType=powerUpType;
        this.Color = color;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public String getColor() {
        return Color;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
