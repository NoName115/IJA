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

        for (int i = 0; i < cards.length; i++) {
            pile.push(new Card(cards[i]));
        }
    }

    public Card getCard() {
        if (pile.size() == 0)
        {
            return null;
        }

        return pile.peek();
    }

    public Card popCard() {
        if (pile.size() == 0)
        {
            return null;
        }

        return pile.pop();
    }

    public void pushCard(Card card) {
        pile.push(card);
    }

    public int size() {
        return pile.size();
    }

    public boolean isEmpty() {
        return pile.isEmpty();
    }

    public Stack<Card> getPile() { return this.pile; }

    public String[] toArray() {
        String[] arr = new String[pile.size()];
        int pos = 0;
        while (pos < pile.size()) {
            arr[pos] = pile.elementAt(pos).toStringFace();
            pos++;
        }

        return arr;
    }
}
