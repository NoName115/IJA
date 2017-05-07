package solitaire.piles;

import solitaire.Card;

public class WastePile extends BasePile {
    public WastePile() {
        super();
    }

    @Override
    public void pushCard(Card card) {
        super.pushCard(card);
        card.turnUp();
    }
}
