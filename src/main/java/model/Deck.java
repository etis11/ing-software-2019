package model;

import java.util.Collections;
import java.util.Stack;


/**
 * Class that's used to create generic type of decks.WE can draw,shuffle, check if this deck is empty or not.
 */
public class Deck<T> {

    /**
     * List of cards contained in a deck.
     */
    private Stack<T> cards;


    /**
     * creates a deck
     */
    public Deck() {
        cards = new Stack<>();
    }


    /**
     * Method that removes the first card from the stack(deck) and returns the card as value of the function
     */
    public T draw() {
        return cards.pop();
    }

    /**
     * Simple method to be used twice during game. First before the game starts and second time when a deck ends.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Method used to check if a deck is empty or not.
     */
    public boolean isEmpty() {
        return cards.empty();
    }

    /**
     * add a card in the current deck
     *
     * @param card to be added
     */
    public void addCard(T card) {
        cards.push(card);
    }

}
