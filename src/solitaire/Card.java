package solitaire;

public class Card {
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructs a solitaire.Card object
     * @param newRank rank of the card
     * @param newSuit suit of the card
     */
    public Card(int newRank, String newSuit)
    {
        rank = newRank;
        suit = newSuit;
        isFaceUp = false;
    }

    public Card(String card) {
        if (card.charAt(0) == 't') rank = 10;
        else if (card.charAt(0) == 'j') rank = 11;
        else if (card.charAt(0) == 'q') rank = 12;
        else if (card.charAt(0) == 'k') rank = 13;
        else if (card.charAt(0) == 'a') rank = 1;
        else rank = Integer.parseInt(card.substring(0, 1));

        suit = card.substring(1, 2);
        isFaceUp = card.charAt(2) == 'u';
    }

    /**
     *
     * @return rank of the solitaire.Card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     *
     * @return suit of the solitaire.Card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Checks if a solitaire.Card is red
     * @return true if card is red; false otherwise
     */
    public boolean isRed()
    {
        return (suit.equals("d") || suit.equals("h"));
    }

    /**
     * @return true if face up; false otherwise
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Turns the card face up
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * Turns the card face down
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

//    /**
//     *Finds appropriate file name for gif corresponding to card based
//     * on suit and rank
//     *
//     * @return name of the file for the appropriate solitaire.Card
//     */
//    public String getFileName()
//    {
//        if (!isFaceUp)return "./src/cards/back.gif"; //using escape sequence, not double slash
//        if (rank == 10) return "./src/cards/t" + suit + ".gif";
//        if (rank == 11) return "./src/cards/j" + suit + ".gif";
//        if (rank == 12) return "./src/cards/q" + suit + ".gif";
//        if (rank == 13) return "./src/cards/k" + suit + ".gif";
//        if (rank == 1) return "./src/cards/a" + suit + ".gif";
//        return "./src/cards/" + rank + suit + ".gif";
//    }
//
    public String toStringRobo()
    {
        switch(rank)
        {
            case 10:
                return "10 - " + suit;
            case 11:
                return "J - " + suit;
            case 12:
                return "Q - " + suit;
            case 13:
                return "K - " + suit;
            case 14:
                return "A - " + suit;
            default:
                return Integer.toString(rank) + " - " + suit;
        }
    }

    public String toString() {
        if (rank == 10)
        {
            return "t" + suit;
        }
        if (rank == 11)
        {
            return "j" + suit;
        }
        if (rank == 12)
        {
            return "q" + suit;
        }
        if (rank == 13)
        {
            return "k" + suit;
        }
        if (rank == 1)
        {
            return "a" + suit;
        }

        return Integer.toString(rank) + suit;
    }

    public String toStringFace() {
        String face;
        if (isFaceUp) {
            face = "u";
        } else {
            face = "d";
        }
        return this.toString() + face;
    }

    public String getFileName()
    {
        if (!isFaceUp)
        {
            return "./src/cards/back.gif";
        }

        return "./src/cards/" + this.toString() + ".gif";
    }
//
//    @Override
//    public String toString() {
//        if (rank == 10) return "t" + suit;
//        if (rank == 11) return "j" + suit;
//        if (rank == 12) return "q" + suit;
//        if (rank == 13) return "k" + suit;
//        if (rank == 1) return "a" + suit;
//        return Integer.toString(rank) + suit;
//    }
}
