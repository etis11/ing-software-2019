package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;
import exceptions.InsufficientAmmoException;
import model.WeaponCard;

import java.util.List;

public class ReloadCommand extends AbstractCommand {

    private String weaponName;

    public ReloadCommand(MessageListener originView, List<MessageListener> allViews, String weaponName){
        super(originView, allViews);
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
