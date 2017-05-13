package solitaire.piles;

import solitaire.Card;
import solitaire.networking.Network.*;
import java.util.Stack;


public class PileMover {
    public static GameMoveResponse stockToWaste(StockPile s, WastePile w) {
        if (s.isEmpty()) {
            if (w.isEmpty()) {
                System.out.println("waste and stock is empty");
                return null;
            }

            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[w.size()];
            resp.from = 1;
            resp.to = 0;
            int index = 0;

            while (!w.isEmpty()) {
                Card card = w.popCard();
                card.turnDown();
                s.pushCard(card);
                resp.add[index++] = card.toStringFace();
            }

            return resp;
        } else {
            Card card = s.popCard();
            w.pushCard(card);
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[1];
            resp.add[0] = card.toStringFace();

            return resp;
        }
    }

    public static GameMoveResponse wasteToFoundation(WastePile w, FoundationPile f) {
        if (w.isEmpty())
        {
            return null;
        }

        if (f.canAdd(w.getCard())) {
            Card card = w.popCard();
            f.pushCard(card);
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[1];
            resp.add[0] = card.toStringFace();

            return resp;
        }

        return null;
    }

    public static GameMoveResponse wasteToTableau(WastePile w, TableauPile t) {
        if (t.canAdd(w.getCard())) {
            Card card = w.popCard();
            t.pushCard(card);
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[1];
            resp.add[0] = card.toStringFace();

            return resp;
        }

        return null;
    }

    public static GameMoveResponse foundationToFoundation(FoundationPile f1, FoundationPile f2) {
        int cardsToTake = f1.howManyCardsToTake(f2.getCard());
        if (cardsToTake == 0)
        {
            return null;
        }
        else
        {
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[cardsToTake];

            Stack<Card> correctCardStack = new Stack<Card>();
            int i = 0;
            while (i < cardsToTake) {
                Card card = f1.popCard();
                correctCardStack.push(card);
                resp.add[i] = card.toStringFace();
                i++;
            }

            for (Card tempCard : correctCardStack)
            {
                f2.pushCard(tempCard);
            }

            return resp;
        }
    }

    public static GameMoveResponse foundationToTableau(FoundationPile f, TableauPile t) {
        if (t.canAdd(f.getCard()))
        {
            Card card = f.popCard();
            t.pushCard(card);
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[1];
            resp.add[0] = card.toStringFace();

            return resp;
        }

        return null;
    }

    public static GameMoveResponse tableauToFoundation(TableauPile t, FoundationPile f) {
        if (f.canAdd(t.getCard()))
        {
            Card card = t.popCard();
            f.pushCard(card);
            GameMoveResponse resp = new GameMoveResponse();
            resp.add = new String[1];
            resp.add[0] = card.toStringFace();

            return resp;
        }

        return null;
    }
}
