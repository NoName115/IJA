package solitaire.pile;

import java.util.ArrayList;

import solitaire.card.*;


/**
 * Trieda z ktorej dedia vsetky ostatne xxxPile
 */
public abstract class Pile_Server
{
    protected ArrayList<Card> cardList;

    public Pile_Server()
    {
        this.cardList = new ArrayList<Card>();
    }

    /**
     * Prida kartu na koniec balicku
     * a vola setDefaultPosition pre danu kartu
     */
    public void addCard(Card inputCard)
    {
        this.cardList.add(inputCard);
        inputCard.setDefaultPosition(this.xPosition, this.yPosition);
    }

    /**
     * Vola sa pocas hry
     * Vlozi kartu do Pile-u
     * Vrati true ak karta bola vlozena, inac false
     */
    public boolean insertCard(ListOfCards inputList) { return false; }

    /**
     * Do balicku kariet v danej Pile-e ulozi vstupny zoznam kariet
     */
    public void returnListOfCardsToPile(ListOfCards inputList, boolean action) {}
}