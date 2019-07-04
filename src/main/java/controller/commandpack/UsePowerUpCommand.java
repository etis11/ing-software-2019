package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class UsePowerUpCommand extends AbstractCommand {

    private String powerUpType;
    private String Color;
    private String param;

    public UsePowerUpCommand(String token, String powerUpType, String color, String param){
        super(token);
        this.powerUpType=powerUpType;
        this.Color = color;
        this.param = param;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public String getColor() {
        return Color;
    }

    public String getParam() {
        return param;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
