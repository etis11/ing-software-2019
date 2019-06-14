package controller.commandpack;

import controller.CommandExecutor;
import controller.JsonReceiver;
import model.GameManager;
import model.JsonCreator;
import network.TokenRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command {

    protected String token;
    protected JsonReceiver receiver;
    protected List<JsonReceiver> allReceivers;


    public AbstractCommand(String token) {
        this.token = token;
    }

    public AbstractCommand() {

    }

    public void setJsonReceiver(JsonReceiver jsonReceiver) {
        receiver = jsonReceiver;
    }

    public void endCommandToAction(GameManager gameManager) {
        gameManager.getMatch().getCurrentPlayer().decrementMoves();
        gameManager.getMatch().getCurrentPlayer().getState().resetRemainingSteps();
        gameManager.getMatch().getCurrentPlayer().getState().nextState(gameManager.getMatch().getCurrentPlayer().getOldState().getName(), gameManager.getMatch().getCurrentPlayer());
        gameManager.getMatch().getCurrentPlayer().setOldState(null);
    }

    @Override
    public void execute(CommandExecutor exe) {
        System.out.println("ERRORE");
    }

    @Override
    public void setAllJsonReceivers(List<JsonReceiver> receivers) {
        allReceivers = new ArrayList<>(receivers);
    }

    @Override
    public String getToken() {
        return token;
    }

    public JsonReceiver getJsonReceiver() {
        return receiver;
    }
}
