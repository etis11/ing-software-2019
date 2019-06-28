package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class ThrowWeaponCommand extends AbstractCommand {

    String weaponToThrow;

    public ThrowWeaponCommand(String token, String weaponToThrow) {
        super(token);
        this.weaponToThrow = weaponToThrow;
    }

    public String getWeaponToThrow(){
        return weaponToThrow;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
