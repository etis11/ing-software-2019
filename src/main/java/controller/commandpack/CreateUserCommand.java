package controller.commandpack;

import controller.CommandExecutor;

public class CreateUserCommand extends AbstractCommand {

    private String username;

    public CreateUserCommand(String token, String username) {
        super(token);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
