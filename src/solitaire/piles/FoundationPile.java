package solitaire.piles;

import solitaire.Card;

import java.util.Stack;

public class FoundationPile extends BasePile {
    public FoundationPile() {
        super();
    }

    /**
     * Function returns true if card can be added on foundation pile
     *
     * @param card card to add
     * @return true/false
     */
    public boolean canAdd(Card card) {
        if (pile.isEmpty()) return (card.getRank() == 13);
        Card top = getCard();
        if (!top.isFaceUp()) return false;
        return (card.isRed() != top.isRed()) && (card.getRank() == top.getRank() - 1);
    }

    public void addCards(Stack<Card> cards) {
        while (!cards.isEmpty()) {
            pushCard(cards.pop());
        }
    }

    public Stack<Card> getPile() { return this.pile; }
}
