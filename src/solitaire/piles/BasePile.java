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

    /**
     * Method return top card
     *
     * @return Card get top card
     */
    public Card getCard() {
        if (pile.size() == 0)
        {
            return null;
        }

        return pile.peek();
    }

    /**
     * Method return top card and pop it
     *
     * @return Card pop top card
     */
    public Card popCard() {
        if (pile.size() == 0)
        {
            return null;
        }

        return pile.pop();
    }

    /**
     * Method insert card to pile
     *
     * @param Card input card
     */
    public void pushCard(Card card) {
        pile.push(card);
    }

    /**
     * Method return size of card pile
     *
     * @return int pile size
     */
    public int size() {
        return pile.size();
    }

    /**
     * Method return true/false if pile is empty
     *
     * @return boolean if pile is empty
     */
    public boolean isEmpty() {
        return pile.isEmpty();
    }

    /**
     * Method return whole pile
     *
     * @return Stack<Card> stack of cards
     */
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
