package controller.commandpack;

import controller.CommandExecutor;
import model.PowerUpCard;

import java.io.IOException;

public class SpawnCommand extends AbstractCommand {

    private String powerUpType;
    private String color;

    public SpawnCommand(String token, String powerUpType, String color) {
        super(token);
        this.powerUpType = powerUpType;
        this.color = color;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public String getColor() {
        return color;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
