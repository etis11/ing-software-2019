package controller.commandpack;

import controller.CommandExecutor;
import model.GameManager;
import view.MessageListener;
import model.AmmoCard;

import java.util.List;

public class PickUpAmmoCommand extends AbstractCommand {

    public PickUpAmmoCommand(MessageListener originView, List<MessageListener> allViews){
        super(originView, allViews);
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
