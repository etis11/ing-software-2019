package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;
import exceptions.PickableNotPresentException;
import model.WeaponCard;

import java.util.List;

public class PickUpWeaponCommand extends AbstractCommand {

    private String weaponName;

    public PickUpWeaponCommand(MessageListener originView, List<MessageListener> allViews, String weaponName){
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
