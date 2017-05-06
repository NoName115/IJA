package solitaire.card;

import solitaire.pile.server.DeckPile_Server;


public class Card_Server
{
    private int value;      // 1 - A, 2 - 2 ..., 11 - J, 12 - Q, 13 - K
    private DeckPile_Server.Type type;
    private boolean isReaveled;

    public Card_Server(int cardValue, DeckPile_Server.Type cardType)
    {
        this.value = cardValue;
        this.type = cardType;
    }

    @Override
    public String toString()
    {
        String cardInString;
        switch(this.value)
        {
            case 1:
                cardInString = "A";
                break;
            case 11:
                cardInString = "J";
                break;
            case 12:
                cardInString = "Q";
                break;
            case 13:
                cardInString = "K";
                break;
            default:
                cardInString = Integer.toString(this.value);
                break;
        }

        return cardInString + " - " + this.type.toString();
    }

    public DeckPile_Server.Type getType() { return this.type; }
    public int getValue() { return this.value; }

    public boolean typeEqual(Card_Server inputCard)
    {
        return this.type == inputCard.getType();
    }

    public boolean colorEqual(Card_Server inputCard)
    {
        return this.type.equalColor(inputCard.getType());
    }
}