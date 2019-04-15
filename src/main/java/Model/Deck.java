package Model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck<T> {

    private Stack<T> cards;

    public Stack<T> getCards() {
        return cards;
    }

    public void setCards(Stack<T> cards) {
        this.cards = cards;
    }
public T draw() {
return cards.pop();
}

public void shuffle() {
    Collections.shuffle(cards);
}

public boolean isEmpty() {
return cards.empty();
}

}
