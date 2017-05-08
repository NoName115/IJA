package solitaire.piles;

import solitaire.Card;

import java.util.Stack;

public class FoundationPile extends BasePile {
    public FoundationPile() {
        super();
    }
    public FoundationPile(String[] cards) {
        super(cards);
    }

    /**
     * Function returns true if card can be added on foundation pile
     *
     * @param card card to add
     * @return true/false
     */
    public boolean canAdd(Card card) {
        if (pile.isEmpty())
        {
            return (card.getRank() == 13);
        }

        Card top = getCard();
        if (!top.isFaceUp())
        {
            return false;
        }

        return (card.isRed() != top.isRed()) && (card.getRank() == top.getRank() - 1);
    }

    public void addCards(Stack<Card> cards) {
        while (!cards.isEmpty()) {
            pushCard(cards.pop());
        }
    }

    @Override
    public Card popCard() {
        Card card = super.popCard();

        if (!this.isEmpty())
        {
            this.getCard().turnUp();
        }

        return card;
    }

    public Stack<Card> getPile() { return this.pile; }

    public int howManyCardsToTake(Card card) {
        int index = 0;

        for (int i = pile.size() - 1; i >= 0; i--) {
            Card pcard = pile.elementAt(i);
            if (!pcard.isFaceUp())
            {
                break;
            }

            if (card == null)
            {
                if (pcard.getRank() == 13)
                {
                    index = pile.size() - i;
                    break;
                }
            }
            else if (pcard.getRank() == card.getRank() - 1 && pcard.isRed() == !card.isRed())
            {
                index = pile.size() - i;
                break;
            }
        }

        return index;
    }
}
