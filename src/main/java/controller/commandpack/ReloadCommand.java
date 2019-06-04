package controller.commandpack;

import controller.CommandExecutor;

public class ReloadCommand extends AbstractCommand {

    private String weaponName;

    public ReloadCommand(long token, String weaponName){
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
