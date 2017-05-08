package solitaire.piles;

import solitaire.Card;

public class TableauPile extends BasePile {
    public TableauPile() {
        super();
    }
    public TableauPile(String[] cards) {
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
            return (card.getRank() == 1);
        }
        
        Card top = getCard();
        return (top.getRank() + 1 == card.getRank()) && (top.getSuit().equals(card.getSuit()));
    }
}
