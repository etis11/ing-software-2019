package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;
import java.util.List;

public class PickUpCommand extends AbstractCommand {


    private String weaponName;

    public PickUpCommand(String token, String weaponName) {
        super(token);
        this.weaponName = weaponName;
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }

    public String getWeaponName() {
        return weaponName;
    }
}
