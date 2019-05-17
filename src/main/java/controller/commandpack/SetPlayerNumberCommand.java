package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

public class SetPlayerNumberCommand extends AbstractCommand {

    private int players;

    public SetPlayerNumberCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, int players){
        super(gameManager, originView, allViews);
        this.players = players;
    }

    @Override
    public void execute() {
        //TODO manca codizione se è il primo user
        if(players <6 && players>2){
            gameManager.getMatch().setPlayerNumber(players);
            for (MessageListener ml : allViews){
                ml.notify("Il numero di uccisioni per la partita è stato cambiato a: "+players);
            }
        }
        else{
            if(players >5 || players<3){
                originView.notify("Numero uccisioni non nel target ammissibile");
            }
            else{
                originView.notify("Operazione non consentita");
            }
        }
    }
}
