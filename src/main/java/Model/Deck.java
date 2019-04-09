package Model;

import java.util.List;
import java.util.Stack;

public class Deck<T> {
   private Stack<T> cards;

public T draw() {
return cards.pop();
}

public void shuffle() {
//TODO
}

public boolean isEmpty() {
return cards.empty();
}

}
