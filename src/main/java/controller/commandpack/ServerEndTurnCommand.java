package controller.commandpack;


import controller.CommandExecutor;
import model.User;

import java.io.IOException;

public class ServerEndTurnCommand extends AbstractCommand {

    /**
     * user that has disconnect
     */
    private final User disconnectUser;

    /**
     * since it's produced by the server, it's meaningless
     * @param token
     * @param disconnectedUser
     */
    public ServerEndTurnCommand(String token, User disconnectedUser){
        super(token);
        this.disconnectUser = disconnectedUser;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }

    public User getDisconnectUser() {
        return disconnectUser;
    }
}

