package controller.commandpack;

import controller.CommandExecutor;

public class PickUpWeaponCommand extends AbstractCommand {

    private String weaponName;

    public PickUpWeaponCommand(String token, String weaponName) {
        super(token);
        this.weaponName = weaponName;
    }

    public String getWeaponName() {
        return weaponName;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
