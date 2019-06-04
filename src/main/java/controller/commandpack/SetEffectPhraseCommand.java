package controller.commandpack;

import controller.CommandExecutor;

public class SetEffectPhraseCommand extends AbstractCommand {

    private String phrase;

    public SetEffectPhraseCommand(long token, String phrase){
        super(token);
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    @Override
    public void execute(CommandExecutor exe) {
        exe.execute(this);
    }
}
