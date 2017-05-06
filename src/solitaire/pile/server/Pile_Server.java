package solitaire.pile.server;

import java.util.ArrayList;

import solitaire.card.*;


/**
 * Trieda z ktorej dedia vsetky ostatne xxxPile
 */
public abstract class Pile_Server
{
    protected ArrayList<Card_Server> cardList;

    public Pile_Server()
    {
        this.cardList = new ArrayList<Card_Server>();
    }

    /**
     * Prida kartu na koniec balicku
     * a vola setDefaultPosition pre danu kartu
     */
    public void addCard(Card_Server inputCard)
    {
        this.cardList.add(inputCard);
    }

    /**
     * Vola sa pocas hry
     * Vlozi kartu do Pile-u
     * Vrati true ak karta bola vlozena, inac false
     */
    public boolean insertCard(ArrayList<Card_Server> inputList) { return false; }

    /**
     * Do balicku kariet v danej Pile-e ulozi vstupny zoznam kariet
     */
    public void returnListOfCardsToPile(ArrayList<Card_Server> inputList, boolean action) {}
}