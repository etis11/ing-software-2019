package controller.commandpack;

import model.GameManager;
import view.MessageListener;

import java.util.List;

public class SetNumberOfDeathCommand extends Command {

    private int death;

    public SetNumberOfDeathCommand(GameManager gameManager, MessageListener originView, List<MessageListener> allViews, int num){
        super(gameManager, originView, allViews);
        this.death = num;
    }

    @Override
    public void execute() {
        //TODO manca codizione se è il primo user
        if(death <9 && death>4){
            gameManager.getMatch().setSkulls(death);
            for (MessageListener ml : allViews){
                ml.notify("Il numero di uccisioni per la partita è stato cambiato a: "+death);
            }
        }
        else{
            if(death >8 || death<5){
                originView.notify("Numero uccisioni non nel target ammissibile");
            }
            else{
                originView.notify("Operazione non consentita");
            }
        }
    }
}
