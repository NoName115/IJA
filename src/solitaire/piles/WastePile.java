package solitaire.piles;

import solitaire.Card;

public class WastePile extends BasePile {
    public WastePile() {
        super();
    }

    public WastePile(String[] cards) {
        super(cards);
    }

    public Card getFirstCard()
    {
        return this.pile.isEmpty() ? null : this.pile.get(this.pile.size() - 1);
    }

    @Override
    public void pushCard(Card card) {
        super.pushCard(card);
        card.turnUp();
    }
}
