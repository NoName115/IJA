package solitaire;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

import solitaire.pile.*;
import solitaire.card.*;

/**
 * Object reprezentujuci jednu hraciu plochu, bez renderu
*/
public class PlayGroundBase
{
    // Vsetky decky potrebne pre hranie
    protected DeckPile deckPile;
    protected DrawPile drawPile;
    protected DrawHelpPile drawHelpPile;
    protected ArrayList<DiscardPile> discardPiles;    // 4 decks
    protected ArrayList<LinkedPile> linkedPiles;      // 8 decks

    // Pole vsetkych Pile-ov
    // 0 - Draw_Pile
    // 1-7 - Linked_Pile
    // 8-11 - Discard_Pile
    // 12 - Draw_Help_Pile
    protected Pile[] allPiles;
    // List undo commandov
    protected ArrayList<Command> undoList;

    protected boolean gameEnded = false;

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
                return "Draw solitaire.card";
            }
        }
    }

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
                    if (hint.destination == null && hint.under == null && lp.getFaceDownCardListSize() == 0 && hint.message != "discard solitaire.pile")
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

        // Zvysok draw help solitaire.pile
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
     * Methoda vrati dvojicu kariet, source & destination solitaire.card
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
                    "discard solitaire.pile"
                    );
            }
        }

        // Skontrolujem ci neviem polozit kartu
        // do linked solitaire.pile-u
        for (LinkedPile pile : this.linkedPiles)
        {
            if (pile.canAddCard(inputCard))
            {
                return new HintMessage(
                    inputCard,
                    pile.getLastFaceUpCard(),
                    inputPile != null ? inputPile.getUnderCard(inputCard) : null,
                    "linked solitaire.pile"
                    );
            }
        }

        return null;
    }

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

        // Kazdy discard solitaire.pile obsahuje vsetky karty
        this.gameEnded = true;
    }
}