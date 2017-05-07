package solitaire.piles;

import solitaire.Card;

import java.util.Stack;

public abstract class BasePile {

    protected Stack<Card> pile;

    public BasePile() {
        this.pile = new Stack<>();
    }

    public Card getCard() {
        if (pile.size() == 0) return null;
        return pile.peek();
    }

    public Card popCard() {
        if (pile.size() == 0) return null;
        return pile.pop();
    }

    public void pushCard(Card card) {
        pile.push(card);
    }

    public boolean isEmpty() {
        return pile.isEmpty();
    }
}
