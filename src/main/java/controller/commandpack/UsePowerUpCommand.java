package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class UsePowerUpCommand extends AbstractCommand {

    private String powerUpType;
    private String Color;
    private String param;
    private String param2;

    public UsePowerUpCommand(String token, String powerUpType, String color, String param, String param2){
        super(token);
        this.powerUpType=powerUpType;
        this.Color = color;
        this.param = param;
        this.param2 = param2;
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

    public String getParam2() {
        return param2;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
