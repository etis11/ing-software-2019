package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class WeaponCommand extends AbstractCommand {

    private String weaponName;

    public WeaponCommand(String token, String weaponName){
        super(token);
        this.weaponName = weaponName;
    }

    public String getWeaponName(){
        return weaponName;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
