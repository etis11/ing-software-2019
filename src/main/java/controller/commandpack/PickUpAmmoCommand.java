package controller.commandpack;

import controller.CommandExecutor;

public class PickUpAmmoCommand extends AbstractCommand {

    public PickUpAmmoCommand(String token){
        super(token);
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
