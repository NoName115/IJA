package solitaire.piles;

import solitaire.Card;

public class WastePile extends BasePile {
    public WastePile() {
        super();
    }

    public WastePile(String[] cards) {
        super(cards);
    }

    @Override
    public void pushCard(Card card) {
        super.pushCard(card);
        card.turnUp();
    }
}
