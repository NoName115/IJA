package solitaire.pile;

import java.util.ArrayList;

import solitaire.card.*;


public class LinkedPile_Server extends Pile_Server
{
    private int Y_CARD_SHIFT;
    private ArrayList<Card> faceDownCardList;
    private ArrayList<Card> faceUpCardList;

    // Obsahuje zakladnu vysku Pile-u
    private static int defaultHeight;

    public LinkedPile_Server()
    {
        super();
    }

    @Override
    public void addCard(Card inputCard)
    {
        this.faceDownCardList.add(inputCard);
    }

    @Override
    public boolean insertCard(ListOfCards inputList)
    {
        if (inputList == null || inputList.isEmpty())
        {
            return false;
        }

        if (!checkCorrectCard(inputList))
        {
            return false;
        }

        for (Card c : inputList.getList())
        {
            this.faceUpCardList.add(c);
        }

        return true;
    }

    @Override
    public void returnListOfCardsToPile(ListOfCards inputList, boolean action)
    {
        if (inputList == null)
        {
            return;
        }

        if (action && !this.faceUpCardList.isEmpty())
        {
            int lastCardIndex = this.faceUpCardList.size() - 1;
            Card tempCard = this.faceUpCardList.get(lastCardIndex);

            this.faceUpCardList.remove(lastCardIndex);
            this.faceDownCardList.add(tempCard);

            tempCard.faceDown();
        }

        for (Card c : inputList.getList())
        {
            this.faceUpCardList.add(c);
        }
    }

    /**
     * Otoci vrchnu kartu z faceDownCardList
     * a prida ju do faceUpCardList
     */
    public boolean reavelTopCard()
    {
        if (faceDownCardList.isEmpty() || faceUpCardList.size() >= 1)
        {
            return false;
        }

        // Reavel top solitaire.card
        int lastCardIndex = this.faceDownCardList.size() - 1;
        Card tempCard = this.faceDownCardList.get(lastCardIndex);

        this.faceDownCardList.remove(lastCardIndex);
        this.faceUpCardList.add(tempCard);

        tempCard.faceUp();

        return true;
    }

    private boolean checkCorrectCard(ListOfCards inputList)
    {
        // Kontrola spravneho typu kariet
        if (!(this.faceDownCardList.isEmpty() && this.faceUpCardList.isEmpty()))
        {
            if (!this.faceUpCardList.isEmpty())
            {
                Card tempCard = inputList.getFirstCard();
                Card topCard = this.faceUpCardList.get(this.faceUpCardList.size() - 1);

                // Kontrola farby
                if (topCard.colorEqual(tempCard))
                {
                    return false;
                }

                // Kontrola hodnoty, rozdiel musi byt 1
                if (topCard.getValue() - tempCard.getValue() != 1)
                {
                    return false;
                }
            }
            
        }
        // Prva karta musi byt King
        else
        {
            if (inputList.getFirstCard().getValue() != 13)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Metoda vrati null ak sa karta neda pridat do balicku,
     * alebo kartu z vrchu balicka ak sa da
     */
    public boolean canAddCard(Card inputCard)
    {
        return checkCorrectCard(new ListOfCards(null, inputCard, 0));
    }

    /**
     * Funkcia vrati poslednu(vrchnu) kartu z faceUpCardList
     * Karta sa zo zoznamu nemaze
     */
    public Card getLastFaceUpCard()
    {
        if (!this.faceUpCardList.isEmpty())
        {
            return this.faceUpCardList.get(this.faceUpCardList.size() - 1);
        }

        return null;
    }

    public int getFaceDownCardListSize()
    {
        return this.faceDownCardList.size();
    }

    /**
     * Vrati kartu ktora je pod kartou v argumente funkcie
     */
    public Card getUnderCard(Card inputCard)
    {
        int underCardIndex = -1;
        for (Card forCard : this.faceUpCardList)
        {
            if (forCard == inputCard)
            {
                break;
            }

            underCardIndex++;
        }

        return underCardIndex < 0 ? null : this.faceUpCardList.get(underCardIndex);
    }

    public ArrayList<Card> getFaceUpCardList()
    {
        return this.faceUpCardList;
    }
}
