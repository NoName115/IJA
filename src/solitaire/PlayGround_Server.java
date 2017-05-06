package solitaire;

import solitaire.card.Card;
import solitaire.pile.server.*;

import java.util.ArrayList;

public class PlayGround_Server
{
    private static final int NUMBER_OF_PILES = 13;
    private static final int NUMBER_OF_LINKED_PILES = 7;

    // Vsetky decky potrebne pre hranie
    private DeckPile_Server deckPile;
    private DrawPile_Server drawPile;
    private DrawHelpPile_Server drawHelpPile;
    private ArrayList<DiscardPile_Server> discardPiles;    // 4 decks
    private ArrayList<LinkedPile_Server> linkedPiles;      // 8 decks

    // Pole vsetkych Pile-ov
    private Pile_Server[] allPiles;
    // List undo commandov
    private ArrayList<Command> undoList;

    private class HintMessage
    {
        private Card source;
        private Card destination;
        private Card under;
        private String message;

        public HintMessage(Card sourceCard, Card destinationCard, Card under, String message)
        {
            this.source = sourceCard;
            this.destination = destinationCard;
            this.under = under;
            this.message = message;
        }

        public String getHintString(boolean moveCard)
        {
            if (moveCard)
            {
                if (this.destination != null)
                {
                    return "Move " + this.source.toString() + " at " + this.destination.toString();
                }
                else
                {
                    return "Move " + this.source.toString() + " at " + this.message;
                }
            }
            else
            {
                return "Draw card";
            }
        }
    }

    public PlayGround_Server()
    {
        // Definovanie zakladnych listov
        this.undoList = new ArrayList<Command>();
        this.allPiles = new Pile[NUMBER_OF_PILES];

        // Vygeneruje balicek 52 kariet
        this.deckPile = new DeckPile();

        this.fillDecks();
        this.reavelTopCards();
    }

    // TODO
    // Tu by sa malo poslat klientovy naplnenie balickov
    // Poslat spravu addCard(hodnotaKarty, typKarty, indexPile-u)
    private void fillDecks()
    {
        for (int i = 0; i < NUMBER_OF_LINKED_PILES; ++i)
        {
            for (int j = 0; j < i + 1; ++j)
            {
                this.linkedPiles.get(i).addCard(this.deckPile.popCard());
            }
        }

        Card deckPileCard = null;
        while ((deckPileCard = this.deckPile.popCard()) != null)
        {
            this.drawPile.addCard(deckPileCard);
        }
    }

    // TODO
    // Poslat klientovy spravu na odhalenie vrchnych kariet
    private void reavelTopCards()
    {
        for (int i = 0; i < NUMBER_OF_LINKED_PILES; ++i)
        {
            this.linkedPiles.get(i).reavelTopCard();
        }
    }

    // TODO
    // Poslat klientovy ze hra skoncila
    /**
     * Funkcie kontroluje ci je hra dohrata
     */
    private void checkGameEnded()
    {
        for (DiscardPile lp : this.discardPiles)
        {
            if (!lp.containAllCards())
            {
                return;
            }
        }

        // Kazdy discard pile obsahuje vsetky karty
        // TODO
        // Poslat spravu
    }

    // TODO
    // Poslat MOVE hracovy
    /**
     * Funkcia urobi undo
     */
    public void undo()
    {
        if (!undoList.isEmpty())
        {
            int lastIndex = undoList.size() - 1;
            Command tempCommand = undoList.get(lastIndex);
            undoList.remove(lastIndex);
            tempCommand.undo();
        }
    }

    // TODO
    // Vratit hint ako string klientovy
    /**
     * Funkcie vrati hint(string)
     */
    public String showHint()
    {
        HintMessage hint;

        // Kontrola kazdej faceUp karty z linkedListu
        // ci sa da dat niekde inde polozit
        for (LinkedPile lp : this.linkedPiles)
        {
            for (Card tempCard : lp.getFaceUpCardList())
            {
                if ((hint = hintCanAddCard(tempCard, lp)) != null)
                {
                    if (hint.destination == null && hint.under == null && lp.getFaceDownCardListSize() == 0 && hint.message != "discard pile")
                    {
                        hint = null;
                        continue;
                    }

                    if (hint.destination != null && hint.under != null && (hint.destination.getValue() == hint.under.getValue()))
                    {
                        hint = null;
                        continue;
                    }

                    return hint.getHintString(true);
                }
            }
        }

        // Vrchna karta z drawHelpPile
        ArrayList<Card> cardList = this.drawHelpPile.getCardList();
        if (!cardList.isEmpty())
        {
            if ((hint = hintCanAddCard(cardList.get(cardList.size() - 1), null)) != null)
            {
                return hint.getHintString(true);
            }
        }

        // Zvysok draw help pile
        for (Card tempCard : this.drawHelpPile.getCardList())
        {
            if ((hint = hintCanAddCard(tempCard, null)) != null)
            {
                return hint.getHintString(false);
            }
        }

        // Draw Pile
        for (Card tempCard : this.drawPile.getCardList())
        {
            if ((hint = hintCanAddCard(tempCard, null)) != null)
            {
                return hint.getHintString(false);
            }
        }

        return null;
    }

    /*
     * Methoda vrati dvojicu kariet, source & destination card
     * Ak neexistuje hint vrati null
     */
    private HintMessage hintCanAddCard(Card inputCard, LinkedPile inputPile)
    {
        if (inputCard == null)
        {
            return null;
        }

        // Skontrolujem ci neviem polozit kartu
        // do odkladacich balickou
        for (DiscardPile pile : this.discardPiles)
        {
            if (pile.canAddCard(inputCard))
            {
                if (inputPile != null && inputCard != inputPile.getLastFaceUpCard())
                {
                    break;
                }

                return new HintMessage(
                    inputCard,
                    null,
                    null,
                    "discard pile"
                    );
            }
        }

        // Skontrolujem ci neviem polozit kartu
        // do linked pile-u
        for (LinkedPile pile : this.linkedPiles)
        {
            if (pile.canAddCard(inputCard))
            {
                return new HintMessage(
                    inputCard,
                    pile.getLastFaceUpCard(),
                    inputPile != null ? inputPile.getUnderCard(inputCard) : null,
                    "linked pile"
                    );
            }
        }

        return null;
    }

} 