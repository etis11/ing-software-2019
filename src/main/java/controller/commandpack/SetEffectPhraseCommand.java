package controller.commandpack;

import controller.CommandExecutor;

import java.io.IOException;

public class SetEffectPhraseCommand extends AbstractCommand {

    private String phrase;

    public SetEffectPhraseCommand(String token, String phrase) {
        super(token);
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    @Override
    public void execute(CommandExecutor exe) throws IOException {
        exe.execute(this);
    }
}
