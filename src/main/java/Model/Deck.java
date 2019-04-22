package Model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;


/**
 * Class that's used to create generic type of decks.WE can draw,shuffle, check if this deck is empty or not.
 * */
public class Deck<T> {


/**
 * List of cards contained in a deck.
 * */
    private Stack<T> cards;

/**
 * get Method used to get list of cards from Stack
 * */
    public Stack<T> getCards() {
        return cards;
    }

 //TODO is it really needed?
    public void setCards(Stack<T> cards) {
        this.cards = cards;
    }

 /**
  *  Method that removes the first card from the stack(deck) and returns the card as value of the function
  * */
public T draw() {
return cards.pop();
}

/**
 * Simple method to be used twice during game. First before the game starts and second time when a deck ends.
 * */
public void shuffle() {
    Collections.shuffle(cards);
}

/**
 * Method used to check if a deck is empty or not.
 * */
public boolean isEmpty() {
return cards.empty();
}

}
