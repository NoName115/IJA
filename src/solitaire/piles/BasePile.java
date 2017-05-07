package solitaire.piles;

import solitaire.Card;

import java.util.Stack;

public abstract class BasePile {

    protected Stack<Card> pile;

    public BasePile() {
        this.pile = new Stack<>();
    }

    public BasePile(String[] cards) {
        this.pile = new Stack<>();
        for (int i = cards.length - 1; i >= 0; i--) {
            pile.push(new Card(cards[i]));
        }
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

    public String[] toArray() {
        String[] arr = new String[pile.size()];
        int pos = 0;
        while (!pile.isEmpty()) {
            arr[pos++] = pile.pop().toStringFace();
        }

        return arr;
    }
}
