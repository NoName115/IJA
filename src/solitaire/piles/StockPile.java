package solitaire.piles;

import solitaire.Card;

import java.util.ArrayList;
import java.util.Stack;

public class StockPile extends BasePile {

    public StockPile() {
        super();
        createStock();
    }

    public StockPile(String[] cards) {
        super(cards);
    }

    private void createStock() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            for (int k = 1; k <= 13; k++) {
                String suit = "";

                if (i == 1)
                {
                    suit = "h";
                }
                if (i == 2)
                {
                    suit = "d";
                }
                if (i == 3)
                {
                    suit = "c";
                }                
                if (i == 4)
                {
                    suit = "s";
                }

                Card temp = new Card(k, suit);
                cards.add(temp);
            }
        }

        while (cards.size() != 0) {
            if (cards.size() == 1) pile.push(cards.remove(0));
            else {
                int random = (int) ((double) cards.size() * Math.random());
                Card card = cards.remove(random);
                System.out.println("Pushing " + card.toString());
                pile.push(card);
            }
        }

    }

    @Override
    public void pushCard(Card card) {
        super.pushCard(card);
        card.turnDown();
    }
}
