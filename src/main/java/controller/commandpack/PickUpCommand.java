package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;
import java.util.List;

public class PickUpCommand extends AbstractCommand {

    /**
     * list containing moves to construct a movement
     */
    private List<String> moves;

    private String weaponName;

    public PickUpCommand(String token, List<String> moves, String weaponName) {
        super(token);
        this.moves = moves;
        this.weaponName = weaponName;
    }

    /**
     * pick the ammo in the tile and put it into the loader
     */
    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }

    public List<String> getMoves(){
        return moves;
    }

    public String getWeaponName() {
        return weaponName;
    }
}
