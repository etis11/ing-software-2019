package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetUsernameCommand extends AbstractCommand {

    private String username;

    public SetUsernameCommand(String token, String username) {
        super(token);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
